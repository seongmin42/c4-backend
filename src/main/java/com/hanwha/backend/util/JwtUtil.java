package com.hanwha.backend.util;

import com.hanwha.backend.data.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {
    private final static long ACCESS_TOKEN_VALID_TIME = 5 * 60 * 60 * 1000L;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";
    private static final String AUTHORITIES_KEY = "role";
    private static final String AUTHORITIES_PREFIX = "ROLE_";

    @Value("${jwt.secret}")
    private String secret;
    private Key secretKey;

    @PostConstruct
    private void init(){
        String encodedSecret = Base64.getEncoder().encodeToString(secret.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(encodedSecret.getBytes());
    }

    public String generateAccessToken(Member member){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(member.getId().toString())
                .claim(AUTHORITIES_KEY, "USER")
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                .compact();
    }

    public Claims getTokenClaims(String token) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e){
            log.warn(e.getMessage());
        }
        return null;
    }

    public boolean verifyToken(String token) {
        return getTokenClaims(token) != null;
    }

    public String getAccessToken(HttpServletRequest request) {
        try{
            String authHeaderValue = request.getHeader(HEADER_AUTHORIZATION);
            return authHeaderValue.substring(TOKEN_PREFIX.length());
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        return null;
    }

    public Authentication getAuthentication(String token){
        Claims claims = getTokenClaims(token);
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(new String[]{AUTHORITIES_PREFIX + claims.get(AUTHORITIES_KEY).toString()})
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
    }

}
