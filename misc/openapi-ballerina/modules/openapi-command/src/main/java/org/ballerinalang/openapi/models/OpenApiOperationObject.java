package org.ballerinalang.openapi.models;

import java.util.List;

/**
 * Open Api Operation Object.
 */
public class OpenApiOperationObject {
    private String operationType;
    private String operationName;
    private List<OpenApiParameterObject> parameters;
    private List<OpenApiResponseObject> responses;
    private OpenApiRequestBodyObject requestBody;

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public List<OpenApiParameterObject> getParameters() {
        return parameters;
    }

    public void setParameters(List<OpenApiParameterObject> parameters) {
        this.parameters = parameters;
    }

    public List<OpenApiResponseObject> getResponses() {
        return responses;
    }

    public void setResponses(List<OpenApiResponseObject> responses) {
        this.responses = responses;
    }

    public OpenApiRequestBodyObject getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(OpenApiRequestBodyObject requestBody) {
        this.requestBody = requestBody;
    }
}
