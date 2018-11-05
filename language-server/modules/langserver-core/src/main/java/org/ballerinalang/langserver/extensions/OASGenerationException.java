package org.ballerinalang.langserver.extensions;

import org.ballerinalang.langserver.compiler.LSCompilerException;

public class OASGenerationException extends Exception {

    public OASGenerationException(String message) {
        super(message);
    }

    public OASGenerationException(String message, Throwable cause) {
        super(message, cause);
    }

    public OASGenerationException(Throwable cause) {
        super(cause);
    }

    public OASGenerationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
