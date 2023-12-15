package com.espark.adarsh.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "projects")
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "address_id")
    Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "contact_id")
    Contact contact;


}