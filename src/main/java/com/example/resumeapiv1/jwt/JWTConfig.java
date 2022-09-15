package com.example.resumeapiv1.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTConfig {


    @Value("${jwt_secret}")
    private String secret;

    public String createToken(String username, HttpServletResponse res) {
        Date expireDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());
        String token = JWT.create()
                .withSubject("User details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("resume")
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC256(secret));
        res.addHeader("Authorization", token);
        return token;
    }

    public String getClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("resume")
                .build();
        return verifier.verify(token).getClaim("username").asString();
    }

}
