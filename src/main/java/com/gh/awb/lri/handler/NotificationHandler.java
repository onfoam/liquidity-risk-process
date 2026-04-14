package com.gh.awb.lri.handler;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.kie.api.runtime.manager.RuntimeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

/**
 * Sends email notifications with:
 * - TLS/SSL support (configurable)
 * - SMTP authentication
 * - Retry logic (3 attempts with exponential backoff)
 * - Non-blocking: failures are logged but do NOT halt the workflow
 */
public class NotificationHandler implements WorkItemHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationHandler.class);
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_BASE_MS = 2000; // 2s, 4s, 8s

    private final RuntimeManager runtimeManager;
    private final TemplateEngine templateEngine;

    public NotificationHandler(RuntimeManager runtimeManager) {
        this.runtimeManager = runtimeManager;
        this.templateEngine = createTemplateEngine();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.info("SendNotification: Starting for work item {}", workItem.getId());
        Map<String, Object> params = workItem.getParameters();
        Map<String, Object> results = new HashMap<>();

        try {
            String templateId = (String) params.get("templateId");
            List<String> recipients = (List<String>) params.get("recipientEmails");
            String subject = (String) params.get("subject");

            if (recipients == null || recipients.isEmpty()) {
                LOG.warn("SendNotification: No recipients, skipping");
                results.put("notificationSent", false);
                manager.completeWorkItem(workItem.getId(), results);
                return;
            }

            // Render template
            Context ctx = new Context();
            params.forEach(ctx::setVariable);
            String htmlBody = templateEngine.process("email/" + templateId, ctx);

            // Send with retry
            boolean sent = sendWithRetry(recipients, subject, htmlBody);
            results.put("notificationSent", sent);
            manager.completeWorkItem(workItem.getId(), results);

        } catch (Exception e) {
            LOG.error("SendNotification: Failed (non-blocking) - {}", e.getMessage(), e);
            results.put("notificationSent", false);
            manager.completeWorkItem(workItem.getId(), results);
        }
    }

    private boolean sendWithRetry(List<String> recipients, String subject, String htmlBody) {
        for (int attempt = 1; attempt <= MAX_RETRIES; attempt++) {
            try {
                sendEmail(recipients, subject, htmlBody);
                LOG.info("SendNotification: Sent to {} recipients (attempt {})", recipients.size(), attempt);
                return true;
            } catch (MessagingException e) {
                LOG.warn("SendNotification: Attempt {}/{} failed - {}", attempt, MAX_RETRIES, e.getMessage());
                if (attempt < MAX_RETRIES) {
                    try { Thread.sleep(RETRY_BASE_MS * (1L << (attempt - 1))); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); break; }
                }
            }
        }
        LOG.error("SendNotification: All {} retries exhausted", MAX_RETRIES);
        return false;
    }

    private void sendEmail(List<String> recipients, String subject, String htmlBody) throws MessagingException {
        Properties props = new Properties();
        String host = prop("lri.smtp.host", "localhost");
        String port = prop("lri.smtp.port", "587");
        boolean auth = Boolean.parseBoolean(prop("lri.smtp.auth", "false"));
        boolean tls = Boolean.parseBoolean(prop("lri.smtp.starttls", "true"));

        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", String.valueOf(auth));
        props.put("mail.smtp.starttls.enable", String.valueOf(tls));
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        props.put("mail.smtp.writetimeout", "10000");

        Session session;
        if (auth) {
            String user = prop("lri.smtp.user", "");
            String pass = prop("lri.smtp.password", "");
            session = Session.getInstance(props, new Authenticator() {
                @Override protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pass);
                }
            });
        } else {
            session = Session.getInstance(props);
        }

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(prop("lri.smtp.from", "lri-noreply@alwahdabank.ly")));
        for (String r : recipients) message.addRecipient(Message.RecipientType.TO, new InternetAddress(r));
        message.setSubject(subject, "UTF-8");
        message.setContent(htmlBody, "text/html; charset=UTF-8");
        Transport.send(message);
    }

    private String prop(String key, String def) { return System.getProperty(key, def); }

    private TemplateEngine createTemplateEngine() {
        ClassLoaderTemplateResolver r = new ClassLoaderTemplateResolver();
        r.setPrefix("templates/"); r.setSuffix(".html"); r.setTemplateMode("HTML"); r.setCharacterEncoding("UTF-8");
        TemplateEngine e = new TemplateEngine(); e.setTemplateResolver(r); return e;
    }

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        LOG.warn("SendNotification: Aborted {}", workItem.getId());
    }
}
