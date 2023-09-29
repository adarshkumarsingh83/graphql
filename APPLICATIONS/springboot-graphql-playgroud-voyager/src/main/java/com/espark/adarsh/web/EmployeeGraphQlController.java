package com.espark.adarsh.web;

import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.service.EmployeeService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.lang.annotation.Annotation;
import java.util.List;

@Component
public class EmployeeGraphQlController implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    EmployeeService employeeService;


    public List<Employee> getAllEmployee() {
        return this.employeeService.getAllEmployee();
    }


    public Employee getEmployee(Long id, DataFetchingEnvironment env) {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String val = attributes.getRequest().getHeader("name");
        return this.employeeService.getEmployee(id);
    }

    public Employee removeEmployee(Long id) {
        return this.employeeService.removeEmployee(id);
    }

    public Employee saveEmployee(EmployeeBean employeeBean) {
        return this.employeeService.saveEmployee(employeeBean.getEmployee());
    }

    public Employee updateEmployee(EmployeeBean employeeBean) {
        return this.employeeService.updateEmployee(employeeBean.getId(), employeeBean.getEmployee());
    }

}
