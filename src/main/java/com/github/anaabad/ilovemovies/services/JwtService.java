package com.github.anaabad.ilovemovies.services;

import com.github.anaabad.ilovemovies.persistence.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("/${configuration.jwt.secret}")
    private String secret;

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;


    public Claims getClaimsFromToken(String token) {
        final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims;
    }

    public boolean isTokenExpired(String token){
        final Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }
    public String createToken(Authentication authentication) {

        Map<String, Object> claims = new HashMap<>();
        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(authentication.getName())
                .claim("role", authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

    }
    public Boolean validateToken(String token, UserEntity userEntity) {
        final String username = getClaimsFromToken(token).getSubject();
        return (username.equals(userEntity.getEmail()) && !isTokenExpired(token));
    }
}
