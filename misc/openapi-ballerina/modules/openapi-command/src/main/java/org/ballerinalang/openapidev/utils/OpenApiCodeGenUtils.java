package org.ballerinalang.openapidev.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.ballerinalang.openapidev.constants.OpenApiCommandConstants;

/**
 * Util methods used for OpenApi Code Generation.
 */
public class OpenApiCodeGenUtils {

    /**
     * Is {@code path} is a valid ballerina project directory.
     *
     * @param path path to suspecting dir
     * @return if {@code path} is a ballerina project directory or not
     */
    public static boolean isBallerinaProject(Path path) {
        boolean isProject = false;
        Path cachePath = path.resolve("Ballerina.toml");

        //Ballerina.toml path should exist in ballerina project directory
        if (Files.exists(cachePath)) {
            isProject = true;
        }

        return isProject;
    }

    /**
     * Resolves path to write generated main source files.
     *
     * @param pkg  module
     * @param path output path without module name
     * @return path to write generated source files
     */
    public static Path getSourcePath(String pkg, String path) {
        return (pkg == null || pkg.isEmpty()) ?
                Paths.get(path).resolve("src") :
                Paths.get(path).resolve("src").resolve(Paths.get(pkg));
    }

    /**
     * Resolves path to write generated implementation source files.
     *
     * @param pkg     module
     * @param srcPath resolved path for main source files
     * @return path to write generated source files
     */
    public static Path getImplPath(String pkg, Path srcPath) {
        return (pkg == null || pkg.isEmpty()) ? srcPath : srcPath.getParent();
    }

    /**
     * Writes a file with content to specified {@code filePath}.
     *
     * @param filePath valid file path to write the content
     * @param content  content of the file
     * @throws IOException when a file operation fails
     */
    public static void writeFile(Path filePath, String content) throws IOException {
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(filePath.toString(), "UTF-8");
            writer.print(content);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * This will escape special characters in ballerina identifiers.
     *
     * @param identifier - identifier string
     * @param isVar - is a variable name or type
     * @return escaped string
     */
    public static String escapeIdentifiers(String identifier, boolean isVar) {
        if (identifier.matches("^[a-zA-Z0-9_]*$")
                && !OpenApiCommandConstants.BAL_KEYWORDS.stream().anyMatch(identifier::equals) && !isVar) {
            return identifier;
        }
        if (!identifier.matches("[a-zA-Z]+") ||
                OpenApiCommandConstants.BAL_KEYWORDS.stream().anyMatch(identifier::equals)) {
            if ((isVar && OpenApiCommandConstants.BAL_KEYWORDS.stream().anyMatch(identifier::equals))
                    || !OpenApiCommandConstants.BAL_KEYWORDS.stream().anyMatch(identifier::equals)) {
                identifier = identifier.replaceAll("([\\\\?!<>*\\-=^+(){}|.$])", "\\\\$1");
                identifier = "'" + identifier;
            }
        }
        return identifier;
    }
}
