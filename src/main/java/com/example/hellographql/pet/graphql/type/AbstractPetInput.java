package com.example.hellographql.pet.graphql.type;

import lombok.Data;

import java.util.Date;

@Data
public class AbstractPetInput {
    private String name;
    private Date birthDate;
    private Integer typeId;
}
