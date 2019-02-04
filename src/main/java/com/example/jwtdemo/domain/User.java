package com.example.jwtdemo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@Data
@ToString(exclude = "password")
@EqualsAndHashCode(exclude = "password")
public class User {

    @NonNull
    private final long id;

    @NonNull
    private final String login;

    @NonNull
    private final String password;

    @NonNull
    private final boolean isAdmin;
}
