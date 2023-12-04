package com.espark.adarsh.processor;

import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.bean.ResponseBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.processer.EmployeeProcessor;
import com.espark.adarsh.service.EmployeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.GraphQLContext;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingEnvironmentImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class GraphqlProcessorTest {


    @InjectMocks
    EmployeeProcessor employeeProcessor;

    @Mock
    EmployeeService employeeService;

    ObjectMapper objectMapper = new ObjectMapper();

    FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();

    @BeforeEach
    void init() throws IOException {
        ReflectionTestUtils.setField(employeeProcessor,"objectMapper",objectMapper);
        ReflectionTestUtils.setField(employeeProcessor,"employeeService",employeeService);

        Resource allEmployeeResource =  loadResource("src/test/resources/getAllEmployee-response.json");
       // ResponseBean<List<Employee>> responseBean = objectMapper.readValue(allEmployeeResource.getInputStream(), new TypeReference<ResponseBean<List<Employee>>>() {});

        Resource employeeResource = loadResource("src/test/resources/getEmployee-response.json");

    }

    Resource loadResource(String filePath ) throws IOException {
        Resource file = resourceLoader.getResource(filePath);
       return file;
    }

    @Test
    public void getAllEmployee() throws Exception {
        Map<String,Object> param = new HashMap<>();
        GraphQLContext graphQLContext = GraphQLContext.of(param);
        DataFetchingEnvironment environment = DataFetchingEnvironmentImpl.newDataFetchingEnvironment()
                .graphQLContext(graphQLContext)
                .build();
        DataFetcher<List<Employee>> dataFetcher =  employeeProcessor.getAllEmployee();
        Assert.notNull(dataFetcher,"empty response object");
        List<Employee> employeeList= dataFetcher.get(environment);
        Assert.notNull(employeeList,"employeeList response object");
    }


    @Test
    public void getEmployee() throws Exception {
        Map<String,Object> param = new HashMap<>();
        param.put("id","1");
        GraphQLContext graphQLContext = GraphQLContext.of(param);
        DataFetchingEnvironment environment = DataFetchingEnvironmentImpl.newDataFetchingEnvironment()
                .graphQLContext(graphQLContext)
                .build();
        DataFetcher<Employee> dataFetcher =  employeeProcessor.getEmployee();
        Assert.notNull(dataFetcher,"empty response object");
        Object dataFetcherResult = dataFetcher.get(environment);
        Assert.notNull(dataFetcherResult,"employee response object");
        DataFetcherResult<Employee> result = (DataFetcherResult<Employee>) dataFetcherResult;
        Employee employee = result.getData();
        //todo fix it with mockito.when().thenReturn()
        //Assert.notNull(dataFetcherResult,"employee response object");
    }


    @Test
    public void removeEmployee() throws Exception {
        Map<String,Object> param = new HashMap<>();
        param.put("id",1L);
        GraphQLContext graphQLContext = GraphQLContext.of(param);
        DataFetchingEnvironment environment = DataFetchingEnvironmentImpl.newDataFetchingEnvironment()
                .graphQLContext(graphQLContext)
                .build();
        DataFetcher<Employee> dataFetcher =  employeeProcessor.removeEmployee();
        Assert.notNull(dataFetcher,"empty response object");
        Object dataFetcherResult = dataFetcher.get(environment);
        Assert.notNull(dataFetcherResult,"employee response object");
        DataFetcherResult<Employee> result = (DataFetcherResult<Employee>) dataFetcherResult;
        Employee employee = result.getData();
        //todo fix it with mockito.when().thenReturn()
        //Assert.notNull(employee,"employee response object");

    }


    @Test
    public void saveEmployee() throws Exception {
        EmployeeBean employeeBean = new EmployeeBean();
        Map<String,Object> param = new HashMap<>();
        param.put("employeeBean",employeeBean);
        GraphQLContext graphQLContext = GraphQLContext.of(param);
        DataFetchingEnvironment environment = DataFetchingEnvironmentImpl.newDataFetchingEnvironment()
                .graphQLContext(graphQLContext)
                .build();
        DataFetcher<Employee> dataFetcher =  employeeProcessor.saveEmployee();
        Assert.notNull(dataFetcher,"empty response object");
        Object dataFetcherResult = dataFetcher.get(environment);
        Assert.notNull(dataFetcherResult,"employee response object");
        DataFetcherResult<Employee> result = (DataFetcherResult<Employee>) dataFetcherResult;
        Employee employee = result.getData();
        //todo fix it with mockito.when().thenReturn()
        //Assert.notNull(result,"employee response object");

    }
    @Test
    public void updateEmployee() throws Exception {
        EmployeeBean employeeBean = new EmployeeBean();
        Map<String,Object> param = new HashMap<>();
        param.put("employeeBean",employeeBean);
        GraphQLContext graphQLContext = GraphQLContext.of(param);
        DataFetchingEnvironment environment = DataFetchingEnvironmentImpl.newDataFetchingEnvironment()
                .graphQLContext(graphQLContext)
                .build();
        DataFetcher<Employee> dataFetcher =  employeeProcessor.updateEmployee();
        Assert.notNull(dataFetcher,"empty response object");
        Object dataFetcherResult = dataFetcher.get(environment);
        Assert.notNull(dataFetcherResult,"employee response object");
        DataFetcherResult<Employee> result = (DataFetcherResult<Employee>) dataFetcherResult;
        Employee employee = result.getData();
        //todo fix it with mockito.when().thenReturn()
        //Assert.notNull(result,"employee response object");
    }

    @Test
    public void employeesFilter() throws Exception {
        Map<String,Object> filterMap = new HashMap<>();
        filterMap.put("employeeBean",filterMap);
        GraphQLContext graphQLContext = GraphQLContext.of(filterMap);
        DataFetchingEnvironment environment = DataFetchingEnvironmentImpl.newDataFetchingEnvironment()
                .graphQLContext(graphQLContext)
                .build();
        DataFetcher<Iterable<Employee>> dataFetcher =  employeeProcessor.employeesFilter();
        Assert.notNull(dataFetcher,"empty response object");
        Object dataFetcherResult = dataFetcher.get(environment);
        Assert.notNull(dataFetcherResult,"employee response object");
        DataFetcherResult<Iterable<Employee>> result = (DataFetcherResult<Iterable<Employee>>) dataFetcherResult;
        Iterable<Employee> employee = result.getData();
        //todo fix it with mockito.when().thenReturn()
        //Assert.notNull(employee,"employee response object");
    }



}
