package com.example.hellographql.pet.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.hellographql.pet.graphql.type.Pet;
import com.example.hellographql.pet.graphql.type.VisitConnection;
import org.springframework.stereotype.Component;

@Component
public class PetResolver implements GraphQLResolver<Pet> {

    public VisitConnection visits(Pet pet) {
        return new VisitConnection(pet.getVisitList());
    }
}
