package org.ballerinalang.openapi.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class contains OpenApi command constants.
 */
public class OpenApiCommandConstants {
    public static final String OPENAPI_COMMAND = "openapi";
    public static final String OPENAPI_GEN_SERVICE_COMMAND = "gen-service";
    public static final String OPENAPI_GEN_CLIENT_COMMAND = "gen-client";
    public static final String OPENAPI_GEN_CONTRACT_COMMAND = "gen-contract";

    public static final String HELP_COMMAND_SHORT = "-h";
    public static final String HELP_COMMAND_LONG = "--help";

    public static final String OUTPUT_LOCATION_SHORT = "-o";
    public static final String OUTPUT_LOCATION_LONG = "--output";

    public static final String CURRENT_DIR = "user.dir";

    //TODO Update keywords if Ballerina Grammer changes
    private static final String[] KEYWORDS = new String[]{"abort", "aborted", "abstract", "all", "annotation",
            "any", "anydata", "boolean", "break", "byte", "catch", "channel", "check", "checkpanic", "client",
            "committed", "const", "continue", "decimal", "else", "error", "external", "fail", "final", "finally",
            "float", "flush", "fork", "function", "future", "handle", "if", "import", "in", "int", "is", "join",
            "json", "listener", "lock", "match", "new", "object", "OBJECT_INIT", "onretry", "PARAMETER", "panic",
            "private", "public", "record", "remote", "resource", "retries", "retry", "return", "returns", "service",
            "source", "start", "stream", "string", "table", "transaction", "try", "type", "typedesc", "typeof",
            "trap", "throw", "wait", "while", "with", "worker", "var", "version", "xml", "xmlns", "BOOLEAN_LITERAL",
            "NULL_LITERAL", "ascending", "descending", "foreach", "map", "group", "from", "default"};

    public static final List<String> BAL_KEYWORDS;

    static {
        BAL_KEYWORDS = Collections.unmodifiableList(Arrays.asList(KEYWORDS));
    }
}
