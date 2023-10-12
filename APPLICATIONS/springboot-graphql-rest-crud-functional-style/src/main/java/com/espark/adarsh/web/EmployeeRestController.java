package com.espark.adarsh.web;


import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.service.GraphqlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Slf4j
@RestController
public class EmployeeRestController {

    @Autowired
    GraphqlService graphqlService;

    @PostMapping("/employee")
    public ResponseBean processRequest(@RequestBody Map<String, Object> query) {
        log.info("EmployeeRestController:: processRequest {}",query);
        return this.graphqlService.processRequest(query);
    }

}

