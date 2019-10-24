package org.ballerinalang.openapidev.constants;

/**
 * This class containts messages related to OpenApi command tool.
 */
public class OpenApiCommandMessages {
    public static final String EXPERIMENTAL_FEATURE = "\nNOTE :This is an experimental tool, which only supports a " +
            "limited set of functionality. \n";

    /**
     * Service Gen Constants.
     */
    public static final String INVALID_SERVICE_GEN_LOCATION = "Ballerina service generation for an OpenApi contract" +
            " should be done inside a valid Ballerina project. To create a new Ballerina project," +
            " please use `ballerina new` command.";
    public static final String NULL_SERVICE_ARGS = "Ballerina service generation process requires a module name and" +
            " a service name to proceed. \nE.g. ballerina openapi gen-service <module-name>:<service-name> " +
            "<openapi-contract>";
    public static final String SINGLE_SERVICE_ARG = "Ballerina service generation process requires both module name " +
            "and a service name to proceed. \nE.g. ballerina openapi gen-service <module-name>:<service-name> " +
            "<openapi-contract>";
    public static final String MODULE_NAME_REQUIRED = "Ballerina service generation process requires a module name to" +
            " proceed. \nE.g. ballerina openapi gen-service <module-name>:<service-name> <openapi-contract>";
    public static final String INVALID_ARGS = "Values provided for module name or service name contains invalid " +
            "characters. Please remove them and re-run the process.";
    public static final String CONTRACT_REQUIRED = "Ballerina service generation process requires a valid OpenApi" +
            " contract.";
    public static final String CONTRACT_NOT_EXIST = "OpenApi contract does not exist in the provided location. Please" +
            " check if the path to contract file is correct.";
    public static final String CONTRACT_IS_NOT_FILE = "Provided OpenApi contract is not a valid file. Please provide " +
            "a valid OpenApi contract file.";
    public static final String UNHANDLED_SERVICE_GEN_ERROR = "Unhandled error occurred while generating Ballerina " +
            "service for the provided OpenApi contract.";

}
