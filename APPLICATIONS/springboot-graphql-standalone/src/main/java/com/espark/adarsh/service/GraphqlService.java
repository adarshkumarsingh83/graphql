package com.espark.adarsh.service;


import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.exception.GraphqlException;
import com.espark.adarsh.filter.EmployeeFilter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GraphqlService {

    @Autowired
    GraphQL graphQL;

    @Autowired
    ObjectMapper objectMapper;

    public List<Employee> getAllEmployee(String query) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("{" + query + "}")
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        LinkedHashMap linkedHashMap = executionResult.getData();
        List<GraphQLError> errors = executionResult.getErrors();

        if (errors != null && !errors.isEmpty()) {
            String error = errors.stream().map(e -> e.getMessage()).collect(Collectors.joining(", "));
            log.error("GraphqlService getAllEmployee {}", error);
            throw new GraphqlException(error);
        }

        List<Employee> employees = new ArrayList<>();
        if (!linkedHashMap.isEmpty()) {
            try {
                String data = objectMapper.writeValueAsString(linkedHashMap.get("getAllEmployee"));
                employees = objectMapper.readValue(data, new TypeReference<List<Employee>>() {
                });
                log.info("Graphql Response {}", employees);
            } catch (JsonProcessingException jsonProcessingException) {
                log.error(jsonProcessingException.getMessage());
            }
        }
        return employees;
    }


    public Employee getEmployee(String query, Long id) {
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
        List<GraphQLError> errors = executionResult.getErrors();

        if (errors != null && !errors.isEmpty()) {
            String error = errors.stream().map(e -> e.getMessage()).collect(Collectors.joining(", "));
            log.error("GraphqlService getEmployee {}", error);
            throw new GraphqlException(error);
        }

        Employee employee = null;
        try {
            if (!linkedHashMap.isEmpty()) {
                String data = objectMapper.writeValueAsString(linkedHashMap.get("getEmployee"));
                employee = objectMapper.readValue(data, Employee.class);
            }
            log.info("Graphql Response {}", employee);

        } catch (JsonProcessingException jsonProcessingException) {
            log.error(jsonProcessingException.getMessage());
        }
        return employee;
    }


    public Iterable<Employee> employeesFilter(String query, EmployeeFilter filter) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("{" + query + "}")
                .graphQLContext(new HashMap<>() {
                    {
                        put("filter", filter);
                    }
                })
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        LinkedHashMap linkedHashMap = executionResult.getData();
        List<GraphQLError> errors = executionResult.getErrors();

        if (errors != null && !errors.isEmpty()) {
            String error = errors.stream().map(e -> e.getMessage()).collect(Collectors.joining(", "));
            log.error("GraphqlService employeesFilter {}", error);
            throw new GraphqlException(error);
        }


        Iterable<Employee> employees = new ArrayList<>();
        if (!linkedHashMap.isEmpty()) {
            try {
                String data = objectMapper.writeValueAsString(linkedHashMap.get("employeesFilter"));
                employees = objectMapper.readValue(data, new TypeReference<Iterable<Employee>>() {
                });
                log.info("Graphql Response {}", employees);
            } catch (JsonProcessingException jsonProcessingException) {
                log.error(jsonProcessingException.getMessage());
            }
        }
        return employees;
    }

    public Employee saveEmployee(String query, final EmployeeBean employeeBean) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("{" + query + "}")
                .graphQLContext(new HashMap<>() {
                    {
                        put("employeeBean", employeeBean);
                    }
                })
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        LinkedHashMap linkedHashMap = executionResult.getData();

        List<GraphQLError> errors = executionResult.getErrors();

        if (errors != null && !errors.isEmpty()) {
            String error = errors.stream().map(e -> e.getMessage()).collect(Collectors.joining(", "));
            log.error("GraphqlService saveEmployee {}", error);
            throw new GraphqlException(error);
        }

        Employee employee = null;
        try {
            if (linkedHashMap != null && !linkedHashMap.isEmpty()) {
                String data = objectMapper.writeValueAsString(linkedHashMap.get("saveEmployee"));
                employee = objectMapper.readValue(data, Employee.class);
            }
            log.info("Graphql Response {}", employee);

        } catch (JsonProcessingException jsonProcessingException) {
            log.error(jsonProcessingException.getMessage());
        }
        return employee;
    }

    public Employee removeEmployee(String query, Long id) {

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

        List<GraphQLError> errors = executionResult.getErrors();

        if (errors != null && !errors.isEmpty()) {
            String error = errors.stream().map(e -> e.getMessage()).collect(Collectors.joining(", "));
            log.error("GraphqlService removeEmployee {}", error);
            throw new GraphqlException(error);
        }

        Employee employee = null;
        try {
            if (linkedHashMap != null && !linkedHashMap.isEmpty()) {
                String data = objectMapper.writeValueAsString(linkedHashMap.get("removeEmployee"));
                employee = objectMapper.readValue(data, Employee.class);
            }
            log.info("Graphql Response {}", employee);

        } catch (JsonProcessingException jsonProcessingException) {
            log.error(jsonProcessingException.getMessage());
        }
        return employee;
    }

    public Employee updateEmployee(String query, Long id, final EmployeeBean employeeBean) {


        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("{" + query + "}")
                .graphQLContext(new HashMap<>() {
                    {
                        put("id", id);
                        put("employeeBean", employeeBean);
                    }
                })
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        LinkedHashMap linkedHashMap = executionResult.getData();

        List<GraphQLError> errors = executionResult.getErrors();

        if (errors != null && !errors.isEmpty()) {
            String error = errors.stream().map(e -> e.getMessage()).collect(Collectors.joining(", "));
            log.error("GraphqlService updateEmployee {}", error);
            throw new GraphqlException(error);
        }

        Employee employee = null;
        try {
            if (linkedHashMap != null && !linkedHashMap.isEmpty()) {
                String data = objectMapper.writeValueAsString(linkedHashMap.get("updateEmployee"));
                employee = objectMapper.readValue(data, Employee.class);
            }
            log.info("Graphql Response {}", employee);

        } catch (JsonProcessingException jsonProcessingException) {
            log.error(jsonProcessingException.getMessage());
        }
        return employee;
    }

}
