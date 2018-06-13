package com.example.hellographql.vet.graphql.type;

import com.example.hellographql.common.type.Person;
import com.example.hellographql.vet.repository.entity.VetEntity;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Vet extends Person {
    private Integer id;
    private String firstName;
    private String lastName;
    private List<Specialty> specialties;

    public Vet(VetEntity entity) {
        this.setId(entity.getId());
        this.setFirstName(entity.getFirstName());
        this.setLastName(entity.getLastName());
        this.setSpecialties(entity.getSpecialties().stream()
                .map(specialtyEntity -> {
                    return new Specialty(specialtyEntity);
                })
                .collect(Collectors.toList()));
    }
}
