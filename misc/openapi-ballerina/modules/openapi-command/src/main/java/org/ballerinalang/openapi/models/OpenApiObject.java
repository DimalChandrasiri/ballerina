package org.ballerinalang.openapi.models;

import io.swagger.v3.oas.models.tags.Tag;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains OpenApi information to generate Ballerina Service.
 */
public class OpenApiObject {
    private String moduleName;
    private String serviceName;
    private String definitionPath;
    private List<OpenApiPathObject> paths;
    private OpenApiComponentObject component;
    private List<Tag> tags;
    private List<OpenApiServerObject> servers = new ArrayList<>();

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDefinitionPath() {
        return definitionPath;
    }

    public void setDefinitionPath(String definitionPath) {
        this.definitionPath = definitionPath;
    }

    public List<OpenApiServerObject> getServers() {
        return servers;
    }

    public void setServers(List<OpenApiServerObject> servers) {
        this.servers = servers;
    }

    public List<OpenApiPathObject> getPaths() {
        return paths;
    }

    public void setPaths(List<OpenApiPathObject> paths) {
        this.paths = paths;
    }

    public OpenApiComponentObject getComponent() {
        return component;
    }

    public void setComponent(OpenApiComponentObject component) {
        this.component = component;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
