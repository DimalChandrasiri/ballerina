package org.ballerinalang.openapi.command;

import org.ballerinalang.openapi.constants.OpenApiCommandConstants;
import org.ballerinalang.tool.BLauncherCmd;
import picocli.CommandLine;

/**
 * This class will implement the "openapi" sub-command "gen-contract" for Ballerina OpenApi tool.
 *
 * Ex: ballerina openapi gen-contract [moduleName]:serviceName [-i: ballerinaFile] [-o: contractFile] [-s: skip-bind]
 */
@CommandLine.Command(
    name = OpenApiCommandConstants.OPENAPI_GEN_CONTRACT_COMMAND
)
public class OpenApiGenContractCommand implements BLauncherCmd {
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
