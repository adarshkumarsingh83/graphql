package com.espark.adarsh.service;

import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.util.ReflectionTestUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.*;


@ExtendWith(MockitoExtension.class)
public class GraphqlServiceTest {

    @Mock
    GraphQL graphQL;

    @Mock
    ExecutionResult executionResult;

    ObjectMapper objectMapper = new ObjectMapper();

    GraphqlService graphqlService;

    String getAllEmployeeResponse;
    String getEmployeeResponse;

    @Autowired
    private ResourceLoader resourceLoader;

    @BeforeEach
    public void init() throws IOException {
        graphqlService = new GraphqlService();
        ReflectionTestUtils.setField(graphqlService, "graphQL", graphQL);
        ReflectionTestUtils.setField(graphqlService, "objectMapper", objectMapper);
        getAllEmployeeResponse = this.getData("src/test/resources/getAllEmployee-ServiceResponse.json");
        getEmployeeResponse = this.getData("src/test/resources/getEmployee-ServiceResponse.json");
    }

    public String getData(String fileName) throws IOException {
        Path file = org.springframework.util.ResourceUtils.getFile( fileName).toPath();
        InputStream dbAsStream = new FileInputStream(file.toFile());
        return new String(dbAsStream.readAllBytes());
    }

    @Test
    public void getAllEmployee() throws JsonProcessingException {
        Mockito.when(graphQL.execute(Mockito.any(ExecutionInput.class)))
                .then((Answer<ExecutionResult>) invocationMock -> executionResult);
        Map<String, Object> expected = new LinkedHashMap<>() {
            {
                put("getAllEmployee", objectMapper.readValue(getAllEmployeeResponse, new TypeReference<List<Employee>>() {
                }));
            }
        };

        Map<String,Object> input =new HashMap<>(){
            {
             put("queryName","getAllEmployee");
             put("query","{ getAllEmployee{ id firstName lastName doj gender} }");
             put("param",new HashMap<>(){{}});
            }
        };
        Mockito.when(executionResult.getData()).thenReturn(expected);
        ResponseBean<List<Employee>> responseBean = graphqlService.processRequest(input);
        Assertions.assertNotNull(responseBean);
    }

    @Test
    public void getAllEmployeeWithError()  {

        Mockito.when(graphQL.execute(Mockito.any(ExecutionInput.class)))
                .then((Answer<ExecutionResult>) invocationMock -> executionResult);

        Map<String,Object> input =new HashMap<>(){
            {
                put("queryName","getAllEmployee");
                put("query","{ getAllEmployee{ id firstName lastName doj gender} }");
                put("param",new HashMap<>(){{}});
            }
        };

        GraphQLError graphQLError= GraphqlErrorBuilder.newError()
                .message("employee not found")
                .build();
        List<GraphQLError> errors = Collections.singletonList(graphQLError);
        Mockito.when(executionResult.getErrors()).thenReturn(errors);

        Map<String, Object> expected = new LinkedHashMap<>();
        Mockito.when(executionResult.getData()).thenReturn(expected);
        ResponseBean<List<Employee>> responseBean = graphqlService.processRequest(input);
        Assertions.assertEquals(responseBean.getErrors().get(0),graphQLError.getMessage());
    }

    @Test
    public void getEmployee() throws JsonProcessingException {
        Mockito.when(graphQL.execute(Mockito.any(ExecutionInput.class)))
                .then((Answer<ExecutionResult>) invocationMock -> executionResult);
        Map<String,Object> input =new HashMap<>(){
            {
                put("queryName","getEmployee");
                put("query","{ getEmployee{ id firstName lastName doj gender} }");
                put("param",new HashMap<>(){{}});
            }
        };
        Map<String, Object> expected = new LinkedHashMap<>() {
            {
                put("getEmployee", objectMapper.readValue(getEmployeeResponse, new TypeReference<Employee>() {
                }));
            }
        };
        Mockito.when(executionResult.getData()).thenReturn(expected);
        ResponseBean<Employee> responseBean = graphqlService.processRequest(input);
        Assertions.assertNotNull(responseBean);
    }


    @Test
    public void getEmployeeWithError() {

        Mockito.when(graphQL.execute(Mockito.any(ExecutionInput.class)))
                .then((Answer<ExecutionResult>) invocationMock -> executionResult);

        Map<String,Object> input =new HashMap<>(){
            {
                put("queryName","getEmployee");
                put("query","{ getEmployee{ id firstName lastName doj gender} }");
                put("param",new HashMap<>(){{}});
            }
        };

        GraphQLError graphQLError= GraphqlErrorBuilder.newError()
                .message("employee not found")
                .build();
        List<GraphQLError> errors = Collections.singletonList(graphQLError);
        Mockito.when(executionResult.getErrors()).thenReturn(errors);

        Map<String, Object> expected = new LinkedHashMap<>();
        Mockito.when(executionResult.getData()).thenReturn(expected);
        ResponseBean<Employee> responseBean =  graphqlService.processRequest(input);
        Assertions.assertEquals(responseBean.getErrors().get(0),graphQLError.getMessage());
    }

}
