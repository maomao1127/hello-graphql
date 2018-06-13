package com.example.hellographql.pet.graphql.type;

import lombok.Data;

@Data
public class UpdatePetInput extends AbstractPetInput {
    private Integer petId;
}
