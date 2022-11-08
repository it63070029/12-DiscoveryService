package com.example.gatewayservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.crypto.Data;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    private Key key;
    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());

    }
    public Claims getAllClaimsFromToken(String token){
        System.out.println("Token : "+token);
        return Jwts.parerBuilder().setSigningKey(key).build
                .parseClaimsJws(token).getBody();
    }
    private boolean isTokenExpired(String token){
        return this.getAllClaimsFromToken(token).getExpireation().before(new Date());
    }
    public boolean isInvalid(String token){
        return this.isTokenExpired(token);
    }
}
