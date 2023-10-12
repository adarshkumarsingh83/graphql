package com.espark.adarsh.service;


import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.functions.GraphqlProcessorFunction;
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
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GraphqlService {

    @Autowired
    GraphQL graphQL;

    @Autowired
    ObjectMapper objectMapper;

    static final String QUERY = "query";
    static final String PARAM = "param";
    static final String QUERY_NAME = "queryName";

    private final Function<ExecutionResult, List<String>> errorListFunction = executionResult -> {
        List<GraphQLError> errors = executionResult.getErrors();
        List<String> errorsList = new LinkedList();
        if (errors != null && !errors.isEmpty()) {
            errorsList = errors.stream()
                    .map(e -> e.getMessage())
                    .collect(Collectors.toList());
            log.error("GraphqlService errorListFunction {}", errorsList);
        }
        return errorsList;
    };

    private final GraphqlProcessorFunction<ExecutionResult, String, List<String>, ResponseBean<Employee>>
            employeeDataFunction = (executionResult, queryName, errorsList) -> {
        LinkedHashMap linkedHashMap = executionResult.getData();
        Employee employee = null;
        try {
            if (linkedHashMap != null && !linkedHashMap.isEmpty()) {
                String data = objectMapper.writeValueAsString(linkedHashMap.get(queryName));
                employee = objectMapper.readValue(data, Employee.class);
            }
            log.info("GraphqlService Response {}", employee);

        } catch (JsonProcessingException jsonProcessingException) {
            log.error("GraphqlService employeeDataFunction {} ", jsonProcessingException.getMessage());
            errorsList.add(jsonProcessingException.getMessage());
        }

        return ResponseBean.<Employee>builder()
                .data(employee)
                .errors(errorsList)
                .build();
    };

    private final GraphqlProcessorFunction<ExecutionResult, String, List<String>, ResponseBean<List<Employee>>>
            employeesDataFunction = (executionResult, queryName, errorsList) -> {
        LinkedHashMap linkedHashMap = executionResult.getData();

        List<Employee> employees = new ArrayList<>();
        if (linkedHashMap != null && !linkedHashMap.isEmpty()) {
            try {
                String data = objectMapper.writeValueAsString(linkedHashMap.get(queryName));
                employees = objectMapper.readValue(data, new TypeReference<List<Employee>>() {
                });
                log.info("Graphql Response {}", employees);
            } catch (JsonProcessingException jsonProcessingException) {
                log.error("GraphqlService employeesDataFunction {} ", jsonProcessingException.getMessage());
                errorsList.add(jsonProcessingException.getMessage());
            }
        }
        return ResponseBean.<List<Employee>>builder()
                .data(employees)
                .errors(errorsList)
                .build();
    };

    private final GraphqlProcessorFunction<ExecutionResult, String, List<String>, ResponseBean<Iterable<Employee>>>
            employeeFilterFunction = (executionResult, queryName, errorsList) -> {
        LinkedHashMap linkedHashMap = executionResult.getData();
        Iterable<Employee> employees = new ArrayList<>();
        if (linkedHashMap != null && !linkedHashMap.isEmpty()) {
            try {
                String data = objectMapper.writeValueAsString(linkedHashMap.get(queryName));
                employees = objectMapper.readValue(data, new TypeReference<Iterable<Employee>>() {
                });
                log.info("GraphqlService employeeFilterFunction {}", employees);
            } catch (JsonProcessingException jsonProcessingException) {
                log.error("GraphqlService employeeFilterFunction {} ", jsonProcessingException.getMessage());
                errorsList.add(jsonProcessingException.getMessage());
            }
        }
        return ResponseBean.<Iterable<Employee>>builder()
                .data(employees)
                .errors(errorsList)
                .build();
    };


    private final Map<String, GraphqlProcessorFunction> queryFunctionMapping = new HashMap<>() {
        {
            put("getEmployee", employeeDataFunction);
            put("getAllEmployee", employeesDataFunction);
            put("employeesFilter", employeeFilterFunction);
            put("saveEmployee", employeeDataFunction);
            put("updateEmployee", employeeDataFunction);
            put("removeEmployee", employeeDataFunction);
            put("updatePartialEmployee", employeeDataFunction);
        }
    };

    public ResponseBean processRequest(Map<String, Object> input) {
        Map<String, Object> param = new HashMap<>();
        if (input.containsKey(PARAM) && input.get(PARAM) != null) {
            param = (Map<String, Object>) input.get(PARAM);
        }

        String query = "";
        if (input.containsKey(QUERY) && input.get(QUERY) != null) {
            query = input.get(QUERY).toString();
        }

        String queryName = "";
        if (input.containsKey(QUERY_NAME) && input.get(QUERY_NAME) != null) {
            queryName = input.get(QUERY_NAME).toString();
        }

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .graphQLContext(param)
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        List<String> errorsList = errorListFunction.apply(executionResult);
        return (ResponseBean) queryFunctionMapping.get(queryName).apply(executionResult, queryName, errorsList);
    }

}
