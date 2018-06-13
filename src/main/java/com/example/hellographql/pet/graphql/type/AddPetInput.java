package com.example.hellographql.pet.graphql.type;

import lombok.Data;

@Data
public class AddPetInput extends AbstractPetInput {
    private Integer ownerId;
}
