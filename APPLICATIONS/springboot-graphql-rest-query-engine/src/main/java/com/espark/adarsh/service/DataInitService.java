package com.espark.adarsh.service;

import com.espark.adarsh.respository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.sql.DataSource;
import java.util.Arrays;

@Service
public class DataInitService {


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void init() {
        /*
        Resource initSchema = new ClassPathResource("data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
        */

       /* employeeRepository.save(new Employee("adarsh", "kumar", "It",
                Arrays.asList(new Contact("bang street", "mp", "in"))));
        employeeRepository.save(new Employee("radha", "singh", "It",
                Arrays.asList(new Contact("hyd street", "ap", "in"))));
        employeeRepository.save(new Employee("sonu", "singh", "IT",
                Arrays.asList(new Contact("ald street", "up", "in"))));
        employeeRepository.save(new Employee("amit", "kumar", "Finance",
                Arrays.asList(new Contact("delhi street", "delhi", "in"))));*/
    }
}
