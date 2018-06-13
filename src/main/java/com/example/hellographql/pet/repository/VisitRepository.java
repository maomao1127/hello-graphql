package com.example.hellographql.pet.repository;

import com.example.hellographql.pet.repository.entity.VisitEntity;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<VisitEntity, Integer> {
}
