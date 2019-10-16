package org.ballerinalang.openapi.models;

import java.util.List;

/**
 * Open Api path Object.
 */
public class OpenApiPathObject {
    private String pathName;
    private List<OpenApiOperationObject> operations;

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public List<OpenApiOperationObject> getOperations() {
        return operations;
    }

    public void setOperations(List<OpenApiOperationObject> operations) {
        this.operations = operations;
    }
}
