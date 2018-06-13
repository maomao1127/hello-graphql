package com.example.hellographql.common.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

@Service
public class JwtTokenService {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);
    private static final String ROLE_PREFIX = "ROLE_";
    private static final String REDIS_JWT_PREFIX = "jwt:userId:";
    @Value("${jwt.enabled}")
    private String enabled;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expires}")
    private String expires;
    @Value("${jwt.prefix}")
    private String prefix;

    public String getJwt(String userId, Long expiryTime) throws IOException {
        logger.debug("expires  = {}", expires);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        String jwt = JWT.create()
                .withSubject(userId)
                .withClaim("role", ROLE_PREFIX + "USER")
                .withExpiresAt(new Date(expiryTime))
                .sign(algorithm);
        logger.debug("create jwt for {} , jwt = {}", userId, jwt);
        return jwt;
    }

    public boolean verifyJwt(String jwt) throws Exception {
        if ("false".equals(enabled)) {
            return true;
        }
        if (jwt == null) {
            throw new Exception("jwt is null!");
        }
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        String token = jwt.replace(prefix, "");
        logger.debug("token = [{}] ", token);
        try {
            DecodedJWT decodedJwt = verifier.verify(token);
            Date expiresIn = decodedJwt.getExpiresAt();
            String userId = decodedJwt.getSubject();
            // logger.debug("userId = {} , expiresIn = {} ", userId, ConcurrentDateUtils.format(expiresIn));
        } catch (TokenExpiredException e) {
            logger.debug("expired token {}", jwt);
            throw new Exception("token has expired");
        } catch (SignatureVerificationException e) {
            logger.debug("invalid token {}", jwt);
            throw new Exception("token is invalid");
        } catch (Exception e) {
            throw e;
        }
        return true;

    }

    public String getUserIdFromToken(String jwt) throws Exception {
        if (jwt == null) {
            throw new Exception("jwt is null!");
        }
        return JWT.decode(jwt).getSubject();
    }

    public String getRoleFromToken(String jwt) throws Exception {
        if (jwt == null) {
            throw new Exception("jwt is null!");
        }
        return JWT.decode(jwt).getClaim("role").asString();
    }
}
