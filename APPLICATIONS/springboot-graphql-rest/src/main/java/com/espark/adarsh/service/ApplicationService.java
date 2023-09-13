package com.espark.adarsh.service;

import com.espark.adarsh.bean.Employee;
import com.espark.adarsh.bean.Gender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationService {

    public String getMessage(String name){
        return "welcome to the espark "+name;
    }

    public Map<String,Object> getData(String name){
        return new HashMap<>(){
            {
                put("name",name);
                put("message","welcome to the espark " +name);
                put("date",LocalDate.now());
            }
        };
    }

    public Employee getEmployee(Integer id){
        return new Employee(){
            {
                setId(100);
                setName("adarsh kumar");
                setDob(LocalDate.now());
                setGender(Gender.MALE);
            }
        };
    }

    public List<Employee> getEmployees(){
        return List.of( new Employee(){
            {
                setId(100);
                setName("adarsh kumar");
                setDob(LocalDate.now());
                setGender(Gender.MALE);
            }
        },
        new Employee(){
            {
                setId(101);
                setName("raddh singh");
                setDob(LocalDate.now());
                setGender(Gender.FEMALE);
            }
        });
    }
}
