package com.example.hellographql.vet.repository;

import com.example.hellographql.vet.repository.entity.SpecialtyEntity;
import org.springframework.data.repository.CrudRepository;

public interface SpecialtyRepository extends CrudRepository<SpecialtyEntity, Integer> {
}
