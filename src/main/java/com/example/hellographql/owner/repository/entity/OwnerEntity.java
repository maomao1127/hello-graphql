package com.example.hellographql.owner.repository.entity;

import com.example.hellographql.owner.graphql.type.Owner;
import com.example.hellographql.pet.repository.entity.PetEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "owners", schema = "petclinic", catalog = "")
public class OwnerEntity {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;

    public OwnerEntity() {
    }

    public OwnerEntity(Owner owner) {
        this.setAddress(owner.getAddress());
        this.setCity(owner.getCity());
        this.setFirstName(owner.getFirstName());
        this.setLastName(owner.getLastName());
        this.setTelephone(owner.getTelephone());
    }

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
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "telephone")
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    private Set<PetEntity> pets;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    public Set<PetEntity> getPets() {
        return pets;
    }

    public void setPets(Set<PetEntity> pets) {
        this.pets = pets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerEntity that = (OwnerEntity) o;
        return id == that.id &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(address, that.address) &&
                Objects.equals(city, that.city) &&
                Objects.equals(telephone, that.telephone);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, lastName, address, city, telephone);
    }
}
