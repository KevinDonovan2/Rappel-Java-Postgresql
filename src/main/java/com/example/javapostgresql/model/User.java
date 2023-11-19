package com.example.javapostgresql.model;

import lombok.*;
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString

public class User {
    public int userId;
    public String userName;
    public String password;
}

