package com.example.hellographql.owner.graphql.type;

public class AbstractOwnerPayload {
    private final Owner owner;

    public Owner getOwner() {
        return owner;
    }

    public AbstractOwnerPayload(Owner owner) {
        this.owner = owner;
    }
}
