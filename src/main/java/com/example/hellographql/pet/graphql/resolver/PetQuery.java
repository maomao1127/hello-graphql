package com.example.hellographql.pet.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.hellographql.common.error.PetClinicException;
import com.example.hellographql.owner.graphql.type.Owner;
import com.example.hellographql.pet.graphql.type.Pet;
import com.example.hellographql.pet.graphql.type.PetType;
import com.example.hellographql.pet.repository.PetRepository;
import com.example.hellographql.pet.repository.PetTypeRepository;
import com.example.hellographql.pet.repository.entity.PetEntity;
import com.example.hellographql.pet.repository.entity.PetTypeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PetQuery implements GraphQLQueryResolver {

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private PetRepository petRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    public List<PetType> pettypes() {
        List<PetType> list = new ArrayList<>();
        Iterable<PetTypeEntity> result = petTypeRepository.findAll();
        result.forEach(entity -> {
            list.add(new PetType(entity));
        });
        return list;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public Pet pet(Integer id) {
        Optional<PetEntity> entity = petRepository.findById(id);
        if (entity.isPresent()) {
            return new Pet(entity.get(), new Owner(entity.get().getOwner()));
        }
        throw new PetClinicException(2003, "petId(" + id + ")对应的Pet不存在");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Pet> pets() {
        List<Pet> list = new ArrayList<>();
        Iterable<PetEntity> result = petRepository.findAll();
        result.forEach(entity -> {
            list.add(new Pet(entity, new Owner(entity.getOwner())));
        });
        return list;
    }
}
