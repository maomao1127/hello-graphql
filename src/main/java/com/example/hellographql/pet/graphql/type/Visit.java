package com.example.hellographql.pet.graphql.type;

import com.example.hellographql.pet.repository.entity.VisitEntity;
import lombok.Data;

import java.util.Date;

@Data
public class Visit {
    private Integer id;
    private String description;
    private Pet pet;
    private Date date;

    public Visit() {
    }

    public Visit(VisitEntity entity, Pet pet) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.pet = pet;
        this.date = entity.getVisitDate();
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}
