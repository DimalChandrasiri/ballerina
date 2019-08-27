package org.ballerinalang.openapi.cmd;

import org.ballerinalang.tool.BLauncherException;
import org.testng.Assert;
import org.testng.annotations.Test;
import picocli.CommandLine;

/**
 * This class contains tests necessary to test OpenApi Generate Contract command.
 * ballerina openapi gen-contract [moduleName]:serviceName [-i: ballerinaFile] [-o: contractFile] [-s: skip-bind]
 */
public class OpenApiGenContractCmdTest extends OpenAPICommandTest {

    @Test(description = "Test openapi gen-contract without any parameters providing")
    public void testWithNoParametersProvided() {
        OpenApiGenClientCmd cmd = new OpenApiGenClientCmd(printStream, tmpDir.toString());
        new CommandLine(cmd);

        String output = "";
        try {
            cmd.execute();
        } catch (BLauncherException e) {
            output = e.getDetailedMessages().get(0);
        }

        Assert.assertTrue(output.contains("Mandatory options are missing in order to generate an OpenApi contract"));
    }

    @Test(description = "Test openapi gen-contract with help option")
    public void testWithHelpOption() {}

    @Test(description = "Test openapi gen-contract without help option")
    public void testWithoutHelpOption() {}

    @Test(description = "Test openapi gen-contract without service name option")
    public void testWithoutServiceName() {}

    @Test(description = "Test openapi gen-contract by providing only the service name")
    public void testWithOnlyServiceName() {}

    @Test(description = "Test openapi gen-contract by providing service name and module name")
    public void testWithServiceNameAndModuleName() {}

    @Test(description = "Test openapi gen-contract by providing service name and ballerina file location")
    public void testWithServiceNameAndBallerinaFile() {}

    @Test(description = "Test openapi gen-contract by providing only the ballerina file")
    public void testWithOnlyBallerinaFile() {}

    @Test(description = "Test openapi gen-contract by providing only the module name")
    public void testWithOnlyModuleName() {}

    @Test(description = "Test openapi gen-contract by providing output parameter with rest of the parameters")
    public void testWithOutputParameter() {}

    @Test(description = "Test openapi gen-contract by providing only the output parameter")
    public void testWithOnlyOutputParameter() {}
}
