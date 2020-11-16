package com.xr.treehole.middleware.jwt;

import com.xr.treehole.config.selfdef.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * some
 */
@Component
@EnableConfigurationProperties(JwtConfig.class)
public class JwtUtils {

    JwtConfig jwtConfig;

    Key secretKey;

    Date exp;

    @Autowired
    public JwtUtils(JwtConfig jwtConfig){
        secretKey = new SecretKeySpec(jwtConfig.getSecret().getBytes(), SignatureAlgorithm.HS256.getJcaName());
        exp = new Date(System.currentTimeMillis() + jwtConfig.getExpTime());
    }

    public String GenerateToken(Map<String, Object> claims) {



        return Jwts.builder()
                .setClaims(claims)  // set claims
                .signWith(secretKey)
                .setExpiration(exp)
                .compact();
    }

    public Claims GetClaimsFromToken(String token) {

        Key secretKey = new SecretKeySpec(jwtConfig.getSecret().getBytes(), SignatureAlgorithm.HS256.getJcaName());
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }

        return claims;
    }

    public boolean Validate(String token){

        try {
            return GetClaimsFromToken(token).getExpiration().after(new Date());
        }catch (Exception e){
            return false;
        }

    }


    public String getUsername(String token){
        Claims claims = GetClaimsFromToken(token);
        return (String) claims.get("username");
    }
}
