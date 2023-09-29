package com.espark.adarsh.exception;

import graphql.GraphQLError;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GraphQlExceptionsHandler {

/*    @GraphQlExceptionHandler
    public GraphQLError handle(BindException ex) {
        return GraphQLError.newError().errorType(ErrorType.BAD_REQUEST).message("...").build();
    }*/
}
