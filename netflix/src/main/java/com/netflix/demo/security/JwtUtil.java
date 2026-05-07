package com.netflix.demo.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final long JWT_TOKEN_VALIDITY = 30L * 24 * 60 * 60 * 1000;

    @Value("%{jwt.secret:defualtSecretkeyForNetflixClone}")
    private String secret;

    private SecretKey getSigninKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String getUsernameFromToken(String token){
        return getClaimsFromToken(token, Claims::getSubject);
    }

    public String getRoleFromToken(String token){
        return getClaimsFromToken(token,claims -> claims.get("role", String.class));
    }

    public Date getExpiratiionDateFromToken(String token){
        return getClaimsFromToken(token, Claims::getExpiration);
    }


    public <T> T getClaimsFromToken(String token,Function<Claims,T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getSigninKey()).build().parseClaimsJws(token).getBody();
    }


    private Boolean isTokenExpiry(String token){
        final Date expiration = getExpiratiionDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(String userName,String role){
        Map<String , Object> claims = new HashMap<>();
        claims.put("role", role);
        //claims.put("email", email);
        return doGenerateToken(claims,userName);
    }

    private String doGenerateToken(Map<String , Object> claims, String subject){
        return Jwts
                            .builder()
                            .setClaims(claims)
                            .setSubject(subject)
                            .setIssuedAt(new Date(System.currentTimeMillis()))
                            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) 
                            .signWith(getSigninKey(),SignatureAlgorithm.HS256)
                            .compact();
    }
    public Boolean validateToken(String token){
        try{
            getAllClaimsFromToken(token);
            return !isTokenExpiry(token);
        }catch(Exception e){
            return false;
        }
    }
}