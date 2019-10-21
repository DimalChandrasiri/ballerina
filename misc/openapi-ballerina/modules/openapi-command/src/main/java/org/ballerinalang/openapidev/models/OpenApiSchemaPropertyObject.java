package org.ballerinalang.openapidev.models;

/**
 * Open Api Schema Property Object.
 */
public class OpenApiSchemaPropertyObject {
    private String propertyName;
    private String propertyType;
    private boolean isArray;
    private boolean isRestRecord;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public boolean isRestRecord() {
        return isRestRecord;
    }

    public void setRestRecord(boolean restRecord) {
        isRestRecord = restRecord;
    }
}
