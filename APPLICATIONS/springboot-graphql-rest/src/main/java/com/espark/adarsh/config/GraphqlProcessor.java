package com.espark.adarsh.config;

import com.espark.adarsh.bean.Employee;
import com.espark.adarsh.service.ApplicationService;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public class GraphqlProcessor {

    @Autowired
    ApplicationService applicationService;



    public DataFetcher<String> getMessageFetcher() {
        return new DataFetcher<String>() {
            @Override
            public String get(DataFetchingEnvironment environment) {
                String name = environment.getGraphQlContext().get("name");
                return applicationService.getMessage(name);
            }
        };
    }

    public DataFetcher<Employee> getEmployeeFetcher() {
        return new DataFetcher<Employee>() {
            @Override
            public Employee get(DataFetchingEnvironment environment) {
                Integer id = environment.getGraphQlContext().get("id");
                return applicationService.getEmployee(id);
            }
        };
    }

    public DataFetcher<List<Employee>> getEmployeesFetcher() {
        return new DataFetcher<List<Employee>>() {
            @Override
            public List<Employee> get(DataFetchingEnvironment environment) {
                return applicationService.getEmployees();
            }
        };
    }


    public DataFetcher<Map<String,Object>> getDataFetcher() {
        return new DataFetcher<Map<String,Object>>() {
            @Override
            public Map<String,Object> get(DataFetchingEnvironment environment) {
                String name = environment.getGraphQlContext().get("name");
                return applicationService.getData(name);
            }
        };
    }
}
