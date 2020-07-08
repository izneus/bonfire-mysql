package com.izneus.bonfire.module.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Izneus
 * @date 2020/06/20
 */
@Component
public class JwtUtils {

    private final JwtProperties jwtProperties;
    private Key key;

    public JwtUtils(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Authentication authentication) {
        // 设置权限字符串
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + jwtProperties.getExpire() * 1000);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setId(UUID.randomUUID().toString())
                .setExpiration(expireDate)
                .setIssuedAt(nowDate)
                .claim("auth", authorities)
                .signWith(key)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        // 获得claims
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        // claim里获得权限字符串
        String authString = claims.get("auth", String.class);
        // 构造Authority
        Collection<? extends GrantedAuthority> authorities = StringUtils.hasText(authString) ?
                Arrays.stream(authString.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
                : Collections.emptyList();
        User principal = new User(claims.getSubject(), "*", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
