package com.espark.adarsh.web;


import com.espark.adarsh.service.GraphqlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class MockGraphQlRestControllerTest {

    @InjectMocks
    GraphqlEmployeeRestController employeeRestController;

    @Mock
    GraphqlService graphqlService;


    @BeforeEach
    public void init() {

    }


    @Test
    public void testGetAllEmployee() {
        Map<String,String> input =new HashMap<>(){
            {
                put("queryName","getAllEmployee");
                put("query","{ getAllEmployee{ id firstName lastName doj gender} }");
            }
        };
        employeeRestController.getAllEmployee(input);
        Mockito.verify(graphqlService, Mockito.atLeastOnce()).getAllEmployee(input);
    }

}
