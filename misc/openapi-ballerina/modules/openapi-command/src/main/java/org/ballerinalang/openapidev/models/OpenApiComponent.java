package org.ballerinalang.openapidev.models;

import java.util.List;

public class OpenApiComponent {
    List<OpenApiSchema> schemas;

    public List<OpenApiSchema> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<OpenApiSchema> schemas) {
        this.schemas = schemas;
    }
}
