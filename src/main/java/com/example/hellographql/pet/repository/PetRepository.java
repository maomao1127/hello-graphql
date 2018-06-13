package com.example.hellographql.pet.repository;

import com.example.hellographql.pet.repository.entity.PetEntity;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<PetEntity, Integer> {
}
