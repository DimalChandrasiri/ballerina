package org.ballerinalang.openapidev.command;

import org.ballerinalang.openapidev.constants.OpenApiCommandConstants;
import org.ballerinalang.tool.BLauncherCmd;
import picocli.CommandLine;

/**
 * This class will implement the "openapi" sub-command "gen-client" for Ballerina OpenApi tool.
 *
 * Ex: ballerina openapi (gen-client) [moduleName]:clientName -o[output directory name]
 */
@CommandLine.Command(
    name = OpenApiCommandConstants.OPENAPI_GEN_CLIENT_COMMAND
)
public class OpenApiGenClientCommand implements BLauncherCmd {
    @Override
    public void execute() {

    }

    @Override
    public String getName() {
        return null;
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
