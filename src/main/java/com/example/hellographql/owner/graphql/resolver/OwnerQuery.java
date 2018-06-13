package com.example.hellographql.owner.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.hellographql.common.error.PetClinicException;
import com.example.hellographql.owner.graphql.type.Owner;
import com.example.hellographql.owner.graphql.type.OwnerFilter;
import com.example.hellographql.owner.graphql.type.OwnerOrder;
import com.example.hellographql.owner.repository.OwnerRepository;
import com.example.hellographql.owner.repository.entity.OwnerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OwnerQuery implements GraphQLQueryResolver {

    @Autowired
    private OwnerRepository ownerRepository;

    @PreAuthorize("hasRole('ROLE_USER')")
    public List<Owner> owners(OwnerFilter filter, List<OwnerOrder> orders) {
        List<Owner> list = new ArrayList<>();
        Iterable<OwnerEntity> result = ownerRepository.findAll();
        result.forEach(entity -> {
            list.add(new Owner(entity));
        });
        return list;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public Owner owner(Integer id) {
        Optional<OwnerEntity> entity = ownerRepository.findById(id);
        if (entity.isPresent()) {
            return new Owner(entity.get());
        }
        throw new PetClinicException(1001, "ownerId(" + id + ")对应的Owner不存在");
    }
}
