package com.example.hellographql.vet.graphql.type;

import lombok.Data;

@Data
public class UpdateSpecialtyInput {
    private Integer specialtyId;
    private String name;
}
