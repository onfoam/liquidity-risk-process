{
  "id": "form_f386715ab568407f",
  "name": "SelectPeriod-taskform",
  "model": {
    "taskName": "SelectPeriod",
    "processId": "com.gh.awb.lri.LiquidityRiskIndicators",
    "formModelType": "org.kie.workbench.common.forms.jbpm.model.authoring.task.TaskFormModel",
    "properties": [
      {
        "name": "selectedYear",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.String",
          "multiple": false
        }
      },
      {
        "name": "selectedQuarterRange",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.String",
          "multiple": false
        }
      }
    ]
  },
  "fields": [
    {
      "id": "field_d0378ccbfb454144",
      "name": "selectedYear",
      "label": "Reporting Year / \u0627\u0644\u0633\u0646\u0629",
      "required": true,
      "readOnly": false,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.String",
      "code": "TextBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textBox.TextBoxFieldDefinition",
      "placeHolder": "e.g. 2025",
      "binding": "selectedYear",
      "maxLength": 100
    },
    {
      "id": "field_9392a559901c453f",
      "name": "selectedQuarterRange",
      "label": "Quarter Range / \u0646\u0637\u0627\u0642 \u0627\u0644\u0631\u0628\u0639",
      "required": true,
      "readOnly": false,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.String",
      "code": "TextBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textBox.TextBoxFieldDefinition",
      "placeHolder": "e.g. Q1-Q2",
      "binding": "selectedQuarterRange",
      "maxLength": 100
    }
  ],
  "layoutTemplate": {
    "version": 1,
    "style": "FLUID",
    "layoutProperties": {},
    "rows": [
      {
        "layoutColumns": [
          {
            "span": "12",
            "rows": [],
            "layoutComponents": [
              {
                "dragTypeName": "org.kie.workbench.common.forms.editor.client.editor.rendering.EditorFieldLayoutComponent",
                "properties": {
                  "field_id": "field_d0378ccbfb454144",
                  "form_id": "form_f386715ab568407f"
                }
              }
            ]
          }
        ]
      },
      {
        "layoutColumns": [
          {
            "span": "12",
            "rows": [],
            "layoutComponents": [
              {
                "dragTypeName": "org.kie.workbench.common.forms.editor.client.editor.rendering.EditorFieldLayoutComponent",
                "properties": {
                  "field_id": "field_9392a559901c453f",
                  "form_id": "form_f386715ab568407f"
                }
              }
            ]
          }
        ]
      }
    ]
  }
}