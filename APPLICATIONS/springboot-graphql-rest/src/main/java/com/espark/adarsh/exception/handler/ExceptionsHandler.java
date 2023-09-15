package com.espark.adarsh.exception.handler;

import com.espark.adarsh.exception.GraphqlException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionsHandler {


    @ExceptionHandler(GraphqlException.class)
    public Map<String,String> handleGraphqlException(GraphqlException ex){
        return new HashMap<>(){
            {
              put("type","graphql exception");
              put("msg",ex.getMessage());
            }
        };
    }
}
