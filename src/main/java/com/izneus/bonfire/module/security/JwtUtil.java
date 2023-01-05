package com.izneus.bonfire.module.security;

import cn.hutool.core.util.IdUtil;
import com.izneus.bonfire.common.util.RedisUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Izneus
 * @date 2020-06-20
 */
@Component
@Slf4j
public class JwtUtil {

    private final JwtConfig jwtConfig;
    private final RedisUtil redisUtil;

    private Key key;

    public JwtUtil(JwtConfig jwtConfig, RedisUtil redisUtil) {
        this.jwtConfig = jwtConfig;
        this.redisUtil = redisUtil;
        /// 可以通过下面的代码随机生成secret字符串
        // SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        // String secretString = Encoders.BASE64.encode(key.getEncoded());
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret()));
    }

    /**
     * 默认的创建jwt方法，一般是给login过程生成token
     *
     * @param userId 用户id
     * @return jwt
     */
    public String createToken(String userId) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + jwtConfig.getExpire() * 1000);
        return Jwts.builder()
                .setSubject(userId)
                .setId(IdUtil.fastSimpleUUID())
                .setExpiration(expireDate)
                .setIssuedAt(nowDate)
                .signWith(key)
                .compact();
    }

    /**
     * 生成自定义claim的jwt，一般是生成临时token
     *
     * @param userId        用户id
     * @param expireSeconds 过期时长，单位秒
     * @param claims        自定义claim
     * @return jwt
     */
    public String createToken(String userId, Long expireSeconds, Map<String, Object> claims) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + expireSeconds * 1000);
        return Jwts.builder()
                .setSubject(userId)
                .setId(IdUtil.fastSimpleUUID())
                .setExpiration(expireDate)
                .setIssuedAt(nowDate)
                .addClaims(claims)
                .signWith(key)
                .compact();
    }

    Authentication getAuthentication(String token) {
        // 获得claims
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        // redis获得权限字符串
        String userId = claims.getSubject();
        String key = redisUtil.getPrivilegeKey(userId);
        String authString = (String) redisUtil.get(key);
        // 没有该key，即白名单
        /*if (null == authString) {
            return null;
        }*/
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
