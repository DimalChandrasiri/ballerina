package org.ballerinalang.openapidev.utils;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.ballerinalang.openapidev.models.OpenApiObject;
import org.ballerinalang.openapidev.models.OpenApiOperationObject;
import org.ballerinalang.openapidev.models.OpenApiParameterObject;
import org.ballerinalang.openapidev.models.OpenApiPathObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class will contain functions to extract types from a given OpenApi object.
 */
public class OpenApiTypeExtractorUtils {

    /**
     * This method will iterate a given OpenApi object extract all types.
     *
     * @param definition - Open Api object
     * @return - returns an OpenApiObject with all extracted types
     */
    public static OpenApiObject extractOpenApiTypes(OpenAPI definition) {
        OpenApiObject typeObject = new OpenApiObject();

        extractTypesFromPaths(definition.getPaths(), typeObject);
        extractComponentTypes(definition.getComponents(), typeObject);

        return null;
    }

    /**
     * This method will iterate available paths, and retrieve the types.
     *
     * @param pathList - path list from Open Api Object
     * @param typeObject - retrieved types will be added to the type object
     */
    private static void extractTypesFromPaths(Paths pathList, OpenApiObject typeObject) {
        final Iterator<Map.Entry<String, PathItem>> pathIterator = pathList.entrySet().iterator();
        List<OpenApiPathObject> paths = new ArrayList<>();

        while (pathIterator.hasNext()) {
            final Map.Entry<String, PathItem> nextPath = pathIterator.next();
            final String pathKey = nextPath.getKey();
            final PathItem pathValue = nextPath.getValue();

            OpenApiPathObject pathObject = new OpenApiPathObject();
            pathObject.setPathName(pathKey);

            if (!nextPath.getValue().readOperations().isEmpty()) {
                final List<Operation> operations = pathValue.readOperations();
                extractTypesFromOperations(operations, pathObject);
            }

            paths.add(pathObject);
        }
    }

    /**
     * This method will extract types from Operations list.
     *
     * @param operationsList - OpenApi operations list
     * @param pathObject - will contain extracted operation types
     */
    private static void extractTypesFromOperations(List<Operation> operationsList, OpenApiPathObject pathObject) {
        final Iterator<Operation> operationIterator = operationsList.iterator();
        List<OpenApiOperationObject> operationObjects = new ArrayList<>();

        while (operationIterator.hasNext()) {
            final Operation nextOperation = operationIterator.next();
            OpenApiOperationObject operation = new OpenApiOperationObject();

            if (nextOperation.getParameters() != null && !nextOperation.getParameters().isEmpty()) {
                extractParameterTypes(nextOperation.getParameters(), operation);
            }

            if (nextOperation.getResponses() != null && !nextOperation.getResponses().isEmpty()) {
                extractResponseTypes(nextOperation.getResponses(), operation);
            }

            if (nextOperation.getRequestBody() != null) {
                //iterate request bodies
            }
        }
    }

    /**
     * This method will iterate parameters to retrieve types
     *
     * @param parameterList - open api parameters list
     * @param operationObject - retrieved types will be added to the operation object
     */
    private static void extractParameterTypes(List<Parameter> parameterList, OpenApiOperationObject operationObject) {
        final Iterator<Parameter> parameterIterator = parameterList.iterator();

        while (parameterIterator.hasNext()) {
            final Parameter nextParameter = parameterIterator.next();
            OpenApiParameterObject parameter = new OpenApiParameterObject();

            parameter.setParamName(OpenApiCodeGenUtils.escapeIdentifiers(nextParameter.getName(), false));
        }
    }

    /**
     * This method will iterate response types to retrieve types
     *
     * @param responses - open api responses list
     * @param operationObject - retrieved types will be added to the operation object
     */
    private static void  extractResponseTypes(ApiResponses responses, OpenApiOperationObject operationObject) {

    }

    /**
     * This method will iterate available components to retrieve types.
     *
     * @param components - open api components object
     * @param typeObject - retrieved types will be added to the type object
     */
    private static void extractComponentTypes(Components components, OpenApiObject typeObject) {

    }
}
