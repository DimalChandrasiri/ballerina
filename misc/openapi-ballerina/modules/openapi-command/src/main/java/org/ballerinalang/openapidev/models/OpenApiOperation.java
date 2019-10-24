package org.ballerinalang.openapidev.models;

import java.util.List;

public class OpenApiOperation {
    String operationMethod;
    String operationName;
    List<OpenApiParameter> parameters;

    public List<OpenApiParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<OpenApiParameter> parameters) {
        this.parameters = parameters;
    }

    public String getOperationMethod() {
        return operationMethod;
    }

    public void setOperationMethod(String operationMethod) {
        this.operationMethod = operationMethod;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }
}
