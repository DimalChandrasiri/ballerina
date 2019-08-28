package org.ballerinalang.openapi.cmd;

import org.ballerinalang.tool.BLauncherException;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class contains tests necessary to test OpenApi Generate Contract command.
 * ballerina openapi gen-contract [moduleName]:serviceName [-i: ballerinaFile] [-o: contractFile] [-s: skip-bind]
 */
public class OpenApiGenContractCmdTest extends OpenAPICommandTest {
    private static final Path RES_DIR = Paths.get("src/test/resources/").toAbsolutePath();

    @BeforeTest(description = "This will create a new ballerina project for testing below scenarios.")
    public void setupBallerinaProject() throws IOException {
        super.setup();
    }

    @Test(description = "Test openapi gen-contract without any parameters providing")
    public void testWithNoParametersProvided() {
        OpenApiGenContractCmd cmd = new OpenApiGenContractCmd(printStream, tmpDir.toString());
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
    public void testWithHelpOption() throws IOException {
        String[] args = {"-h"};
        OpenApiGenContractCmd cmd = new OpenApiGenContractCmd(printStream, tmpDir.toString());
        new CommandLine(cmd).parseArgs(args);

        String output = "";
        try {
            cmd.execute();
            output = readOutput(true);
        } catch (BLauncherException e) {
            output = e.getDetailedMessages().get(0);
        }

        Assert.assertTrue(output.contains("Ballerina OpenApi - Gen Contract is a tool which will generate an " +
                "OpenApi contract for a given Ballerina service."));
    }

    @Test(description = "Test openapi gen-contract without service name option")
    public void testWithoutServiceName() {

    }

    @Test(description = "Test openapi gen-contract by providing only the service name")
    public void testWithOnlyServiceName() throws IOException {
        Path petstoreBal = RES_DIR.resolve(Paths.get("expected_gen", "petstore_gen.bal"));
        String[] args = {"OpenApiPetstore", "-i " + petstoreBal.toString()};
        OpenApiGenContractCmd cmd = new OpenApiGenContractCmd(printStream, tmpDir.toString());
        new CommandLine(cmd).parseArgs(args);

        String output = "";
        try {

            Stream<String> expectedServiceLines = Files.lines(petstoreBal);
            String expectedServiceContent = expectedServiceLines.collect(Collectors.joining(System.lineSeparator()));
            expectedServiceLines.close();

            cmd.execute();
            output = readOutput(true);
        } catch (BLauncherException e) {
            output = e.getDetailedMessages().get(0);
        }

        Assert.assertTrue(output.contains("Ballerina OpenApi - Gen Contract is a tool which will generate an " +
                "OpenApi contract for a given Ballerina service."));
    }

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
