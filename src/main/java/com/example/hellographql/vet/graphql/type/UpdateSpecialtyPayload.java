package com.example.hellographql.vet.graphql.type;

public class UpdateSpecialtyPayload {
    private final Specialty specialty;

    public Specialty getSpecialty() {
        return specialty;
    }

    public UpdateSpecialtyPayload(Specialty specialty) {
        this.specialty = specialty;
    }
}
