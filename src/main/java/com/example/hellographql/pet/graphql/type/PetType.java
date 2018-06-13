package com.example.hellographql.pet.graphql.type;

import com.example.hellographql.pet.repository.entity.PetTypeEntity;
import lombok.Data;

@Data
public class PetType {
    private Integer id;
    private String name;

    public PetType(PetTypeEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
