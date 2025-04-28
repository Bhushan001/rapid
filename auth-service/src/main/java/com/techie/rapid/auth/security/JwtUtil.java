package com.techie.rapid.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for JWT token operations.
 */
@Component
public class JwtUtil {

    // Secret key for signing JWT tokens
    @Value("${jwt.secret}")
    private String secret;

    // JWT expiration time in milliseconds
    @Value("${jwt.expiration}")
    private long jwtExpirationInMs; // in milliseconds

    /*
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /*
     * Extracts the expiration date from the JWT token.
     *
     * @param token the JWT token
     * @return the expiration date extracted from the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /*
     * Extracts a specific claim from the JWT token.
     *
     * @param token the JWT token
     * @param claimsResolver a function to extract the claim
     * @param <T> the type of the claim
     * @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /*
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token
     * @return the claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        try {
            return Jwts.parser().setSigningKey(key) // Correct usage
                    .setAllowedClockSkewSeconds(30).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Invalid JWT token: " + e.getMessage());
        }
    }

    /*
     * Generates a JWT token with the specified username and claims.
     *
     * @param username the username to include in the token
     * @param claims additional claims to include in the token
     * @return the generated JWT token
     */
    public String generateToken(String username, Map<String, Object> claims) {
        return generateToken(username, new Date(System.currentTimeMillis() + jwtExpirationInMs), claims);
    }

    /*
     * Generates a JWT token with the specified username, expiration date, and claims.
     *
     * @param username the username to include in the token
     * @param expiration the expiration date of the token
     * @param claims additional claims to include in the token
     * @return the generated JWT token
     */
    public String generateToken(String username, Date expiration, Map<String, Object> claims) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(expiration).signWith(key, SignatureAlgorithm.HS256).compact();
        return token;
    }

    /*
     * Checks if the JWT token is valid.
     *
     * @param token the JWT token
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /*
     * Validates the JWT token by checking if it is not expired and matches the username.
     *
     * @param token the JWT token
     * @param username the username to validate against
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }


    public String getUsernameFromToken(String token) {
        return extractUsername(token);
    }
}
