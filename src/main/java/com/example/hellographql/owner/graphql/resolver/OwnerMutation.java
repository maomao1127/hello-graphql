package com.example.hellographql.owner.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.hellographql.common.error.PetClinicException;
import com.example.hellographql.owner.graphql.type.*;
import com.example.hellographql.owner.repository.OwnerRepository;
import com.example.hellographql.owner.repository.entity.OwnerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Optional;

@Component
public class OwnerMutation implements GraphQLMutationResolver {

    @Autowired
    private OwnerRepository ownerRepository;

    public AddOwnerPayload addOwner(AddOwnerInput addOwnerInput) {
        final Owner owner = new Owner();
        owner.setAddress(addOwnerInput.getAddress());
        owner.setCity(addOwnerInput.getCity());
        owner.setTelephone(addOwnerInput.getTelephone());
        owner.setFirstName(addOwnerInput.getFirstName());
        owner.setLastName(addOwnerInput.getLastName());

        OwnerEntity ownerEntity = ownerRepository.save(new OwnerEntity(owner));
        owner.setId(ownerEntity.getId());
        owner.setPets(new ArrayList<>());
        return new AddOwnerPayload(owner);
    }

    public UpdateOwnerPayload updateOwner(UpdateOwnerInput updateOwnerInput) {
        Optional<OwnerEntity> entity = ownerRepository.findById(updateOwnerInput.getOwnerId());
        if (entity.isPresent()) {
            if (!StringUtils.isEmpty(updateOwnerInput.getAddress())) {
                entity.get().setAddress(updateOwnerInput.getAddress());
            }
            if (!StringUtils.isEmpty(updateOwnerInput.getCity())) {
                entity.get().setCity(updateOwnerInput.getCity());
            }
            if (!StringUtils.isEmpty(updateOwnerInput.getFirstName())) {
                entity.get().setFirstName(updateOwnerInput.getFirstName());
            }
            if (!StringUtils.isEmpty(updateOwnerInput.getLastName())) {
                entity.get().setLastName(updateOwnerInput.getLastName());
            }
            if (!StringUtils.isEmpty(updateOwnerInput.getTelephone())) {
                entity.get().setTelephone(updateOwnerInput.getTelephone());
            }
            ownerRepository.save(entity.get());
            return new UpdateOwnerPayload(new Owner(entity.get()));
        }

        throw new PetClinicException(1001, "ownerId(" + updateOwnerInput.getOwnerId() + ")对应的Owner不存在");
    }
}
