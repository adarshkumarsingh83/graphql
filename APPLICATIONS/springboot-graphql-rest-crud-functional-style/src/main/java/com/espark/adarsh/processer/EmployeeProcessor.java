package com.espark.adarsh.processer;

import com.espark.adarsh.bean.EmployeeBean;
import com.espark.adarsh.entity.Employee;
import com.espark.adarsh.exception.EmployeeNotFoundException;
import com.espark.adarsh.filter.EmployeeFilter;
import com.espark.adarsh.functions.GraphqlInputProcessorFunction;
import com.espark.adarsh.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class EmployeeProcessor {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ObjectMapper objectMapper;

    private  final GraphqlInputProcessorFunction<DataFetchingEnvironment,String,EmployeeBean>
     graphqlInputProcessorFunction = (environment, paramName) -> {
        Map<String,Object> employeeMapBean = environment.getGraphQlContext().get(paramName);
        EmployeeBean employeeBean=null;
        try {
            if (employeeMapBean != null && !employeeMapBean.isEmpty()) {
                String data = objectMapper.writeValueAsString(employeeMapBean);
                employeeBean = objectMapper.readValue(data, EmployeeBean.class);
            }
            log.info("Graphql Response {}", employeeBean.getEmployee());

        } catch (JsonProcessingException jsonProcessingException) {
            log.error(jsonProcessingException.getMessage());
        }
        return employeeBean;
    };


    public DataFetcher<List<Employee>> getAllEmployee() {
        return new DataFetcher<List<Employee>>() {
            @Override
            public List<Employee> get(DataFetchingEnvironment environment) {
                return employeeService.getAllEmployee();
            }
        };
    }

    public DataFetcher<Employee> getEmployee() throws EmployeeNotFoundException {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                Long id = Long.parseLong(environment.getGraphQlContext().get("id"));
                Employee employee = null;
                DataFetcherResult dataFetcherResult = null;
                try {
                    employee = employeeService.getEmployee(id);
                    dataFetcherResult = new DataFetcherResult(employee, List.of());
                } catch (EmployeeNotFoundException enf) {
                    dataFetcherResult = new DataFetcherResult(employee, List.of(enf));
                }
                return dataFetcherResult;
            }
        };
    }

    public DataFetcher<Employee> removeEmployee() throws EmployeeNotFoundException {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                Long id = Long.parseLong(environment.getGraphQlContext().get("id")+"");
                Employee employee = null;
                DataFetcherResult dataFetcherResult = null;
                try {
                    employee = employeeService.removeEmployee(id);
                    dataFetcherResult = new DataFetcherResult(employee, List.of());
                } catch (EmployeeNotFoundException enf) {
                    dataFetcherResult = new DataFetcherResult(employee, List.of(enf));
                }
                return dataFetcherResult;
            }
        };
    }


    public DataFetcher<LinkedHashMap<String,Object>> saveEmployee() {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                Employee employee = null;
                DataFetcherResult dataFetcherResult = null;
                try {
                   EmployeeBean employeeBean = graphqlInputProcessorFunction.processConversion(environment,"employeeBean");
                    employee = employeeService.saveEmployee(employeeBean.getEmployee());
                    dataFetcherResult = new DataFetcherResult(employee, List.of());
                } catch (Exception e) {
                    dataFetcherResult = new DataFetcherResult(employee, List.of(e));
                }
                return dataFetcherResult;
            }
        };
    }

    public DataFetcher<Employee> updateEmployee() throws EmployeeNotFoundException {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                Employee employee = null;
                DataFetcherResult dataFetcherResult = null;
                try {
                    EmployeeBean employeeBean = graphqlInputProcessorFunction.processConversion(environment,"employeeBean");
                    employee = employeeService.updateEmployee(employeeBean.getId(), employeeBean.getEmployee());
                    dataFetcherResult = new DataFetcherResult(employee, List.of());
                } catch (Exception e) {
                    dataFetcherResult = new DataFetcherResult(employee, List.of(e));
                }
                return dataFetcherResult;
            }
        };
    }

    public DataFetcher<Employee> updatePartialEmployee() throws EmployeeNotFoundException {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                Employee employee = null;
                DataFetcherResult dataFetcherResult = null;
                try {
                    Map<String,Object> employeeMapBean = environment.getGraphQlContext().get("employeeBean");
                    Long id = Long.parseLong(employeeMapBean.get("id")+"");
                    employeeMapBean.remove("id");
                    employee = employeeService.updatePartialEmployee(id,employeeMapBean);
                    dataFetcherResult = new DataFetcherResult(employee, List.of());
                } catch (Exception e) {
                    dataFetcherResult = new DataFetcherResult(employee, List.of(e));
                }
                return dataFetcherResult;
            }
        };
    }

    public DataFetcher<Iterable<Employee>> employeesFilter() throws EmployeeNotFoundException {
        return new DataFetcher() {
            @Override
            public Object get(DataFetchingEnvironment environment) {
                Iterable<Employee> iterable = null;
                DataFetcherResult dataFetcherResult = null;
                try {
                    Map<String,Object> filterMap = environment.getGraphQlContext().get("filter");
                    String data = objectMapper.writeValueAsString(filterMap);
                    EmployeeFilter filter = objectMapper.readValue(data, new TypeReference<EmployeeFilter>() {});
                    iterable = employeeService.employeesFilter(filter);
                    dataFetcherResult = new DataFetcherResult(iterable, List.of());
                } catch (Exception e) {
                    dataFetcherResult = new DataFetcherResult(iterable, List.of(e));
                }
                return dataFetcherResult;
            }
        };
    }
}
