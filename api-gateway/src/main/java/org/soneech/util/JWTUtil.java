package org.soneech.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    @Value("${app.jwt.secret}")
    private String secret;
    @Value("${app.jwt.subject}")
    private String subject;
    @Value("${app.jwt.issuer}")
    private String issuer;

    public void validateToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(subject)
                .withIssuer(issuer)
                .build();
        verifier.verify(token);
    }
}
