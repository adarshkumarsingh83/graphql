package com.espark.adarsh.web;

import com.espark.adarsh.bean.Employee;
import com.espark.adarsh.service.GraphQLService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ApplicationController {

    @Autowired
    GraphQLService graphQLService;

    @GetMapping("/message/{name}")
    public String getMessage(@PathVariable("name") String name) {
        return graphQLService.getMessage("message", name);
    }


    @GetMapping("/data/{name}")
    public Map<String,Object> getData(@PathVariable("name") String name) throws JsonProcessingException {
        return graphQLService.getData("data", name);
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) {
        return graphQLService.getEmployee("employee(id:"+id+"){ id name dob gender}", id);
    }

    @GetMapping("/employees")
    public List<Employee> getEmployees() {
        return graphQLService.getEmployees("employees{ id name dob gender}");
    }

}


