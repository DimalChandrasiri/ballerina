package org.ballerinalang.openapidev.command;

import org.ballerinalang.openapidev.constants.OpenApiCommandConstants;
import org.ballerinalang.tool.BLauncherCmd;
import picocli.CommandLine;

import java.io.PrintStream;

/**
 * Main class to implement "openapi" command for ballerina.
 * This class will accept sub-commands and execute the relevant sub-command class as given to the sub-commands
 * parameter.
 *
 * Command usage will change according to the sub-command.
 */
@CommandLine.Command(
    name = OpenApiCommandConstants.OPENAPI_COMMAND,
    description = "Generate ballerina service/client using an OpenApi definition  or export an OpenApi contract " +
            "for given Ballerina service.",
    subcommands = {
        OpenApiGenServiceCommand.class,
        OpenApiGenClientCommand.class,
        OpenApiGenContractCommand.class
    }
)
public class OpenApiCommand implements BLauncherCmd {
    private PrintStream outStream;

    @CommandLine.Option(
        names = {
            OpenApiCommandConstants.HELP_COMMAND_SHORT,
            OpenApiCommandConstants.HELP_COMMAND_LONG
        },
        hidden = true
    )
    private boolean helpFlag;

    public OpenApiCommand() {
        this.outStream = System.err;
    }

    public OpenApiCommand(PrintStream outStream) {
        this.outStream = outStream;
    }

    @Override
    public void execute() {
        String commandUsageInfo = BLauncherCmd.getCommandUsageInfo(getName());
        outStream.println(commandUsageInfo);
    }

    @Override
    public String getName() {
        return OpenApiCommandConstants.OPENAPI_COMMAND;
    }

    @Override
    public void printLongDesc(StringBuilder out) {
    }

    @Override
    public void printUsage(StringBuilder stringBuilder) {
    }

    @Override
    public void setParentCmdParser(CommandLine parentCmdParser) {
    }
}
