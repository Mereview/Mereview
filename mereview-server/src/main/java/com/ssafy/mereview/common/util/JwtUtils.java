package com.ssafy.mereview.common.util;

import com.ssafy.mereview.domain.member.entity.Member;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    private String secret;
    private int accessExpirationMs;

    private int refreshTokenExpirationMs;


    public JwtUtils(String secret, int accessExpirationMs, int refreshTokenExpirationMs){
        this.secret = secret;
        this.accessExpirationMs = accessExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public Map<String, String> generateJwt(Member member) {


        Date now = new Date();
        Date accessTokenExpiration = new Date(now.getTime() + accessExpirationMs);
        Date refreshTokenExpiration = new Date(now.getTime() + refreshTokenExpirationMs);

        String accessToken = Jwts.builder()
                .setSubject(member.getEmail())
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiration)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(member.getEmail())
                .setIssuedAt(now)
                .setExpiration(refreshTokenExpiration)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return tokens;

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
