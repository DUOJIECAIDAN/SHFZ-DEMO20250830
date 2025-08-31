package com.shanhaifangzhou.base.security.jwt;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.secret:shanhaifangzhou-secret-key}")
    private String secret;

    @Value("${jwt.expiration:86400}")
    private Long expiration;

    @Value("${jwt.header:Authorization}")
    private String header;

    @Value("${jwt.prefix:Bearer }")
    private String prefix;

    /**
     * 生成JWT令牌
     */
    public String generateToken(String username) {
        return generateToken(username, new HashMap<>());
    }

    /**
     * 生成JWT令牌（带额外信息）
     */
    public String generateToken(String username, Map<String, Object> claims) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), JwtSignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 从JWT令牌中获取用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    /**
     * 从JWT令牌中获取过期时间
     */
    public Date getExpirationDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims != null ? claims.getExpiration() : null;
    }

    /**
     * 从JWT令牌中获取声明信息
     */
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT令牌解析失败: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 验证JWT令牌是否有效
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("JWT令牌验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 检查JWT令牌是否过期
     */
    public boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration != null && expiration.before(new Date());
    }

    /**
     * 从请求头中提取JWT令牌
     */
    public String extractTokenFromHeader(String authHeader) {
        if (StrUtil.isBlank(authHeader) || !authHeader.startsWith(prefix)) {
            return null;
        }
        return authHeader.substring(prefix.length());
    }

    /**
     * 获取签名密钥
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * 刷新JWT令牌
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        
        // 移除过期时间，重新生成
        claims.setExpiration(null);
        return generateToken(claims.getSubject(), claims);
    }
}