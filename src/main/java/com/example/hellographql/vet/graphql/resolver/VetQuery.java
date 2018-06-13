package com.example.hellographql.vet.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.hellographql.vet.graphql.type.Specialty;
import com.example.hellographql.vet.repository.SpecialtyRepository;
import com.example.hellographql.vet.repository.entity.SpecialtyEntity;
import com.example.hellographql.vet.graphql.type.Vet;
import com.example.hellographql.vet.repository.VetRepository;
import com.example.hellographql.vet.repository.entity.VetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VetQuery implements GraphQLQueryResolver {

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Vet> vets() {
        List<Vet> list = new ArrayList<>();
        Iterable<VetEntity> result = vetRepository.findAll();
        result.forEach(entity -> {
            list.add(new Vet(entity));
        });
        return list;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Specialty> specialties() {
        List<Specialty> list = new ArrayList<>();
        Iterable<SpecialtyEntity> result = specialtyRepository.findAll();
        result.forEach(entity -> {
            list.add(new Specialty(entity));
        });
        return list;
    }
}
