package com.espark.adarsh.service;

import com.espark.adarsh.bean.Employee;
import com.espark.adarsh.exception.GraphqlException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.GraphQLError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GraphQLService {

    @Autowired
    GraphQL graphQL;

    @Autowired
    ObjectMapper objectMapper;


    public String getMessage(String query, String name) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("{" + query + "}")
                .graphQLContext(new HashMap<>() {
                    {
                        put("name", name);
                    }
                })
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        List<GraphQLError> errors = executionResult.getErrors();
        if (errors != null && !errors.isEmpty()) {
            String error = errors.stream().map(e -> e.getMessage()).collect(Collectors.joining(", "));
            log.error("GraphqlService getMessage {}", error);
            throw new GraphqlException(error);
        }
        String message = executionResult.getData().toString();
        log.info("Graphql Response {}", message);
        return message;
    }

    public Map<String, Object> getData(String query, String name) throws JsonProcessingException {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("{" + query + "}")
                .graphQLContext(new HashMap<>() {
                    {
                        put("name", name);
                    }
                })
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        List<GraphQLError> errors = executionResult.getErrors();
        if (errors != null && !errors.isEmpty()) {
            String error = errors.stream().map(e -> e.getMessage()).collect(Collectors.joining(", "));
            log.error("GraphqlService getData {}", error);
            throw new GraphqlException(error);
        }
        LinkedHashMap<String, Object> linkedHashMap = executionResult.getData();
        log.info("Graphql Response {}", linkedHashMap);

        try {
            if (linkedHashMap != null && !linkedHashMap.isEmpty()) {
                String data =  linkedHashMap.get("data").toString();
                linkedHashMap = objectMapper.readValue(data, new TypeReference<LinkedHashMap<String, Object>>() {
                });
            }
            log.info("Graphql Response {}", linkedHashMap);

        } catch (JsonProcessingException jsonProcessingException) {
            log.error(jsonProcessingException.getMessage());
        }
        return linkedHashMap;
    }

    public Employee getEmployee(String query, Integer id) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("{" + query + "}")
                .graphQLContext(new HashMap<>() {
                    {
                        put("id", id);
                    }
                })
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        List<GraphQLError> errors = executionResult.getErrors();
        if (errors != null && !errors.isEmpty()) {
            String error = errors.stream().map(e -> e.getMessage()).collect(Collectors.joining(", "));
            log.error("GraphqlService getEmployee {}", error);
            throw new GraphqlException(error);
        }
        LinkedHashMap linkedHashMap = executionResult.getData();
        Employee employee = null;
        try {
            if (linkedHashMap!=null && !linkedHashMap.isEmpty()) {
                String data = objectMapper.writeValueAsString(linkedHashMap.get("employee"));
                employee = objectMapper.readValue(data, Employee.class);
            }
            log.info("Graphql Response {}", employee);

        } catch (JsonProcessingException jsonProcessingException) {
            log.error(jsonProcessingException.getMessage());
        }
        return employee;
    }


    public List<Employee> getEmployees(String query) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("{" + query + "}")
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        List<GraphQLError> errors = executionResult.getErrors();
        if (errors != null && !errors.isEmpty()) {
            String error = errors.stream().map(e -> e.getMessage()).collect(Collectors.joining(", "));
            log.error("GraphqlService getEmployees {}", error);
            throw new GraphqlException(error);
        }
        LinkedHashMap linkedHashMap = executionResult.getData();
        List<Employee> employees = new ArrayList<>();
        if (!linkedHashMap.isEmpty()) {
            try {
                String data = objectMapper.writeValueAsString(linkedHashMap.get("employees"));
                employees = objectMapper.readValue(data, new TypeReference<List<Employee>>() {
                });
                log.info("Graphql Response {}", employees);
            } catch (JsonProcessingException jsonProcessingException) {
                log.error(jsonProcessingException.getMessage());
            }
        }
        return employees;
    }
}
