package com.espark.adarsh.web;


import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.service.GraphqlService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmployeeRestController {

    @Autowired
    GraphqlService graphqlService;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return this.graphqlService.getAllEmployee("getAllEmployee{ id firstName lastName doj gender}");
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") Long id) {
        return this.graphqlService.getEmployee("getEmployee{ id firstName lastName doj gender}",id);
    }

    @PostMapping("/employee")
    public Employee saveEmployee(@RequestBody @Valid EmployeeBean employeeBean) throws JsonProcessingException {
        return this.graphqlService.saveEmployee("saveEmployee(employeeBean:"+employeeBean.toString()+"){ id firstName lastName doj gender}",employeeBean);
    }

   @DeleteMapping("/employee/{id}")
    public Employee removeEmployee(@PathVariable("id") Long id) {
        return this.graphqlService.removeEmployee("removeEmployee{ id firstName lastName doj gender}",id);
    }



    @PutMapping("/employee")
    public Employee updateEmployee(@RequestBody @Valid EmployeeBean employeeBean) {
        return this.graphqlService.updateEmployee("updateEmployee(employeeBean:"+employeeBean.toString()+"){ id firstName lastName doj gender}",employeeBean.getId(), employeeBean);
    }


    @PostMapping("/employee/filter")
    public Iterable<Employee> employeesFilter(@RequestBody EmployeeFilter filter) {
       return this.graphqlService.employeesFilter("employeesFilter{ id firstName lastName doj gender}",filter);
    }

}

