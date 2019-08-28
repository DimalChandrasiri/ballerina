/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.openapi;

/**
 * This class contains the messages constants required for OpenApi tool.
 */
public class OpenApiMesseges {

    public static final String CLIENT_MANDATORY = "Client name is mandatory to generate the ballerina client. " +
            "\nE.g. ballerina openapi gen-client [<module>]:<servicename> <openapicontract>";
    public static final String OPENAPI_FILE_MANDATORY = "An OpenApi definition file is required to " +
            "generate the client. \nE.g: ballerina openapi gen-client [<module>]:<servicename> <OpenApiContract>";
    public static final String OPENAPI_CLIENT_EXCEPTION = "Error occurred when generating client for openapi contract";


    public static final String GEN_CONTRACT_PARAM_MANDATORY = "Mandatory options are missing in order to generate" +
            " an OpenApi contract. The command should match the following. \nballerina openapi gen-contract " +
            "[<module-name>]:<service-name> -i <ballerina-file>";
    public static final String GEN_CONTRACT_BALLERINA_DOC_MANDATORY = "Ballerina document location is not provided " +
            "to generate the OpenApi contract. The command should match the following. \nballerina openapi " +
            "gen-contract [<module-name>]:<service-name> -i <ballerina-file>";
    public static final String GEN_CONTRACT_BALLERINA_MODULE_NOT_FOUND = "The module is not found in the location " +
            "provided. Please specify a valid ballerina module.";



    public static final String GEN_SERVICE_MODULE_ARGS_REQUIRED = "A module name and a service name is required " +
            "in order to generate the ballerina service for the provided OpenApi contract. \nE.g. ballerina " +
            "openapi gen-service <module_name>:<service_name> <openapi_contract>";
    public static final String GEN_SERVICE_MODULE_REQUIRED = "A module name is required in order to generate the " +
            "ballerina service for the provided OpenApi contract. \nE.g. ballerina openapi gen-service " +
            "<module_name>:<service_name> <openapi_contract>";
    public static final String GEN_SERVICE_SERVICE_NAME_REQUIRED = "A service name is required in order to generate " +
            "the ballerina service for the provided OpenApi contract. \nE.g. ballerina openapi gen-service " +
            "<module_name>:<service_name> <openapi_contract>";
    public static final String GEN_SERVICE_PROJECT_ROOT = "Ballerina service generation should be done from the " +
                                                          "project root. \nIf you like to start with a new project " +
                                                          "use `ballerina new` command to create a new project.";
    public static final String MODULE_DIRECTORY_EXCEPTION = "Unable to create module directory. File system error " +
                                                            "occured.";
    public static final String RESOURCE_DIRECTORY_EXCEPTION = "Unable to create resource directory. File system error" +
                                                            " occured.";
    public static final String TESTS_DIRECTORY_EXCEPTION = "Unable to create tests directory. File system error " +
                                                           "occured";
    public static final String SOURCE_DIRECTORY_EXCEPTION = "Unable to create source directory. File system error " +
                                                            "occured.";
    public static final String MODULE_MD_EXCEPTION = "Unable to create moudle.md file. File system error occured.";
    public static final String DEFINITION_EXISTS = "The location provided already contains an OpenApi contract " +
            "imilar to the provided contract.";

    private OpenApiMesseges() {
        throw new AssertionError();
    }

}
