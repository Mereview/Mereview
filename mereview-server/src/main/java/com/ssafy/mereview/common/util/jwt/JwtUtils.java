package com.ssafy.mereview.common.util.jwt;

import com.ssafy.mereview.domain.member.entity.Member;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtils {
    private static String secret;

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

    public String generateEmailToken(String email){
        Date now = new Date();
        Date accessTokenExpiration = new Date(now.getTime() + accessExpirationMs);

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiration)
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

    public static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
