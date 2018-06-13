package com.example.hellographql.pet.graphql.type;

import lombok.Data;

import java.util.List;

@Data
public class VisitConnection {
    private Integer totalCount;
    private List<Visit> visits;

    public VisitConnection(List<Visit> list) {
        this.totalCount = list.size();
        this.visits = list;
    }
}
