package com.example.hellographql.owner.graphql.type;

import lombok.Data;

@Data
public class OwnerFilter {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
}
