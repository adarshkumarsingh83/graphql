package com.espark.adarsh.web;


import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.service.GenericGraphqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
public class EmployeeGenericRestController {

    @Autowired
    GenericGraphqlService graphqlService;

    @PostMapping("/employee/fetch")
    public ResponseBean getEmployees(@RequestBody Map<String, Object> query) {
        return this.graphqlService.processRequest(query);
    }

}

