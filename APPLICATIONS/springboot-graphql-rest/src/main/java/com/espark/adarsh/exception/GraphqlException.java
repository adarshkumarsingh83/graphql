package com.espark.adarsh.exception;

public class GraphqlException extends RuntimeException{
    public GraphqlException() {
    }

    public GraphqlException(String message) {
        super(message);
    }

    public GraphqlException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphqlException(Throwable cause) {
        super(cause);
    }

    public GraphqlException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
