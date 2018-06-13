package com.example.hellographql.vet.repository;

import com.example.hellographql.vet.repository.entity.VetEntity;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<VetEntity, Integer> {
}
