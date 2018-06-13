package com.example.hellographql.pet.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.hellographql.common.error.PetClinicException;
import com.example.hellographql.owner.graphql.type.Owner;
import com.example.hellographql.owner.repository.OwnerRepository;
import com.example.hellographql.owner.repository.entity.OwnerEntity;
import com.example.hellographql.pet.graphql.type.*;
import com.example.hellographql.pet.repository.PetRepository;
import com.example.hellographql.pet.repository.PetTypeRepository;
import com.example.hellographql.pet.repository.VisitRepository;
import com.example.hellographql.pet.repository.entity.PetEntity;
import com.example.hellographql.pet.repository.entity.PetTypeEntity;
import com.example.hellographql.pet.repository.entity.VisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Component
public class PetMutation implements GraphQLMutationResolver {
    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Autowired
    private VisitRepository visitRepository;

    public AddPetPayload addPet(AddPetInput addPetInput) {
        Optional<OwnerEntity> ownerEntity = ownerRepository.findById(addPetInput.getOwnerId());
        Optional<PetTypeEntity> petTypeEntity = petTypeRepository.findById(addPetInput.getTypeId());

        if (!ownerEntity.isPresent()) {
            throw new PetClinicException(2001, "ownerId(" + addPetInput.getOwnerId() + ")对应的Owner不存在");
        }
        if (!petTypeEntity.isPresent()) {
            throw new PetClinicException(2002, "typeId(" + addPetInput.getTypeId() + ")对应的PetType不存在");
        }

        final PetEntity pet = new PetEntity();
        pet.setBirthDate(new java.sql.Date(addPetInput.getBirthDate().getTime()));
        pet.setName(addPetInput.getName());
        pet.setOwner(ownerEntity.get());
        pet.setPetType(petTypeEntity.get());

        petRepository.save(pet);
        return new AddPetPayload(new Pet(pet, new Owner(ownerEntity.get())));
    }

    public UpdatePetPayload updatePet(UpdatePetInput updatePetInput) {
        Optional<PetEntity> entity = petRepository.findById(updatePetInput.getPetId());
        if (entity.isPresent()) {
            if (!StringUtils.isEmpty(updatePetInput.getName())) {
                entity.get().setName(updatePetInput.getName());
            }
            if (!StringUtils.isEmpty(updatePetInput.getBirthDate())) {
                entity.get().setBirthDate(new java.sql.Date(updatePetInput.getBirthDate().getTime()));
            }
            if (!StringUtils.isEmpty(updatePetInput.getTypeId())) {
                Optional<PetTypeEntity> petTypeEntity = petTypeRepository.findById(updatePetInput.getTypeId());
                entity.get().setPetType(petTypeEntity.get());
            }
            petRepository.save(entity.get());
            return new UpdatePetPayload(new Pet(entity.get(), new Owner(entity.get().getOwner())));
        }
        throw new PetClinicException(2003, "petId(" + updatePetInput.getPetId() + ")对应的Pet不存在");
    }

    public AddVisitPayload addVisit(AddVisitInput addVisitInput) {
        VisitEntity visit = new VisitEntity();
        visit.setDescription(addVisitInput.getDescription());
        visit.setVisitDate(new java.sql.Date(addVisitInput.getDate().getTime()));

        Optional<PetEntity> petEntity = petRepository.findById(addVisitInput.getPetId());
        if (petEntity.isPresent()) {
            visit.setPet(petEntity.get());
        }
        visitRepository.save(visit);

        Pet pet = new Pet(petEntity.get(), new Owner(petEntity.get().getOwner()));
        return new AddVisitPayload(new Visit(visit, pet));
    }
}
