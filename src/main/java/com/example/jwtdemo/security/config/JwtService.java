package com.example.jwtdemo.security.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtService {

    static final String ID = "id";

    static final String USERNAME = "username";

    static final String IS_ADMIN = "isAdmin";

    private final String issuer;

    private final Algorithm algorithm;

    private final JWTVerifier verifier;

    public JwtService(@Value("${com.example.security.issuer}") String issuer, Algorithm algorithm) {
        this.algorithm = algorithm;
        this.issuer = issuer;
        this.verifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    public String sign(long userId, String username, boolean isAdmin) {
        log.debug("Generating JWT for user '{}' (id: {})", username, userId);

        Date now = new Date();
        Date oneHourLater = new Date(now.getTime() + 3600_000);

        return JWT.create()
                .withIssuer(issuer)
                .withIssuedAt(now)
                .withExpiresAt(oneHourLater)
                .withClaim(ID, userId)
                .withClaim(USERNAME, username)
                .withClaim(IS_ADMIN, isAdmin)
                .sign(algorithm);
    }

    DecodedJWT verifyToken(String jwt) {
        return verifier.verify(jwt);
    }
}
