package com.example.hellographql.owner.graphql.type;

import lombok.Data;

@Data
public class UpdateOwnerInput extends AbstractOwnerInput {

    private Integer ownerId;
}
