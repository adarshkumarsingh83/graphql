package com.espark.adarsh.web;


import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.entity.Gender;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.filter.FilterField;
import com.espark.adarsh.service.GraphqlService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class EmployeeRestControllerTest {

    @InjectMocks
    EmployeeRestController employeeRestController;

    @Mock
    GraphqlService graphqlService;


    Long id;
    EmployeeBean employee;

    EmployeeFilter employeeFilter;

    @BeforeEach
    public void init() {
        this.id = 1L;
        this.employee = new EmployeeBean() {
            {
                setId(10L);
                setFirstName("sonu");
                setLastName("singh");
                setGender(Gender.MALE);
                setDoj(LocalDate.now());
            }
        };

        //  {"id":null,"salary": { "operator" : "gt" ,"value": "5" },"carrier":null}
        this.employeeFilter = new EmployeeFilter() {
            {
                setId(null);
                setSalary(new FilterField() {
                    {
                        setOperator("gt");
                        setValue("5");
                    }
                });
                setCarrier(null);
            }
        };
    }

    @Test
    public void testGetAllEmployee() {
        employeeRestController.getAllEmployee();
        Mockito.verify(graphqlService, Mockito.atLeastOnce()).getAllEmployee(Mockito.any());
    }

    @Test
    public void testGetEmployee() {
        employeeRestController.getEmployee(id);
        Mockito.verify(graphqlService, Mockito.atLeastOnce()).getEmployee(Mockito.anyString(), Mockito.anyLong());
    }

    @Test
    public void testSaveEmployee() throws JsonProcessingException {
        employeeRestController.saveEmployee(employee);
        Mockito.verify(graphqlService, Mockito.atLeastOnce()).saveEmployee(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void testUpdateEmployee() {
        employeeRestController.updateEmployee(employee);
        Mockito.verify(graphqlService, Mockito.atLeastOnce()).updateEmployee(Mockito.anyString(), Mockito.anyLong(), Mockito.any());
    }

    @Test
    public void testRemoveEmployee() {
        employeeRestController.removeEmployee(id);
        Mockito.verify(graphqlService, Mockito.atLeastOnce()).removeEmployee(Mockito.anyString(), Mockito.anyLong());
    }


    @Test
    public void testEmployeesFilter() {
        employeeRestController.employeesFilter(employeeFilter);
        Mockito.verify(graphqlService, Mockito.atLeastOnce()).employeesFilter(Mockito.anyString(), Mockito.any());
    }


}

