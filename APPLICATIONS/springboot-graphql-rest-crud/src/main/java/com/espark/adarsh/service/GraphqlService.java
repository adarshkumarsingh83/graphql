package com.espark.adarsh.service;


import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
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

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GraphqlService {

    @Autowired
    GraphQL graphQL;

    @Autowired
    ObjectMapper objectMapper;

    public ResponseBean<List<Employee>> getAllEmployee(String query) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("{" + query + "}")
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
                String data = objectMapper.writeValueAsString(linkedHashMap.get("getAllEmployee"));
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

    public ResponseBean<Employee> getEmployees(Map<String, Object> input) {
        Map<String, Object> param = null;
        if (input.containsKey("param") && input.get("param") != null) {
            param = (Map<String, Object>) input.get("param");
        }
        String query = "";
        if (input.containsKey("query") && input.get("query") != null) {
            query = input.get("query").toString();
        }

        String queryName = "";
        if (input.containsKey("queryName") && input.get("queryName") != null) {
            queryName = input.get("queryName").toString();
        }

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .graphQLContext(param)
                .build();
        ExecutionResult executionResult = graphQL.execute(executionInput);
        LinkedHashMap linkedHashMap = executionResult.getData();
        List<GraphQLError> errors = executionResult.getErrors();
        List<String> errorsList = new LinkedList();

        if (errors != null && !errors.isEmpty()) {
            errorsList = errors.stream()
                    .map(e -> e.getMessage())
                    .collect(Collectors.toList());
            log.error("GraphqlService getEmployees {}", errorsList);
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


    public ResponseBean<Employee> getEmployee(String query, Long id) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query("{" + query + "}")
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
                String data = objectMapper.writeValueAsString(linkedHashMap.get("getEmployee"));
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


    public ResponseBean<Iterable<Employee>> employeesFilter(String query, EmployeeFilter filter) {
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
        List<String> errorsList = new LinkedList();
        if (errors != null && !errors.isEmpty()) {
            errorsList = errors.stream()
                    .map(e -> e.getMessage())
                    .collect(Collectors.toList());
            log.error("GraphqlService getAllEmployee {}", errorsList);
        }


        Iterable<Employee> employees = new ArrayList<>();
        if (linkedHashMap != null && !linkedHashMap.isEmpty()) {
            try {
                String data = objectMapper.writeValueAsString(linkedHashMap.get("employeesFilter"));
                employees = objectMapper.readValue(data, new TypeReference<Iterable<Employee>>() {
                });
                log.info("Graphql Response {}", employees);
            } catch (JsonProcessingException jsonProcessingException) {
                log.error(jsonProcessingException.getMessage());
                errorsList.add(jsonProcessingException.getMessage());
            }
        }
        return ResponseBean.<Iterable<Employee>>builder()
                .data(employees)
                .errors(errorsList)
                .build();
    }

    public ResponseBean<Employee> saveEmployee(String query, final EmployeeBean employeeBean) {
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
        List<String> errorsList = new LinkedList<>();

        if (errors != null && !errors.isEmpty()) {
            errorsList = errors.stream()
                    .map(e -> e.getMessage())
                    .collect(Collectors.toList());
            log.error("GraphqlService getAllEmployee {}", errorsList);
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
            errorsList.add(jsonProcessingException.getMessage());
        }
        return ResponseBean.<Employee>builder()
                .data(employee)
                .errors(errorsList)
                .build();
    }

    public ResponseBean<Employee> removeEmployee(String query, Long id) {

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
        List<String> errorsList = new LinkedList<>();
        List<GraphQLError> errors = executionResult.getErrors();

        if (errors != null && !errors.isEmpty()) {
            errorsList = errors.stream()
                    .map(e -> e.getMessage())
                    .collect(Collectors.toList());
            log.error("GraphqlService getAllEmployee {}", errorsList);
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
            errorsList.add(jsonProcessingException.getMessage());
        }
        return ResponseBean.<Employee>builder()
                .data(employee)
                .errors(errorsList)
                .build();
    }

    public ResponseBean<Employee> updateEmployee(String query, Long id, final EmployeeBean employeeBean) {

        ResponseBean.ResponseBeanBuilder<Employee> builder = ResponseBean.<Employee>builder();
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
        List<String> errorsList = new LinkedList<>();

        if (errors != null && !errors.isEmpty()) {
            errorsList = errors.stream()
                    .map(e -> e.getMessage())
                    .collect(Collectors.toList());
            log.error("GraphqlService getAllEmployee {}", errorsList);
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
            errorsList.add(jsonProcessingException.getMessage());
        }

        return builder
                .data(employee)
                .errors(errorsList)
                .build();
    }

}
