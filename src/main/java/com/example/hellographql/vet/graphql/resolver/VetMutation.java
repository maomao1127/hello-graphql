package com.example.hellographql.vet.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.hellographql.common.error.PetClinicException;
import com.example.hellographql.vet.graphql.type.*;
import com.example.hellographql.vet.repository.SpecialtyRepository;
import com.example.hellographql.vet.repository.VetRepository;
import com.example.hellographql.vet.repository.entity.SpecialtyEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class VetMutation implements GraphQLMutationResolver {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private VetRepository vetRepository;

    AddSpecialtyPayload addSpecialty(AddSpecialtyInput addSpecialtyInput) {
        SpecialtyEntity entity = new SpecialtyEntity();
        entity.setName(addSpecialtyInput.getName());

        specialtyRepository.save(entity);
        return new AddSpecialtyPayload(new Specialty(entity));
    }

    UpdateSpecialtyPayload updateSpecialty(UpdateSpecialtyInput updateSpecialtyInput) {
        Optional<SpecialtyEntity> specialtyEntity = specialtyRepository.findById(updateSpecialtyInput.getSpecialtyId());
        if (specialtyEntity.isPresent()) {
            specialtyEntity.get().setName(updateSpecialtyInput.getName());
            specialtyRepository.save(specialtyEntity.get());
            return new UpdateSpecialtyPayload(new Specialty(specialtyEntity.get()));
        }
        throw new PetClinicException(3001, "specialtyId(" + updateSpecialtyInput.getSpecialtyId() + ")对应的Specialty不存在");
    }

    RemoveSpecialtyPayload removeSpecialty(RemoveSpecialtyInput removeSpecialtyInput) {
        Optional<SpecialtyEntity> specialtyEntity = specialtyRepository.findById(removeSpecialtyInput.getSpecialtyId());
        if (specialtyEntity.isPresent()) {
            specialtyRepository.delete(specialtyEntity.get());
            Iterable<SpecialtyEntity> result = specialtyRepository.findAll();
            List<Specialty> list = new ArrayList<>();
            result.forEach(entity -> {
                list.add(new Specialty(entity));
            });
            return new RemoveSpecialtyPayload(list);
        }
        throw new PetClinicException(3001, "specialtyId(" + removeSpecialtyInput.getSpecialtyId() + ")对应的Specialty不存在");
    }
}
