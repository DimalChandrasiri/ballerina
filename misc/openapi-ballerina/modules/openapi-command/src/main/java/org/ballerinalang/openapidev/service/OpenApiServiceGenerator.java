package org.ballerinalang.openapidev.service;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.ballerinalang.openapidev.constants.OpenApiServiceGeneratorConstants;
import org.ballerinalang.openapidev.models.GeneratedBalFile;
import org.ballerinalang.openapidev.models.OpenApiObject;
import org.ballerinalang.openapidev.utils.OpenApiCodeGenUtils;
import org.ballerinalang.openapidev.utils.OpenApiTypeExtractorUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * This class generates Ballerina Service for OpenApi Contract.
 */
public class OpenApiServiceGenerator {
    private String balSourcePackage;
    private PrintStream outputStream = System.err;

    /**
     * Generates Ballerina source for the given OpenApi contract and write to the output location.
     *
     * @param serviceName - Custom service name
     * @param contractPath - Open Api contract path
     * @param outputPath - Where the generated files should be saved
     * @throws IOException - exception if read error
     */
    public void generateService(String serviceName, String contractPath, String outputPath) throws IOException {
        List<GeneratedBalFile> generatedFiles = generateBallerinaSource(serviceName, contractPath);

        Path srcPath = OpenApiCodeGenUtils.getSourcePath(balSourcePackage, outputPath);
        Path implPath = OpenApiCodeGenUtils.getImplPath(balSourcePackage, srcPath);

        writeGeneratedSources(generatedFiles, srcPath, implPath);
    }

    private List<GeneratedBalFile> generateBallerinaSource(String serviceName, String contractPath) {
        OpenAPI definition = new OpenAPIV3Parser().read(contractPath);

        if (definition == null) {
            //Throw exception if parse is not working
        }

        final OpenApiObject definitionObject = OpenApiTypeExtractorUtils.extractOpenApiTypes(definition);

        return null;
    }

    private void writeGeneratedSources(List<GeneratedBalFile> sources, Path srcPath, Path implPath) throws IOException {
        // Remove old generated files - if any - before regenerate
        // if srcPackage was not provided and source was written to main package nothing will be deleted.
        if (balSourcePackage != null && !balSourcePackage.isEmpty() && Files.exists(srcPath)) {
            final File[] listFiles = new File(String.valueOf(srcPath)).listFiles();
            if (listFiles != null) {
                Arrays.stream(listFiles).forEach(file -> {
                    boolean deleteStatus = true;
                    if (!file.isDirectory() && !file.getName().equals(OpenApiServiceGeneratorConstants.MODULE_MD)) {
                        deleteStatus = file.delete();
                    }

                    //Capture return value of file.delete() since if
                    //unable to delete returns false from file.delete() without an exception.
                    if (!deleteStatus) {
                        outputStream.println("Unable to clean module directory.");
                    }
                });
            }
        }

        for (GeneratedBalFile file : sources) {
            Path filePath;
            // We only overwrite files of overwritable type.
            // So non overwritable files will be written to disk only once.
            if (!file.getType().isOverwritable()) {
                filePath = implPath.resolve(file.getFileName());
                if (Files.notExists(filePath)) {
                    OpenApiCodeGenUtils.writeFile(filePath, file.getContent());
                }
            } else {
                filePath = srcPath.resolve(file.getFileName());
                OpenApiCodeGenUtils.writeFile(filePath, file.getContent());
            }
        }
    }


}
