package com.espark.adarsh.processer;

import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.exception.EmployeeNotFoundException;
import com.espark.adarsh.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class EmployeeProcessor {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ObjectMapper objectMapper;


    public DataFetcher<List<Employee>> getAllEmployee() {
        return new DataFetcher<List<Employee>>() {
            @Override
            public List<Employee> get(DataFetchingEnvironment environment) {
                return employeeService.getAllEmployee();
            }
        };
    }

    public DataFetcher<Employee> getEmployee() throws EmployeeNotFoundException {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                Long id = environment.getGraphQlContext().get("id");
                Employee employee = null;
                DataFetcherResult dataFetcherResult = null;
                try {
                    employee = employeeService.getEmployee(id);
                    dataFetcherResult = new DataFetcherResult(employee, List.of());
                } catch (EmployeeNotFoundException enf) {
                    dataFetcherResult = new DataFetcherResult(employee, List.of(enf));
                }
                return dataFetcherResult;
            }
        };
    }



}
