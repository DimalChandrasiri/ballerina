package org.ballerinalang.openapidev.utils;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.apache.commons.lang3.StringUtils;
import org.ballerinalang.openapidev.constants.OpenApiCommandConstants;
import org.ballerinalang.openapidev.models.*;

import java.io.PrintStream;
import java.util.*;
import java.util.Map.Entry;

/**
 * This class will extract relevant information from a given OpenApi Object
 */
public class OpenApiTypeExtractor {

    private static final PrintStream outStream = System.err;

    /**
     * Iterates given OpenApi object to extract types.
     *
     * @param definition
     * @return
     */
    public static OpenApiObject extractOpenApiType(OpenAPI definition) {
        OpenApiObject typesObject = new OpenApiObject();


        //Add paths to types object
        typesObject.setPaths(extractOpenApiPathType(definition, definition.getPaths()));

        //Add components to types object
        typesObject.setComponents(extractComponentTypes(definition.getComponents()));

        return typesObject;
    }

    private static List<OpenApiPath> extractOpenApiPathType(OpenAPI definition, Paths paths) {
        List<OpenApiPath> pathsList = new ArrayList<>();
        final Iterator<Entry<String, PathItem>> pathsIterator = paths.entrySet().iterator();

        while (pathsIterator.hasNext()) {
            OpenApiPath path = new OpenApiPath();
            final Entry<String, PathItem> nextPath = pathsIterator.next();
            final String pathName = nextPath.getKey();
            final PathItem pathObject = nextPath.getValue();

            path.setPathName(pathName);

            if (pathObject.readOperations() != null && !pathObject.readOperations().isEmpty()) {
                path.setOperations(extractOpenApiOperationsType(definition, pathObject.readOperationsMap(), pathName));
            }

            pathsList.add(path);
        }

        return pathsList;
    }

    /**
     * This method will iterate a given operation map and extract all data types.
     *
     *
     * @param definition
     * @param operationMap - Open Api Operations map
     * @param operationPath - Operation Method ( ex : GET, POST etc.. )
     * @return - returns an operations list with extracted data types
     */
    private static List<OpenApiOperation> extractOpenApiOperationsType(OpenAPI definition, Map<PathItem.HttpMethod,
            Operation> operationMap, String operationPath) {
        List<OpenApiOperation> operationsList = new ArrayList<>();
        final Iterator<Entry<PathItem.HttpMethod, Operation>> operationsIterator = operationMap.
                entrySet().iterator();

        while (operationsIterator.hasNext()) {
            final Entry<PathItem.HttpMethod, Operation> nextOperation = operationsIterator.next();
            final PathItem.HttpMethod operationMethod = nextOperation.getKey();
            final Operation operationObject = nextOperation.getValue();
            OpenApiOperation operation = new OpenApiOperation();

            operation.setOperationMethod(operationMethod.toString());

            if (operationObject.getOperationId() != null && !operationObject.getOperationId().isEmpty()) {
                String operationName = OpenApiCodeGenUtils.stripSpecialCharacters(
                        operationObject.getOperationId().replace(" ", "_"));
                operation.setOperationName(operationName);
            } else {
                String resourceName = OpenApiCommandConstants.RESOURCE_NAME_PREFIX
                        + operationMethod.toString().toLowerCase(Locale.ENGLISH) + "_"
                        + operationPath.replaceAll("/", "_")
                        .replaceAll("[{}]", "");
                operation.setOperationName(OpenApiCodeGenUtils.stripSpecialCharacters(resourceName));

                //Print a warning to user since custom resource name is used
                outStream.println("\nWarning : Operation ID missing for path " + operationPath + " operation "
                        + operationMethod + "." + "\nThe relevant ballerina resource will be mapped to "
                        + resourceName  + ".");
            }

            if (operationObject.getParameters() != null && !operationObject.getParameters().isEmpty()) {
                operation.setParameters(extractOpenApiParameterTypes(definition, operationObject.getParameters()));
            }

            //TODO extract responses

            //TODO extract request bodies

            operationsList.add(operation);
        }
        return operationsList;
    }

    /**
     * This method will extract types for a given parameter list.
     *
     * @param definition
     * @param parameters
     * @return
     */
    private static List<OpenApiParameter> extractOpenApiParameterTypes(OpenAPI definition, List<Parameter> parameters) {
        final Iterator<Parameter> parameterIterator = parameters.iterator();
        List<OpenApiParameter> paramList = new ArrayList<>();

        while (parameterIterator.hasNext()) {
            final Parameter nextParameter = parameterIterator.next();
            OpenApiParameter parameter = new OpenApiParameter();

            parameter.setParamName(nextParameter.getName().toLowerCase());
            parameter.setIn(nextParameter.getIn());

            if (nextParameter.getRequired() != null) {
                parameter.setRequired(nextParameter.getRequired());
            }

            if (nextParameter.get$ref() != null && !nextParameter.get$ref().isEmpty()) {
                String[] refArray = nextParameter.get$ref().split("/");
                parameter.setParamType(OpenApiCodeGenUtils.stripSpecialCharacters(refArray[refArray.length - 1]));
            } else if (nextParameter.getSchema() != null) {
                if (nextParameter.getSchema().getType() != null &&
                        nextParameter.getSchema().getType().equals("object")) {
                    //capitalize param name to match param type
                    String paramType = StringUtils.capitalize(nextParameter.getName());
                    parameter.setParamType(paramType);
                    addSchemaToComponents(definition, nextParameter.getSchema(), paramType);
                } else {
                    parameter.setParamType(nextParameter.getSchema().getType());
                }
            }

            paramList.add(parameter);
        }
        return paramList;
    }

