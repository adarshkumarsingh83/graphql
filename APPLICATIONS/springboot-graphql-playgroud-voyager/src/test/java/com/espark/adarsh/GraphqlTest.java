package com.espark.adarsh;

import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Sql({"/data.sql"})
@Slf4j
@Import({TestConfiguration.class})
public class GraphqlTest {



    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Mock
    EmployeeService employeeService;

    @Autowired
    ObjectMapper objectMapper;
    Employee employee;

    @BeforeEach
    void init() throws JsonProcessingException {
        String employeeJson="{\n" +
                "        \"id\": \"1\",\n" +
                "        \"firstName\": \"adarsh\",\n" +
                "        \"lastName\": \"kumar\",\n" +
                "        \"salary\": 10,\n" +
                "        \"career\": \"It\",\n" +
                "        \"attributes\": \"{\\\"key1\\\":\\\"value\\\"}\"\n" +
                "      }";
        employee = objectMapper.readValue(employeeJson, new TypeReference<Employee>() {});
    }

    @Test
    public void getEmployeeById() throws IOException {
        Mockito.when(employeeService.getEmployee(1L)).thenReturn(employee);
        GraphQLResponse response = graphQLTestTemplate.postForResource("schema.graphqls");
        assertThat(response.isOk()).isTrue();
    }

}
