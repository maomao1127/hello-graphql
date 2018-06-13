package com.example.hellographql.vet.graphql.type;

public class AddSpecialtyPayload {
    private final Specialty specialty;

    public Specialty getSpecialty() {
        return specialty;
    }

    public AddSpecialtyPayload(Specialty specialty) {
        this.specialty = specialty;
    }
}
