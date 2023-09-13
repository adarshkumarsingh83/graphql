package com.espark.adarsh.processer;

import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.service.EmployeeService;
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

    public DataFetcher<Employee> getEmployee() {
        return new DataFetcher<Employee>() {
            @Override
            public Employee get(DataFetchingEnvironment environment) {
                Long id = environment.getGraphQlContext().get("id");
                return employeeService.getEmployee(id);
            }
        };
    }

    public DataFetcher<Employee> removeEmployee() {
        return new DataFetcher<Employee>() {
            @Override
            public Employee get(DataFetchingEnvironment environment) {
                Long id = environment.getGraphQlContext().get("id");
                return employeeService.removeEmployee(id);
            }
        };
    }


    public DataFetcher<Employee> saveEmployee() {
        return new DataFetcher<Employee>() {
            @Override
            public Employee get(DataFetchingEnvironment environment) {
                EmployeeBean employeeBean = environment.getGraphQlContext().get("employeeBean");
                return employeeService.saveEmployee(employeeBean.getEmployee());
            }
        };
    }

    public DataFetcher<Employee> updateEmployee() {
        return new DataFetcher<Employee>() {
            @Override
            public Employee get(DataFetchingEnvironment environment) {
                EmployeeBean employeeBean = environment.getGraphQlContext().get("employeeBean");
                return employeeService.updateEmployee(employeeBean.getId(), employeeBean.getEmployee());
            }
        };
    }

    public DataFetcher<Iterable<Employee>> employeesFilter() {
        return new DataFetcher<Iterable<Employee>>() {
            @Override
            public Iterable<Employee> get(DataFetchingEnvironment environment) {
                EmployeeFilter filter = environment.getGraphQlContext().get("filter");
                return employeeService.employeesFilter(filter);
            }
        };
    }
}
