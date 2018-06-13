package com.example.hellographql.pet.repository;

import com.example.hellographql.pet.repository.entity.PetTypeEntity;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetTypeEntity, Integer> {
}
