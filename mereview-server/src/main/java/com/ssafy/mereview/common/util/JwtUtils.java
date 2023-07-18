package com.ssafy.mereview.common.util;

import com.ssafy.mereview.domain.member.entity.Member;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

public class JwtUtils {
    private String secret;
    private int jwtExpirationMs;

    public JwtUtils(String secret, int jwtExpirationMs) {
        this.secret = secret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generateJwt(Member member) {
        return Jwts.builder()
                .setSubject(member.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwt(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (JwtException e) {
            // log the error
        }

        return false;
    }
}
