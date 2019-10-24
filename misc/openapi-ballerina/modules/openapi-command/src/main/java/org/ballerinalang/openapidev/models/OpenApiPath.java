package org.ballerinalang.openapidev.models;

import java.util.List;

/**
 * This class will represent extracted path data from an OpenApi Object.
 */
public class OpenApiPath {
    String pathName;
    List<OpenApiOperation> operations;

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public List<OpenApiOperation> getOperations() {
        return operations;
    }

    public void setOperations(List<OpenApiOperation> operations) {
        this.operations = operations;
    }
}
