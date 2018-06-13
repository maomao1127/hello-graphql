package com.example.hellographql.pet.graphql.type;

public class AddVisitPayload {
    private final Visit visit;

    public AddVisitPayload(Visit visit) {
        this.visit = visit;
    }

    public Visit getVisit() {
        return visit;
    }
}
