package com.espark.adarsh.processer;

import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.exception.EmployeeNotFoundException;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.service.EmployeeService;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeProcessor {

    @Autowired
    EmployeeService employeeService;


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

    public DataFetcher<Employee> removeEmployee() throws EmployeeNotFoundException {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                Long id = environment.getGraphQlContext().get("id");
                Employee employee = null;
                DataFetcherResult dataFetcherResult = null;
                try {
                    employee = employeeService.removeEmployee(id);
                    dataFetcherResult = new DataFetcherResult(employee, List.of());
                } catch (EmployeeNotFoundException enf) {
                    dataFetcherResult = new DataFetcherResult(employee, List.of(enf));
                }
                return dataFetcherResult;
            }
        };
    }


    public DataFetcher<Employee> saveEmployee() {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                Employee employee = null;
                DataFetcherResult dataFetcherResult = null;
                try {
                    EmployeeBean employeeBean = environment.getGraphQlContext().get("employeeBean");
                    employee = employeeService.saveEmployee(employeeBean.getEmployee());
                    dataFetcherResult = new DataFetcherResult(employee, List.of());
                } catch (Exception e) {
                    dataFetcherResult = new DataFetcherResult(employee, List.of(e));
                }
                return dataFetcherResult;
            }
        };
    }

    public DataFetcher<Employee> updateEmployee() throws EmployeeNotFoundException {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                Employee employee = null;
                DataFetcherResult dataFetcherResult = null;
                try {
                    EmployeeBean employeeBean = environment.getGraphQlContext().get("employeeBean");
                    employee = employeeService.updateEmployee(employeeBean.getId(), employeeBean.getEmployee());
                    dataFetcherResult = new DataFetcherResult(employee, List.of());
                } catch (Exception e) {
                    dataFetcherResult = new DataFetcherResult(employee, List.of(e));
                }
                return dataFetcherResult;
            }
        };
    }

    public DataFetcher<Iterable<Employee>> employeesFilter() throws EmployeeNotFoundException {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                Iterable<Employee> iterable = null;
                DataFetcherResult dataFetcherResult = null;
                try {
                    EmployeeFilter filter = environment.getGraphQlContext().get("filter");
                    iterable = employeeService.employeesFilter(filter);
                    dataFetcherResult = new DataFetcherResult(iterable, List.of());
                } catch (Exception e) {
                    dataFetcherResult = new DataFetcherResult(iterable, List.of(e));
                }
                return dataFetcherResult;
            }
        };
    }
}
