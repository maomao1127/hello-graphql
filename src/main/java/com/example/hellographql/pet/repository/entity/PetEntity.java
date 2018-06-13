package com.example.hellographql.pet.repository.entity;

import com.example.hellographql.owner.repository.entity.OwnerEntity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "pets", schema = "petclinic", catalog = "")
public class PetEntity {
    private int id;
    private String name;
    private Date birthDate;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "birth_date")
    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    private PetTypeEntity petType;


    @ManyToOne
    @JoinColumn(name = "type_id")
    public PetTypeEntity getPetType() {
        return petType;
    }

    public void setPetType(PetTypeEntity petType) {
        this.petType = petType;
    }

    private OwnerEntity owner;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    public OwnerEntity getOwner() {
        return owner;
    }

    public void setOwner(OwnerEntity owner) {
        this.owner = owner;
    }

    private Set<VisitEntity> visits;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
    public Set<VisitEntity> getVisits() {
        return visits;
    }

    public void setVisits(Set<VisitEntity> visits) {
        this.visits = visits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PetEntity that = (PetEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(birthDate, that.birthDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, birthDate);
    }
}
