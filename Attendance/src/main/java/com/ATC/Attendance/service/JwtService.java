package com.ATC.Attendance.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    private String SECRET_KEY = "BE9A7E2A75F8648F1D1EF8E58125FC0E16E0A3E6533565F971F81EAE0EB2D70DDD03F376CD1580E96747BD266E48529284B7F392A8E0CB73BF13AB4D4666F66077DCD0A6E32C71003750FE862898BFBBB4DA5D0D2B163CED3E171FE05BAE232B13422FB268C4F23E8F4DC2787565218DF82C317691272FC28DC0B9C7867B782D";

    public String extractUserCode(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails){
        return generatorateToken(new HashMap<>(),userDetails);
    }

    public String generatorateToken(
        Map<String, Object> extractClaim,
        UserDetails userDetails
    ){
        extractClaim.put("role", userDetails.getAuthorities().stream().findFirst().get().getAuthority().toString());
        return Jwts
            .builder()
            .setClaims(extractClaim)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new java.util.Date())
            .setExpiration(new java.util.Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(getSignInKey(),SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String userCode = extractUserCode(token);
        return (userCode.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token){
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
