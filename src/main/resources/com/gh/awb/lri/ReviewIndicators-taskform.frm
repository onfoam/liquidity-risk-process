{
  "id": "form_5647f882965946b6",
  "name": "ReviewIndicators-taskform",
  "model": {
    "taskName": "ReviewIndicators",
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
        "name": "returnComment",
        "typeInfo": {
          "type": "BASE",
          "className": "java.lang.String",
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
        "name": "makerAction",
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
      "id": "field_cc9c1f45aaf940bf",
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
      "id": "field_e7f6bd3dba3b407a",
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
      "id": "field_e2b09cc5ca6b42cf",
      "name": "lcrQ1",
      "label": "LCR \u2013 Q1 / \u0627\u0644\u0631\u0628\u0639 \u0627\u0644\u0623\u0648\u0644",
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
      "id": "field_63d2f3b7e4b542fb",
      "name": "lcrQ2",
      "label": "LCR \u2013 Q2 / \u0627\u0644\u0631\u0628\u0639 \u0627\u0644\u062b\u0627\u0646\u064a",
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
      "id": "field_bbf65bbae10d4eee",
      "name": "lcrCoverage",
      "label": "LCR \u2013 Coverage / \u0627\u0644\u062a\u063a\u0637\u064a\u0629 \u0627\u0644\u0645\u0637\u0644\u0642\u0629",
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
      "id": "field_c20adde11f1848a1",
      "name": "nsfrQ1",
      "label": "NSFR \u2013 Q1 / \u0627\u0644\u0631\u0628\u0639 \u0627\u0644\u0623\u0648\u0644",
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
      "id": "field_7b9e859e75c14456",
      "name": "nsfrQ2",
      "label": "NSFR \u2013 Q2 / \u0627\u0644\u0631\u0628\u0639 \u0627\u0644\u062b\u0627\u0646\u064a",
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
      "id": "field_79872657253c40f8",
      "name": "nsfrCoverage",
      "label": "NSFR \u2013 Coverage / \u0627\u0644\u062a\u063a\u0637\u064a\u0629 \u0627\u0644\u0645\u0637\u0644\u0642\u0629",
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
      "id": "field_35db6dee50ed4498",
      "name": "leverageQ1",
      "label": "Leverage \u2013 Q1 / \u0627\u0644\u0631\u0628\u0639 \u0627\u0644\u0623\u0648\u0644",
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
      "id": "field_2805e088fb8a4e72",
      "name": "leverageQ2",
      "label": "Leverage \u2013 Q2 / \u0627\u0644\u0631\u0628\u0639 \u0627\u0644\u062b\u0627\u0646\u064a",
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
      "id": "field_68da8a0986034f3c",
      "name": "leverageCoverage",
      "label": "Leverage \u2013 Coverage / \u0627\u0644\u062a\u063a\u0637\u064a\u0629 \u0627\u0644\u0645\u0637\u0644\u0642\u0629",
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
      "id": "field_c6b29880e9ef420e",
      "name": "returnComment",
      "label": "Return Comment / \u0633\u0628\u0628 \u0627\u0644\u0625\u0631\u062c\u0627\u0639",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.String",
      "code": "TextArea",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textArea.TextAreaFieldDefinition",
      "placeHolder": "",
      "binding": "returnComment",
      "maxLength": 255,
      "rows": 4
    },
    {
      "id": "field_c0169fddf92f4b83",
      "name": "makerComment",
      "label": "Employee Comment / \u062a\u0639\u0644\u064a\u0642 \u0627\u0644\u0645\u0648\u0638\u0641",
      "required": false,
      "readOnly": false,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.String",
      "code": "TextArea",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textArea.TextAreaFieldDefinition",
      "placeHolder": "\u0623\u0636\u0641 \u062a\u0639\u0644\u064a\u0642\u0643 \u0647\u0646\u0627...",
      "binding": "makerComment",
      "maxLength": 255,
      "rows": 4
    },
    {
      "id": "field_6bfc518850844553",
      "name": "makerAction",
      "label": "Action",
      "required": false,
      "readOnly": false,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.String",
      "code": "TextBox",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textBox.TextBoxFieldDefinition",
      "placeHolder": "save or submit",
      "binding": "makerAction",
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
                  "field_id": "field_cc9c1f45aaf940bf",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_e7f6bd3dba3b407a",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_e2b09cc5ca6b42cf",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_63d2f3b7e4b542fb",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_bbf65bbae10d4eee",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_c20adde11f1848a1",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_7b9e859e75c14456",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_79872657253c40f8",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_35db6dee50ed4498",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_2805e088fb8a4e72",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_68da8a0986034f3c",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_c6b29880e9ef420e",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_c0169fddf92f4b83",
                  "form_id": "form_5647f882965946b6"
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
                  "field_id": "field_6bfc518850844553",
                  "form_id": "form_5647f882965946b6"
                }
              }
            ]
          }
        ]
      }
    ]
  }
}