package org.ballerinalang.openapi.models;

/**
 * Open Api Response Object.
 */
public class OpenApiResponseObject {
    private String responseCode;
    private String responseType;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
}
