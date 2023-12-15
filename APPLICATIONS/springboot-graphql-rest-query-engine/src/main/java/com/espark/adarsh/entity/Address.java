package com.espark.adarsh.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;
    private String street;
    private String state;
    private String country;

}

