package org.ballerinalang.openapidev.models;

import java.util.List;

/**
 * Open Api Schema Object.
 */
public class OpenApiSchemaObject {
    private String schemaName;
    private String schemaType;
    private String reference;
    private String itemType;
    private String itemName;
    private String unescapedItemName;
    private List<String> required;
    private List<OpenApiSchemaPropertyObject> schemaProperties;
    private boolean isArray;

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSchemaType() {
        return schemaType;
    }

    public void setSchemaType(String schemaType) {
        this.schemaType = schemaType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnescapedItemName() {
        return unescapedItemName;
    }

    public void setUnescapedItemName(String unescapedItemName) {
        this.unescapedItemName = unescapedItemName;
    }

    public List<String> getRequired() {
        return required;
    }

    public void setRequired(List<String> required) {
        this.required = required;
    }

    public List<OpenApiSchemaPropertyObject> getSchemaProperties() {
        return schemaProperties;
    }

    public void setSchemaProperties(List<OpenApiSchemaPropertyObject> schemaProperties) {
        this.schemaProperties = schemaProperties;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }
}
