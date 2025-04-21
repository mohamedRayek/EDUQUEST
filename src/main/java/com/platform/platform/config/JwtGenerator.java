//package com.platform.platform.config;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import jakarta.annotation.PostConstruct;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Base64;
//import java.util.Date;
//
//import org.springframework.stereotype.Component;
//
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//
//@Component
//@Slf4j
//public class JwtGenerator {
//    @Value("${app.jwt-secret}")
//    private String jwtSecret;
//
//    @Value("${app.jwt-expiration-ms}")
//    private long jwtExpirationMs;
//
//    public void init() {
//        try {
//            // التحقق من أن المفتاح تم تحميله بشكل صحيح
//            if (jwtSecret == null || jwtSecret.isEmpty()) {
//                throw new IllegalStateException("JWT secret is not configured");
//            }
//            getSigningKey(); // اختبار إنشاء المفتاح
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to initialize JwtGenerator", e);
//        }
//    }
//
//    public String generateToken(Authentication authentication) {
//        log.info("Generating token for: {}", authentication.getName());
//        String username = authentication.getName();
//        Date now = new Date();
//        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
//
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(now)
//                .setExpiration(expiryDate)
//                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
//                .compact();
//    }
//
//    private Key getSigningKey() {
//        byte[] keyBytes = java.util.Base64.getDecoder().decode(jwtSecret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public String getUsernameFromToken(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder()
//                    .setSigningKey(getSigningKey())
//                    .build()
//                    .parseClaimsJws(token);
//            return true;
//        } catch (Exception ex) {
//            return false;
//        }
//    }
//
//
//}
