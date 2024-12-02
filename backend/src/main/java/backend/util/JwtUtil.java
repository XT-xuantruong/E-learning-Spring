package backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import backend.entity.User;

import java.util.Date;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class JwtUtil {
    private final String secret = generateSecret();
    private final long accessTokenExpiration = 15 * 60 * 1000; 
    private final long refreshTokenExpiration = 7 * 24 * 60 * 60 * 1000;
    
    // Using ConcurrentHashMap for thread safety
    private final Map<String, Date> blacklistedTokens = new ConcurrentHashMap<>();
    
    private String generateSecret() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretBytes = new byte[64];
        secureRandom.nextBytes(secretBytes);
        return Base64.getEncoder().encodeToString(secretBytes);
    }
    
    public String generateAccessToken(User user) {
        return Jwts.builder()
            .setSubject(user.getEmail())
            .setId(user.getId().toString())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }
    
    public String generateRefreshToken(User user) {
        return Jwts.builder()
            .setSubject(user.getEmail())
            .setId(user.getId().toString())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public void blacklistToken(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
            blacklistedTokens.put(token, claims.getExpiration());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token");
        }
    }

}