    /**
     * Util method to add inline schema object to schema list in components list to process in future.
     *
     * @param definition - OpenApi definition
     * @param schema - schema needs to be added
     * @param schemaType - schema type
     */
    private static void addSchemaToComponents(OpenAPI definition, Schema schema, String schemaType) {
        if (definition.getComponents() != null && definition.getComponents().getSchemas() != null) {
            definition.getComponents().getSchemas().put(schemaType, schema);
        } else {
            if (definition.getComponents() == null) {
                Components components = new Components();
                Map<String, Schema> schemas = new HashMap<>();
                schemas.put(schemaType, schema);
                components.setSchemas(schemas);
                definition.setComponents(components);
            } else if (definition.getComponents().getSchemas() == null) {
                Map<String, Schema> schemas = new HashMap<>();
                schemas.put(schemaType, schema);
                definition.getComponents().setSchemas(schemas);
            }
        }
    }

    /**
     * This method will extract types from Components Object
     *
     * @param components
     * @return
     */
    private static OpenApiComponent extractComponentTypes(Components components) {
        OpenApiComponent component = new OpenApiComponent();
        List<OpenApiSchema> schemaList = new ArrayList<>();

        if (components.getSchemas() != null) {
            component.setSchemas(extractSchemasComponent(components.getSchemas(), schemaList));
        }

        return component;
    }

    private static List<OpenApiSchema> extractSchemasComponent(Map<String, Schema> schemaMap,
                                                               List<OpenApiSchema> schemaList) {
        final Iterator<Entry<String, Schema>> schemaIterator = schemaMap.entrySet().iterator();

        while (schemaIterator.hasNext()) {
            final Entry<String, Schema> nextSchema = schemaIterator.next();
            final String schemaName = nextSchema.getKey();
            final Schema schemaObject = nextSchema.getValue();

            schemaList.add(extractSchemaTypes(schemaObject, schemaName, schemaList));

        }
        return schemaList;
    }

    private static OpenApiSchema extractSchemaTypes(Schema schema, String schemaName, List<OpenApiSchema> schemaList) {
        OpenApiSchema typeSchema = new OpenApiSchema();
        typeSchema.setName(OpenApiCodeGenUtils.stripSpecialCharacters(schemaName));

        if (schema instanceof ObjectSchema) {
            if (schema.getProperties() != null) {
                typeSchema.setProperties(extractPropertyTypes(schema.getProperties(), schemaList));
            } else if (schema.get$ref() != null) {

            }
        } else if (schema instanceof MapSchema) {

        }

        return typeSchema;
    }

    private static List<OpenApiProperty> extractPropertyTypes(Map<String, Schema> properties, List<OpenApiSchema> schemaList) {
        List<OpenApiProperty> propertyList = new ArrayList<>();
        final Iterator propertyIterator = properties.entrySet().iterator();

        while (propertyIterator.hasNext()) {
            final Object nextProperty = propertyIterator.next();
            final String propertyName = (String) ((Entry) nextProperty).getKey();
            final Schema propertyObject = (Schema) ((Entry) nextProperty).getValue();
            OpenApiProperty property = new OpenApiProperty();

            property.setPropertyName(OpenApiCodeGenUtils.stripSpecialCharacters(propertyName));
            final String propertyObjectType = propertyObject.getType();

            if (propertyObjectType != null && propertyObjectType.equals("object")) {
                schemaList.add(extractSchemaTypes(propertyObject, StringUtils.capitalize(propertyName) , schemaList));
            } else if (propertyObjectType != null && propertyObjectType.equals("array")) {
                if (propertyObject instanceof ArraySchema) {
                    ArraySchema arrayObj = (ArraySchema) propertyObject;
                    if (arrayObj.getItems() != null && arrayObj.getItems().getType() != null) {
                        final String arrayType = arrayObj.getItems().getType();
                        property.setArray(true);
                        property.setPropertyType(getBallerinaTypeForOpenApiType(arrayType));
                    } else if (arrayObj.getItems() != null && arrayObj.getItems().get$ref() != null) {
                        String[] refArray = arrayObj.getItems().get$ref().split("/");
                        property.setPropertyType(OpenApiCodeGenUtils
                                .stripSpecialCharacters(refArray[refArray.length - 1]));
                    }
                }
            } else if (propertyObjectType == null && propertyObject.get$ref() != null) {
                String[] refArray = propertyObject.get$ref().split("/");
                property.setPropertyType(OpenApiCodeGenUtils.stripSpecialCharacters(refArray[refArray.length - 1]));
            } else if (propertyObjectType != null)  {
                property.setPropertyType(getBallerinaTypeForOpenApiType(propertyObjectType));
            }
            propertyList.add(property);
        }
        return propertyList;
    }

    /**
     * This util method will convert OpenApi types to Ballerina compatible types.
     * Note : If not supported type, a warning will be given and type any will be used as default.
     *
     * @param openApiType - Open Api Type
     * @return - returns compatible Balerina Type
     */
    private static String getBallerinaTypeForOpenApiType(String openApiType) {
        switch (openApiType) {
            case "integer":
            case "number":
                return "int";
            case "string":
                return "string";
            case "boolean":
                return "boolean";
            default:
                return "any";
        }
    }
}
