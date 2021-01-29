package com.izneus.bonfire.module.security;

import com.izneus.bonfire.common.util.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final RedisUtil redisUtil;

    private Key key;

    public JwtUtil(JwtProperties jwtProperties, RedisUtil redisUtil) {
        this.jwtProperties = jwtProperties;
        this.redisUtil = redisUtil;
        // todo 如果要给其他系统解析jwt，最好明确指定key
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userId) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + jwtProperties.getExpire() * 1000);
        return Jwts.builder()
                .setSubject(userId)
                .setId(UUID.randomUUID().toString())
                .setExpiration(expireDate)
                .setIssuedAt(nowDate)
                .signWith(key)
                .compact();
    }

    public String createToken(String userId, Long expireSeconds, Map<String, Object> claims) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expireSeconds * 1000);
        return Jwts.builder()
                .setSubject(userId)
                .setId(UUID.randomUUID().toString())
                .setExpiration(expireDate)
                .setIssuedAt(nowDate)
                .addClaims(claims)
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
        // redis获得权限字符串
        String userId = claims.getSubject();
        String authString = (String) redisUtil.get("user:" + userId + ":authorities");
        // 构造Authority
        Collection<? extends GrantedAuthority> authorities = StringUtils.hasText(authString)
                ? Arrays.stream(authString.split(",")).map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()) : Collections.emptyList();
        SecurityUser principal = new SecurityUser(claims.getSubject(), "*", "*", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
