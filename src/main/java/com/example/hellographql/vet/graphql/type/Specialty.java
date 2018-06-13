package com.example.hellographql.vet.graphql.type;

import com.example.hellographql.vet.repository.entity.SpecialtyEntity;
import lombok.Data;

@Data
public class Specialty {
    private Integer id;
    private String name;

    public Specialty(SpecialtyEntity entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
