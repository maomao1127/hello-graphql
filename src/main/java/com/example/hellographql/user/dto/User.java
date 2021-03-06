package com.example.hellographql.user.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private Date lastPasswordResetDate;
    private List<String> roles;
}
