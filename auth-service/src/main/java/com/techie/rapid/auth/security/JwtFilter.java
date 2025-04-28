package com.techie.rapid.auth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JwtFilter is a custom filter that processes JWT tokens in incoming HTTP requests.
 * It extracts the token from the Authorization header, validates it, and sets the authentication context.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    /**
     * The secret key used for signing the JWT tokens.
     */
    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * The JWT utility class used for token operations.
     */
    private final JwtUtil jwtUtil;
    /**
     * The custom user details service used for loading user-specific data.
     */
    private final CustomUserDetailsService userDetailsService;

    /**
     * This method is called for each request to filter the JWT token.
     * It extracts the token from the Authorization header, validates it, and sets the authentication context.
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @param filterChain the filter chain
     * @throws ServletException if an error occurs during filtering
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser().setSigningKey(key).setAllowedClockSkewSeconds(30).build().parseClaimsJws(token).getBody();
                String username = claims.getSubject();
                List<String> roles = (List<String>) claims.get("roles"); // Correct location
                List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, claims, authorities); // Set claims as credentials
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
                e.printStackTrace();
                System.err.println("JWT parsing failed: " + e.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
