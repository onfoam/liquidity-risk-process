{
  "id": "form_de65c18053f2438d",
  "name": "FinalApproval-taskform",
  "model": {
    "taskName": "FinalApproval",
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
      }
    ]
  },
  "fields": [
    {
      "id": "field_f2c915d860424201",
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
      "id": "field_03bb6e3f49294744",
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
      "id": "field_0e5dd4c29a2b4e4e",
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
      "id": "field_b3116d7a89a64ad2",
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
      "id": "field_6cf60fd62963454a",
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
      "id": "field_95c0000d1f994805",
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
      "id": "field_98302e377f9a4501",
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
      "id": "field_fab30bab20834c9e",
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
      "id": "field_2bff5039983f46ac",
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
      "id": "field_738a0d3916394254",
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
      "id": "field_81a95711f5ce445e",
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
      "id": "field_9877607085bd4286",
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
      "id": "field_0653dd57bafc46d3",
      "name": "checkerComment",
      "label": "Manager Comment / \u062a\u0639\u0644\u064a\u0642 \u0627\u0644\u0645\u062f\u064a\u0631",
      "required": false,
      "readOnly": true,
      "validateOnChange": true,
      "standaloneClassName": "java.lang.String",
      "code": "TextArea",
      "serializedFieldClassName": "org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textArea.TextAreaFieldDefinition",
      "placeHolder": "",
      "binding": "checkerComment",
      "maxLength": 255,
      "rows": 4
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
                  "field_id": "field_f2c915d860424201",
                  "form_id": "form_de65c18053f2438d"
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
                  "field_id": "field_03bb6e3f49294744",
                  "form_id": "form_de65c18053f2438d"
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
                  "field_id": "field_0e5dd4c29a2b4e4e",
                  "form_id": "form_de65c18053f2438d"
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
                  "field_id": "field_b3116d7a89a64ad2",
                  "form_id": "form_de65c18053f2438d"
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
                  "field_id": "field_6cf60fd62963454a",
                  "form_id": "form_de65c18053f2438d"
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
                  "field_id": "field_95c0000d1f994805",
                  "form_id": "form_de65c18053f2438d"
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
                  "field_id": "field_98302e377f9a4501",
                  "form_id": "form_de65c18053f2438d"
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
                  "field_id": "field_fab30bab20834c9e",
                  "form_id": "form_de65c18053f2438d"
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
                  "field_id": "field_2bff5039983f46ac",
                  "form_id": "form_de65c18053f2438d"
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
                  "field_id": "field_738a0d3916394254",
                  "form_id": "form_de65c18053f2438d"
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
                  "field_id": "field_81a95711f5ce445e",
                  "form_id": "form_de65c18053f2438d"
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
                  "field_id": "field_9877607085bd4286",
                  "form_id": "form_de65c18053f2438d"
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
                  "field_id": "field_0653dd57bafc46d3",
                  "form_id": "form_de65c18053f2438d"
                }
              }
            ]
          }
        ]
      }
    ]
  }
}