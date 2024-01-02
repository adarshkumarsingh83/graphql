package com.espark.adarsh.entity;


import com.espark.adarsh.annotation.GraphRootQuery;
import com.espark.adarsh.annotation.GraphSubQuery;
import com.espark.adarsh.annotation.GraphQuery;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "departments")
@GraphSubQuery(value = "departments{*.*}")
@GraphRootQuery(value = "departments{*.*}")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long deptId;
    private String name;

    @GraphQuery(value = "address{*.*}", classType = Address.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "address_id")
    Address address;

    @GraphQuery(value = "contact{*.*}", classType = Contact.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "contact_id")
    Contact contact;

}

