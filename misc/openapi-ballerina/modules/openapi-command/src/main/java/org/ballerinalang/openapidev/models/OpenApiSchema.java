package org.ballerinalang.openapidev.models;

import java.util.List;

public class OpenApiSchema {
    private String name;
    private String type;
    private String reference;
    private List<OpenApiProperty> properties;

    public List<OpenApiProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<OpenApiProperty> properties) {
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
