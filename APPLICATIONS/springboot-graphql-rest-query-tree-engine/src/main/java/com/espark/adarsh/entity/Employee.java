package com.espark.adarsh.entity;


import com.espark.adarsh.annotation.GraphRootQuery;
import com.espark.adarsh.annotation.GraphQuery;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
@Table(name = "employees")
@GraphRootQuery(value = "employee{*.*}", level = 2)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;
    private String firstName;
    private String lastName;
    private String career;

    @GraphQuery(value = "contact{*.*}",classType = Contact.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "contact_id")
    private Contact contact;

    @GraphQuery(value = "address{*.*}",classType = Address.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "address_id")
    private Address address;

    @GraphQuery(value = "department{*.*}",classType = Department.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "department_id")
    private Department department;

    @GraphQuery(value = "projects{*.*}",classType = Projects.class)
    @OneToMany(cascade = CascadeType.ALL
            , orphanRemoval = true)
    @JoinColumn(name = "project_id")
    List<Projects> projects;



}

