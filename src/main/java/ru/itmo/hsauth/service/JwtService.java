package ru.itmo.hsauth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String JWT_SIGNING_KEY;
    @Value("${jwt.lifetime}")
    public Duration JWT_LIFE_TIME;


    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        var rolesList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", rolesList);

        return generateToken(userDetails, claims);
    }

    public Claims validateToken(final String token) {
        return extractAllClaims(token);
    }


    private String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        var issuedDate = new Date();
        var expirationDate = new Date(issuedDate.getTime() + JWT_LIFE_TIME.toMillis());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claims(extraClaims)
                .issuedAt(issuedDate)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    private Claims extractAllClaims(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)     // ExpiredJwtException, SignatureException, JwtException
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SIGNING_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
