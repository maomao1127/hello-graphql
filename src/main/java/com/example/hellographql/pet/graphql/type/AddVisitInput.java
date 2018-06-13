package com.example.hellographql.pet.graphql.type;

import lombok.Data;

import java.util.Date;

@Data
public class AddVisitInput {
    private Integer petId;
    private String description;
    private Date date;
}
