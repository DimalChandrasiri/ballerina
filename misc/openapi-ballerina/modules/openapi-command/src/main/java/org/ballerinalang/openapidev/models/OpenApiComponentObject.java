package org.ballerinalang.openapidev.models;

import java.util.List;

/**
 * OpenApi Component Type Object.
 */
public class OpenApiComponentObject {
    private List<OpenApiSchemaObject> schemaTypes;
    private List<OpenApiSchemaObject> parameterTypes;

    public List<OpenApiSchemaObject> getSchemaTypes() {
        return schemaTypes;
    }

    public void setSchemaTypes(List<OpenApiSchemaObject> schemaTypes) {
        this.schemaTypes = schemaTypes;
    }

    public List<OpenApiSchemaObject> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<OpenApiSchemaObject> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
