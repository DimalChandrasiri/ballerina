package org.ballerinalang.openapidev.models;

import java.util.List;

/**
 * This class will represent extracted OpenApi object types
 */
public class OpenApiObject {
    String serviceName;
    String contractPath;
    OpenApiComponent components;
    List<OpenApiPath> paths;

    public OpenApiComponent getComponents() {
        return components;
    }

    public void setComponents(OpenApiComponent components) {
        this.components = components;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getContractPath() {
        return contractPath;
    }

    public void setContractPath(String contractPath) {
        this.contractPath = contractPath;
    }

    public List<OpenApiPath> getPaths() {
        return paths;
    }

    public void setPaths(List<OpenApiPath> paths) {
        this.paths = paths;
    }
}
