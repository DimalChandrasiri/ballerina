package org.ballerinalang.openapi.models;

import java.util.List;

/**
 * Open Api Request Body Object.
 */
public class OpenApiRequestBodyObject {
    private Boolean required;
    private String ref;
    private String type;
    private List<OpenApiSchemaObject> contentList;

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<OpenApiSchemaObject> getContentList() {
        return contentList;
    }

    public void setContentList(List<OpenApiSchemaObject> contentList) {
        this.contentList = contentList;
    }
}
