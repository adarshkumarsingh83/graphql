package com.espark.adarsh.entity;

import com.espark.adarsh.annotation.GraphQueries;
import com.espark.adarsh.entity.converter.PhoneListConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;



@Data
@Entity
@Table(name = "contacts")
@GraphQueries(value = "contacts{*}")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Long contactId;
    private String type;
    private String email;
    @Convert(converter = PhoneListConverter.class)
    private List<String> phone;

}

