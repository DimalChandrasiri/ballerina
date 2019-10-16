package org.ballerinalang.openapi.models;

/**
 * Open Api Parameter Object.
 */
public class OpenApiParameterObject {
    private boolean isPathParam;
    private boolean isQueryParam;
    private String paramName;
    private OpenApiSchemaObject paramType;
    private boolean isLastParameter;

    public boolean isPathParam() {
        return isPathParam;
    }

    public void setPathParam(boolean pathParam) {
        isPathParam = pathParam;
    }

    public boolean isQueryParam() {
        return isQueryParam;
    }

    public void setQueryParam(boolean queryParam) {
        isQueryParam = queryParam;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public OpenApiSchemaObject getParamType() {
        return paramType;
    }

    public void setParamType(OpenApiSchemaObject paramType) {
        this.paramType = paramType;
    }

    public boolean isLastParameter() {
        return isLastParameter;
    }

    public void setLastParameter(boolean lastParameter) {
        isLastParameter = lastParameter;
    }
}
