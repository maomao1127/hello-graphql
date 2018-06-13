package com.example.hellographql.vet.graphql.type;

import java.util.List;

public class RemoveSpecialtyPayload {
    private final List<Specialty> specialties;

    public RemoveSpecialtyPayload(List<Specialty> specialties) {
        this.specialties = specialties;
    }

    public List<Specialty> getSpecialties() {
        return specialties;
    }
}
