package com.example.jwtdemo.domain;

import lombok.Data;
import lombok.NonNull;

@Data
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
