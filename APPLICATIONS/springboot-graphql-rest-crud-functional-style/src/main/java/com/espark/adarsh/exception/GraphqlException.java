package com.espark.adarsh.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GraphqlException extends RuntimeException implements GraphQLError {

    List<String> errors=new ArrayList<>();

    public GraphqlException() {
    }

    public GraphqlException(List<String> errors){
        super(errors.toString());
        this.errors=errors;
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

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public Map<String, Object> getExtensions() {
        Map<String, Object> customAttributes = new LinkedHashMap<>();
        errors.stream().forEach(e->customAttributes.put(e.toString(),e.toString()));
        return customAttributes;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }
}
