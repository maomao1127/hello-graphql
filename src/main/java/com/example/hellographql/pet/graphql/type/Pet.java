package com.example.hellographql.pet.graphql.type;

import com.example.hellographql.owner.graphql.type.Owner;
import com.example.hellographql.pet.repository.entity.PetEntity;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Pet {
    private Integer id;
    private String name;
    private PetType type;
    private Date birthDate;
    private Owner owner;
    private List<Visit> visitList;

    public Pet() {
    }

    public Pet(PetEntity entity, Owner owner) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.birthDate = entity.getBirthDate();
        this.type = new PetType(entity.getPetType());
        this.owner = owner;
        this.visitList = CollectionUtils.isEmpty(entity.getVisits()) ? new ArrayList<>() :
                entity.getVisits().stream()
                        .map(visitEntity -> {
                            return new Visit(visitEntity, this);
                        })
                        .sorted(Comparator.comparing(Visit::getDate).reversed())
                        .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", birthDate=" + birthDate +
                ", visitList=" + visitList +
                '}';
    }
}
