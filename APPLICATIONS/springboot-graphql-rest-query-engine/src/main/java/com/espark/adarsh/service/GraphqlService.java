package com.espark.adarsh.service;



import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
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
public class GraphqlService {

    @Autowired
    GraphQL graphQL;

    @Autowired
    ObjectMapper objectMapper;

    public ResponseBean<List<Employee>> getAllEmployee(Map<String,String> payload) {
        String query=payload.get("query");
        String queryName = payload.get("queryName");
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        LinkedHashMap linkedHashMap = executionResult.getData();
        List<GraphQLError> errors = executionResult.getErrors();
        List<String> errorsList = new LinkedList();

        if (errors != null && !errors.isEmpty()) {
            errorsList = errors.stream()
                    .map(e -> e.getMessage())
                    .collect(Collectors.toList());
            log.error("GraphqlService getAllEmployee {}", errorsList);
        }

        List<Employee> employees = new ArrayList<>();
        if (linkedHashMap != null && !linkedHashMap.isEmpty()) {
            try {
                String data = objectMapper.writeValueAsString(linkedHashMap.get(queryName));
                employees = objectMapper.readValue(data, new TypeReference<List<Employee>>() {
                });
                log.info("Graphql Response {}", employees);
            } catch (JsonProcessingException jsonProcessingException) {
                log.error(jsonProcessingException.getMessage());
                errorsList.add(jsonProcessingException.getMessage());
            }
        }
        return ResponseBean.<List<Employee>>builder()
                .data(employees)
                .errors(errorsList)
                .build();

    }

    public ResponseBean<Employee> getEmployee(Long id, Map<String, String> payload) {
        String query=payload.get("query");
        String queryName = payload.get("queryName");

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query( query)
                .variables(new HashMap<>() {
                    {
                        put("var", id);
                    }
                })
                .graphQLContext(new HashMap<>() {
                    {
                        put("id", id);
                    }
                })
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        LinkedHashMap linkedHashMap = executionResult.getData();
        List<GraphQLError> errors = executionResult.getErrors();
        List<String> errorsList = new LinkedList();

        if (errors != null && !errors.isEmpty()) {
            errorsList = errors.stream()
                    .map(e -> e.getMessage())
                    .collect(Collectors.toList());
            log.error("GraphqlService getAllEmployee {}", errorsList);
        }

        Employee employee = null;
        try {
            if (linkedHashMap != null && !linkedHashMap.isEmpty()) {
                String data = objectMapper.writeValueAsString(linkedHashMap.get(queryName));
                employee = objectMapper.readValue(data, Employee.class);
            }
            log.info("Graphql Response {}", employee);

        } catch (JsonProcessingException jsonProcessingException) {
            log.error(jsonProcessingException.getMessage());
            errorsList.add(jsonProcessingException.getMessage());
        }
        return ResponseBean.<Employee>builder()
                .data(employee)
                .errors(errorsList)
                .build();
    }


}
