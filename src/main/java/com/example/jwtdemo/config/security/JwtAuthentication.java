package com.example.jwtdemo.config.security;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
public class JwtAuthentication implements Authentication {

    private static final Collection<GrantedAuthority> USER_AUTHORITIES;

    private static final Collection<GrantedAuthority> ADMIN_AUTHORITIES;

    static {
        val roleUser = new SimpleGrantedAuthority("ROLE_USER");
        val roleAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
        USER_AUTHORITIES = List.of(roleUser);
        ADMIN_AUTHORITIES = List.of(roleUser, roleAdmin);
    }

    private final long id;

    private final String username;

    private final boolean isAdmin;

    private final boolean isAuthenticated;

    private final String token;

    JwtAuthentication(String token) {
        this.token = token;
        this.id = 0L;
        this.username = null;
        this.isAdmin = false;
        this.isAuthenticated = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isAdmin)
            return ADMIN_AUTHORITIES;
        return USER_AUTHORITIES;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object getPrincipal() {
        return id;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    @Override
    public String getName() {
        return username;
    }
}
