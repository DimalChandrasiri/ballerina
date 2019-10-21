package org.ballerinalang.openapidev.command;

import org.ballerinalang.openapidev.constants.OpenApiCommandConstants;
import org.ballerinalang.openapidev.constants.OpenApiCommandMessages;
import org.ballerinalang.openapidev.service.OpenApiServiceGenerator;
import org.ballerinalang.openapidev.utils.OpenApiCodeGenUtils;
import org.ballerinalang.tool.BLauncherCmd;
import org.ballerinalang.tool.LauncherUtils;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class contains implementation logic for the ballerina command "openapi gen-service".
 *
 * Ex: ballerina openapi gen-service moduleName:serivceName openapi-contract [-c: skip-copy-contract] [-o: outputFile]
 */
@CommandLine.Command(
    name = OpenApiCommandConstants.OPENAPI_GEN_SERVICE_COMMAND
)
public class OpenApiGenServiceCommand implements BLauncherCmd {
    private PrintStream printStream;
    private String executionPath;

    @CommandLine.Parameters(
        index = "0",
        split = ":",
        description = "Captures module name and service name as a whole string and split with `:`"
    )
    private List<String> serviceArgs;

    @CommandLine.Parameters(
        index = "1..*",
        description = "Captures arguments required to generate the Ballerina service"
    )
    private List<String> commandArgs;

    @CommandLine.Option(
        names = {"-c", "--skip-copy-contract"},
        description = "Captures user confirmation to skip copying the contract to ballerina project"
    )
    boolean skipCopyContract = false;

    @CommandLine.Option(
        names = {OpenApiCommandConstants.OUTPUT_LOCATION_SHORT, OpenApiCommandConstants.OUTPUT_LOCATION_LONG},
        description = "Captures where to write the generated files (current dir by default)"
    )
    private String outputLocation = "";

    @CommandLine.Option(
        names = {OpenApiCommandConstants.HELP_COMMAND_SHORT, OpenApiCommandConstants.HELP_COMMAND_LONG},
        hidden = true,
        description = "Captures whether to print the command help documentation"
    )
    private boolean showHelp = false;

    //Constructor to use the current user dir as execution point
    public OpenApiGenServiceCommand() {
        printStream = System.err;
        executionPath = System.getProperty(OpenApiCommandConstants.CURRENT_DIR);
    }

    //Constructor to use a custom location as the execution point
    public OpenApiGenServiceCommand(PrintStream outStream, String exePath) {
        printStream = outStream;
        executionPath = exePath;
    }

    /**
     * 1. notify experimental feature - done
     * 2. if help flag show help and exit
     * 3. check if service arguements are passed properly
     * 4. check if OpenApi contract location is passed properly
     * 5. check if additional properties are passed
     * 6. check if location is bal project root
     * 7. create module structure
     * 8. copy contract if skip-copy is false
     * 9. generate service
     */

    @Override
    public void execute() {
        OpenApiServiceGenerator serviceGenerator = new OpenApiServiceGenerator();
        File openapiContract;

        //Notify user that OpenApi tool is experimental
        printStream.println(OpenApiCommandMessages.EXPERIMENTAL_FEATURE);

        //Print command line usage information
        if (showHelp) {
            String commandUsageInfo = BLauncherCmd.getCommandUsageInfo("openapi-" + getName());
            printStream.println(commandUsageInfo);
            return;
        }

        //Validate service arguments
        if (null == serviceArgs || serviceArgs.size() == 0) {
            throw LauncherUtils.createLauncherException(OpenApiCommandMessages.NULL_SERVICE_ARGS);
        } else if (serviceArgs.size() == 1) {
            throw LauncherUtils.createLauncherException(OpenApiCommandMessages.SINGLE_SERVICE_ARG);
        } else if (serviceArgs.size() == 2 && serviceArgs.get(0).isEmpty()) {
            throw LauncherUtils.createLauncherException(OpenApiCommandMessages.MODULE_NAME_REQUIRED);
        } else if (!serviceArgs.get(0).matches("[A-Za-z0-9]+")
                || !serviceArgs.get(1).matches("[A-Za-z0-9]+")) {
            throw LauncherUtils.createLauncherException(OpenApiCommandMessages.INVALID_ARGS);
        }

        //Validate command arguments and contract location
        if (commandArgs == null) {
            throw LauncherUtils.createLauncherException(OpenApiCommandMessages.CONTRACT_REQUIRED);
        }

        String moduleName = serviceArgs.get(0);
        String serviceName = serviceArgs.get(1);
        openapiContract = new File(commandArgs.get(0));

        if (!openapiContract.exists()) {
            throw LauncherUtils.createLauncherException(OpenApiCommandMessages.CONTRACT_NOT_EXIST);
        } else if (!openapiContract.isFile()) {
            throw LauncherUtils.createLauncherException(OpenApiCommandMessages.CONTRACT_IS_NOT_FILE);
        }

        //Check if not in a ballerina project
        if (!OpenApiCodeGenUtils.isBallerinaProject(Paths.get(executionPath))) {
            throw LauncherUtils.createLauncherException(OpenApiCommandMessages.INVALID_SERVICE_GEN_LOCATION);
        }

        //Generate Ballerina Service
        try {
            serviceGenerator.generateService(serviceName, openapiContract.toString(), outputLocation);
        } catch (IOException e) {
            throw LauncherUtils.createLauncherException(OpenApiCommandMessages.UNHANDLED_SERVICE_GEN_ERROR
                    + "\nContract Location : " + openapiContract.getPath() + "\n" + e.getMessage());
        }
    }

    @Override
    public String getName() {
        return OpenApiCommandConstants.OPENAPI_GEN_SERVICE_COMMAND;
    }

    @Override
    public void printLongDesc(StringBuilder out) {

    }

    @Override
    public void printUsage(StringBuilder out) {

    }

    @Override
    public void setParentCmdParser(CommandLine parentCmdParser) {

    }
}
