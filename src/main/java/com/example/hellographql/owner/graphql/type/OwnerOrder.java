package com.example.hellographql.owner.graphql.type;

import lombok.Data;

@Data
public class OwnerOrder {
    private OrderField field;
    private OrderType order;
}
