package com.xr.treehole.middleware.jwt;

import com.xr.treehole.service.SecurityUserDetailService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * classname: jwtauthorizationtokenfilter
 * function : implement the authentication and authorization
 * <p>
 * decide which resources should be used by which purview is decided in config.security.SecurityConfig
 */
@Component
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    SecurityUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//
//        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
//        if (header == null || header.isEmpty() || !header.startsWith("Bearer ")){
//            filterChain.doFilter(httpServletRequest, httpServletResponse);
//            return;
//        }
//
//        String token = header.split(" ")[1].trim();

        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies == null){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        Cookie jwtCookie = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(jwtUtils.CookieName)) {
                jwtCookie = cookie;
                break;
            }
        }

        if (jwtCookie == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String token = jwtCookie.getValue();


        // refresh JWT token
        if (jwtUtils.shouldReissue(token)) {
            Claims claims = jwtUtils.GetClaimsFromToken(token);
            String newToken = jwtUtils.GenerateToken(claims);
            jwtCookie.setValue(newToken);
        }

        if (!jwtUtils.Validate(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // For Authentication and get the user Authorization
        String username = jwtUtils.getUsername(token);
        UserDetails userDetails = userDetailService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
