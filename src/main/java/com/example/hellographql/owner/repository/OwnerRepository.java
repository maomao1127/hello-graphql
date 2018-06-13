package com.example.hellographql.owner.repository;

import com.example.hellographql.owner.repository.entity.OwnerEntity;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<OwnerEntity, Integer> {
}
