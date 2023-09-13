package com.espark.adarsh.bean;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {

    Integer id;
    String name;
    LocalDate dob;
    Gender gender;
}
