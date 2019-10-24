package org.ballerinalang.openapidev.utils;

import java.util.*;

/**
 * This class will contain functions to extract types from a given OpenApi object.
 */
public class OpenApiTypeExtractorUtils {

    /**
     * This method will iterate a given OpenApi object extract all types.
     *
     * @param definition - Open Api object
     * @return - returns an OpenApiObject with all extracted types

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
                final Map<PathItem.HttpMethod, Operation> operations = pathValue.readOperationsMap();
                extractTypesFromOperations(operations, pathObject);
            }

            paths.add(pathObject);
        }
    }

    /**
     * This method will extract types from Operations list.
     *
     * @param operationMap - OpenApi operations list
     * @param pathObject - will contain extracted operation types

    private static void extractTypesFromOperations(Map<PathItem.HttpMethod, Operation> operationMap, OpenApiPathObject pathObject) {
        final Iterator<Map.Entry<PathItem.HttpMethod, Operation>> operationIterator =
                operationMap.entrySet().iterator();
        List<OpenApiOperationObject> operationObjects = new ArrayList<>();
        
        //Iterate operation map to extract operation data
        while (operationIterator.hasNext()) {
            final Map.Entry<PathItem.HttpMethod, Operation> nextOperation = operationIterator.next();
            final PathItem.HttpMethod operationMethod = nextOperation.getKey();
            final Operation operationObject = nextOperation.getValue();

            /**
             * 1. Strip operation id for resource name
             * 2. If no operation id, warn
             * 3. generate custom operation id using operation method
             * 4. extract parameters
             * 5. extract responses
             * 6. extract request body


            //Create new operation type object and add type of operation
            OpenApiOperationObject operationTypeObject = new OpenApiOperationObject();
            operationTypeObject.setOperationMethod(operationMethod.toString().toLowerCase(Locale.ENGLISH));

            if (operationObject.getOperationId() !=  null && operationObject.getOperationId().isEmpty()) {
                operationTypeObject.set
            } else {

            }

        }

        while (operationIterator.hasNext()) {
            final Map.Entry<PathItem.HttpMethod, Operation> nextOperation = operationIterator.next();
            final Operation nextOperationObject = nextOperation.getValue();

            //New Operation Type Object
            OpenApiOperationObject operation = new OpenApiOperationObject();
            operation.setOperationType(nextOperation.getKey().toString());

            if (nextOperationObject.getParameters() != null && !nextOperationObject.getParameters().isEmpty()) {
                extractParameterTypes(nextOperationObject.getParameters(), operation);
            }

            if (nextOperationObject.getResponses() != null && !nextOperationObject.getResponses().isEmpty()) {
                extractResponseTypes(nextOperationObject.getResponses(), operation);
            }

            if (nextOperationObject.getRequestBody() != null) {
                //iterate request bodies
            }
        }
    }

    /**
     * This method will iterate parameters to retrieve types.
     *
     * @param parameterList - open api parameters list
     * @param operationObject - retrieved types will be added to the operation object

    private static void extractParameterTypes(List<Parameter> parameterList, OpenApiOperationObject operationObject) {
        final Iterator<Parameter> parameterIterator = parameterList.iterator();

        while (parameterIterator.hasNext()) {
            final Parameter nextParameter = parameterIterator.next();
            OpenApiParameterObject parameter = new OpenApiParameterObject();

            parameter.setParamName(OpenApiCodeGenUtils.escapeIdentifiers(nextParameter.getName(), false));
        }
    }

    /**
     * This method will iterate response types to retrieve types.
     *
     * @param responses - open api responses list
     * @param operationObject - retrieved types will be added to the operation object

    private static void  extractResponseTypes(ApiResponses responses, OpenApiOperationObject operationObject) {

    }

    /**
     * This method will iterate available components to retrieve types.
     *
     * @param components - open api components object
     * @param typeObject - retrieved types will be added to the type object

    private static void extractComponentTypes(Components components, OpenApiObject typeObject) {

    }
    */
}
