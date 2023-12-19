package com.espark.adarsh.web;


import com.espark.adarsh.annotation.processor.GraphqlQueryProcessor;
import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.service.GraphqlService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class GraphqlEmployeeRestController {

    @Autowired
    GraphqlService graphqlService;

    @Autowired
    GraphqlQueryProcessor graphqlQueryProcessor;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/graphql/employees")
    public ResponseBean<List<Employee>> getAllEmployee(@RequestBody Map<String, String> payload) {
        return this.graphqlService.getAllEmployee(payload);
    }

    @PostMapping("/graphql/employee/{id}")
    public ResponseBean<Employee> getEmployee(@PathVariable("id") Long id, @RequestBody Map<String, String> payload) {
        return this.graphqlService.getEmployee(id, payload);
    }


    @GetMapping("/graphql/employee/query")
    public Map<String,String> getQuery(){
        return graphqlQueryProcessor.getSubQueryMap();
    }

}

