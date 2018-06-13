package com.example.hellographql.pet.graphql.type;

public class AbstractPetPayload {
    private final Pet pet;

    public AbstractPetPayload(Pet pet) {
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }
}
