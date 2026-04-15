{
  "id": "form_7940b5f13cdf47c2",
  "name": "ManagerReview-taskform",
  "model": {
    "taskName": "ManagerReview",
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
      },
      {
        "name": "lcrQ1",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.Float",
          "multiple": false
        }
      },
      {
        "name": "lcrQ2",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.Float",
          "multiple": false
        }
      },
      {
        "name": "lcrCoverage",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.Float",
          "multiple": false
        }
      },
      {
        "name": "nsfrQ1",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.Float",
          "multiple": false
        }
      },
      {
        "name": "nsfrQ2",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.Float",
          "multiple": false
        }
      },
      {
        "name": "nsfrCoverage",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.Float",
          "multiple": false
        }
      },
      {
        "name": "leverageQ1",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.Float",
          "multiple": false
        }
      },
      {
        "name": "leverageQ2",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.Float",
          "multiple": false
        }
      },
      {
        "name": "leverageCoverage",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.Float",
          "multiple": false
        }
      },
      {
        "name": "makerComment",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.String",
          "multiple": false
        }
      },
      {
        "name": "checkerComment",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.String",
          "multiple": false
        }
      },
      {
        "name": "checkerAction",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.String",
          "multiple": false
        }
      },
      {
        "name": "returnComment",
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
      "id": "field_cadea50f2a904d32",
      "name": "selectedYear",
      "label": "Year / \u0627\u0644\u0633\u0646\u0629",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.String",
      "code": "TextBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textBox.TextBoxFieldDefinition",
      "placeHolder": "",
      "binding": "selectedYear",
      "maxLength": 100
    },
    {
      "id": "field_f81358afbfe4445a",
      "name": "selectedQuarterRange",
      "label": "Quarter Range / \u0646\u0637\u0627\u0642 \u0627\u0644\u0631\u0628\u0639",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.String",
      "code": "TextBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textBox.TextBoxFieldDefinition",
      "placeHolder": "",
      "binding": "selectedQuarterRange",
      "maxLength": 100
    },
    {
      "id": "field_a9a81cc2bb4145dc",
      "name": "lcrQ1",
      "label": "LCR \u2013 Q1",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.Float",
      "code": "DecimalBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.decimalBox.DecimalBoxFieldDefinition",
      "placeHolder": "",
      "binding": "lcrQ1"
    },
    {
      "id": "field_973077d4c52f42bd",
      "name": "lcrQ2",
      "label": "LCR \u2013 Q2",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.Float",
      "code": "DecimalBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.decimalBox.DecimalBoxFieldDefinition",
      "placeHolder": "",
      "binding": "lcrQ2"
    },
    {
      "id": "field_659fa89c34e9463e",
      "name": "lcrCoverage",
      "label": "LCR \u2013 Coverage",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.Float",
      "code": "DecimalBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.decimalBox.DecimalBoxFieldDefinition",
      "placeHolder": "",
      "binding": "lcrCoverage"
    },
    {
      "id": "field_305bc1f139744dbc",
      "name": "nsfrQ1",
      "label": "NSFR \u2013 Q1",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.Float",
      "code": "DecimalBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.decimalBox.DecimalBoxFieldDefinition",
      "placeHolder": "",
      "binding": "nsfrQ1"
    },
    {
      "id": "field_110ebd9281ee42c9",
      "name": "nsfrQ2",
      "label": "NSFR \u2013 Q2",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.Float",
      "code": "DecimalBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.decimalBox.DecimalBoxFieldDefinition",
      "placeHolder": "",
      "binding": "nsfrQ2"
    },
    {
      "id": "field_975f8788259c493f",
      "name": "nsfrCoverage",
      "label": "NSFR \u2013 Coverage",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.Float",
      "code": "DecimalBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.decimalBox.DecimalBoxFieldDefinition",
      "placeHolder": "",
      "binding": "nsfrCoverage"
    },
    {
      "id": "field_af80eae8fe3e431e",
      "name": "leverageQ1",
      "label": "Leverage \u2013 Q1",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.Float",
      "code": "DecimalBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.decimalBox.DecimalBoxFieldDefinition",
      "placeHolder": "",
      "binding": "leverageQ1"
    },
    {
      "id": "field_2a3cdc4d923148f0",
      "name": "leverageQ2",
      "label": "Leverage \u2013 Q2",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.Float",
      "code": "DecimalBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.decimalBox.DecimalBoxFieldDefinition",
      "placeHolder": "",
      "binding": "leverageQ2"
    },
    {
      "id": "field_52c1a23c600a4dcd",
      "name": "leverageCoverage",
      "label": "Leverage \u2013 Coverage",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.Float",
      "code": "DecimalBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.decimalBox.DecimalBoxFieldDefinition",
      "placeHolder": "",
      "binding": "leverageCoverage"
    },
    {
      "id": "field_86e2b37d87d24b31",
      "name": "makerComment",
      "label": "Employee Comment / \u062a\u0639\u0644\u064a\u0642 \u0627\u0644\u0645\u0648\u0638\u0641",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.String",
      "code": "TextArea",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textArea.TextAreaFieldDefinition",
      "placeHolder": "",
      "binding": "makerComment",
      "maxLength": 255,
      "rows": 4
    },
    {
      "id": "field_9783de7c2b024737",
      "name": "checkerComment",
      "label": "Manager Comment / \u062a\u0639\u0644\u064a\u0642 \u0627\u0644\u0645\u062f\u064a\u0631",
      "required": false,
      "readOnly": false,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.String",
      "code": "TextArea",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textArea.TextAreaFieldDefinition",
      "placeHolder": "\u0623\u0636\u0641 \u062a\u0639\u0644\u064a\u0642\u0643 \u0647\u0646\u0627...",
      "binding": "checkerComment",
      "maxLength": 255,
      "rows": 4
    },
    {
      "id": "field_f1b384b4d4d34bc8",
      "name": "returnComment",
      "label": "Return Reason / \u0633\u0628\u0628 \u0627\u0644\u0625\u0631\u062c\u0627\u0639",
      "required": false,
      "readOnly": false,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.String",
      "code": "TextArea",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textArea.TextAreaFieldDefinition",
      "placeHolder": "\u0645\u0637\u0644\u0648\u0628 \u0639\u0646\u062f \u0627\u0644\u0625\u0631\u062c\u0627\u0639",
      "binding": "returnComment",
      "maxLength": 255,
      "rows": 4
    },
    {
      "id": "field_4babbfed665746de",
      "name": "checkerAction",
      "label": "Action",
      "required": false,
      "readOnly": false,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.String",
      "code": "TextBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textBox.TextBoxFieldDefinition",
      "placeHolder": "submit or return",
      "binding": "checkerAction",
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
                  "field_id": "field_cadea50f2a904d32",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "field_id": "field_f81358afbfe4445a",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "HTML_CODE": "<h4>Liquidity Coverage Ratio (LCR)</h4><hr/>"
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
                  "field_id": "field_a9a81cc2bb4145dc",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "field_id": "field_973077d4c52f42bd",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "field_id": "field_659fa89c34e9463e",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "HTML_CODE": "<h4>Net Stable Funding Ratio (NSFR)</h4><hr/>"
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
                  "field_id": "field_305bc1f139744dbc",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "field_id": "field_110ebd9281ee42c9",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "field_id": "field_975f8788259c493f",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "HTML_CODE": "<h4>Leverage Ratio</h4><hr/>"
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
                  "field_id": "field_af80eae8fe3e431e",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "field_id": "field_2a3cdc4d923148f0",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "field_id": "field_52c1a23c600a4dcd",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "HTML_CODE": "<h4>Comments / \u0627\u0644\u062a\u0639\u0644\u064a\u0642\u0627\u062a</h4><hr/>"
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
                  "field_id": "field_86e2b37d87d24b31",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "field_id": "field_9783de7c2b024737",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "field_id": "field_f1b384b4d4d34bc8",
                  "form_id": "form_7940b5f13cdf47c2"
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
                  "field_id": "field_4babbfed665746de",
                  "form_id": "form_7940b5f13cdf47c2"
                }
              }
            ]
          }
        ]
      }
    ]
  }
}