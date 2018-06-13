package com.example.hellographql.owner.graphql.type;

import com.example.hellographql.common.type.Person;
import com.example.hellographql.owner.repository.entity.OwnerEntity;
import com.example.hellographql.pet.graphql.type.Pet;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Owner extends Person {
    private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
    private List<Pet> pets;

    public Owner() {
    }

    public Owner(OwnerEntity entity) {
        this.setId(entity.getId());
        this.setFirstName(entity.getFirstName());
        this.setLastName(entity.getLastName());
        this.address = entity.getAddress();
        this.city = entity.getCity();
        this.telephone = entity.getTelephone();
        this.pets = entity.getPets().stream()
                .map(petEntity -> {
                    return new Pet(petEntity, this);
                })
                .collect(Collectors.toList());
    }
}
