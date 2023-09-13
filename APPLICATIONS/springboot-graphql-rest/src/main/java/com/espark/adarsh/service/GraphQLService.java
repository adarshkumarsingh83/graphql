package com.espark.adarsh.service;

import com.espark.adarsh.bean.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        String message = executionResult.getData().toString();
        log.info("Graphql Response {}", message);
        return message;
    }

    public Map<String, Object> getData(String query, String name) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("{" + query + "}")
                .graphQLContext(new HashMap<>() {
                    {
                        put("name", name);
                    }
                })
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        Map<String, Object> message = executionResult.toSpecification();
        log.info("Graphql Response {}", message);
        return message;
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
        LinkedHashMap linkedHashMap = executionResult.getData();
        Employee employee = null;
        try {
            if (!linkedHashMap.isEmpty()) {
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
