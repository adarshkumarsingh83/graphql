package com.espark.adarsh.entity;

import com.espark.adarsh.annotation.GraphRootQuery;
import com.espark.adarsh.annotation.GraphSubQuery;
import com.espark.adarsh.annotation.GraphQuery;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "projects")
@GraphSubQuery(value = "projects{*.*}")
@GraphRootQuery(value = "projects{*.*}")
public class Projects implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    protected Long projectId;
    protected String name;

    @Column(name = "startDate", columnDefinition = "DATE")
    private LocalDate startDate;

    @Column(name = "endDate", columnDefinition = "DATE")
    private LocalDate endDate;

    @GraphQuery(value = "address{*.*}",classType = Address.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "address_id")
    Address address;

    @GraphQuery(value = "contact{*.*}",classType = Contact.class)
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "contact_id")
    Contact contact;

}