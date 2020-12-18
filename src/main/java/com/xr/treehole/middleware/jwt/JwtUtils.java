package com.xr.treehole.middleware.jwt;

import com.xr.treehole.config.selfdef.JwtConfig;
import com.xr.treehole.util.Encrypt;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * some
 */
@Component
@EnableConfigurationProperties(JwtConfig.class)
public class JwtUtils {

    public final String CookieName = "JwtToken";

    JwtConfig jwtConfig;

    Key secretKey;

    Date exp;

    Date reissueTime;

    @Autowired
    public JwtUtils(JwtConfig jwtConfig){
        secretKey = new SecretKeySpec(jwtConfig.getSecret().getBytes(), SignatureAlgorithm.HS256.getJcaName());
        exp = new Date(System.currentTimeMillis() + jwtConfig.getExpTime());
        reissueTime = new Date(System.currentTimeMillis() + jwtConfig.getExpTime() - jwtConfig.getReissueDuration());
    }

    public String GenerateToken(Map<String, Object> claims) {

        return Jwts.builder()
                .setClaims(claims)  // set claims
                .signWith(secretKey)
                .setExpiration(exp)
                .compact();
    }

    public String GenerateToken(Claims claims){
        return Jwts.builder()
                .setClaims(claims)
                .signWith(secretKey)
                .setExpiration(exp)
                .compact();
    }

    public Claims GetClaimsFromToken(String token) {

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

    public boolean shouldReissue(String token){
        try {
            return GetClaimsFromToken(token).getExpiration().after(reissueTime)
                    && GetClaimsFromToken(token).getExpiration().before(exp);
        }catch (Exception e){
            return false;
        }
    }

    public String getUsername(String token){
        Claims claims = GetClaimsFromToken(token);
        return (String) claims.get("username");
    }


    public void IssueJwtTokenInCookie(HttpServletResponse response, String emailAddress){
        Map<String, Object> claims = new HashMap<>();
        String hashedEmail = Encrypt.Hashing(emailAddress);
        claims.put("username", hashedEmail);
        String token = GenerateToken(claims);

        Cookie cookie = new Cookie(CookieName, token);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
