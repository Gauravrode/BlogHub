package com.acts.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/api/users/register",
            "/auth/login",
            "/api/posts"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, java.io.IOException {

        String requestURI = request.getRequestURI();

        if (PUBLIC_ENDPOINTS.stream().anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(request, response);
            return;         }

        String requestHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            token = requestHeader.substring(7);
            try {
                username = this.jwtHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.info(" Illegal Argument while fetching the username");
            } catch (ExpiredJwtException e) {
                logger.info(" JWT token is expired!");
            } catch (MalformedJwtException e) {
                logger.info(" Invalid JWT Token!");
            } catch (Exception e) {
                logger.info(" Unknown JWT Error!");
            }
        } else {
            logger.info(" No Authorization Header Found for: " + requestURI);
        }

    
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            if (this.jwtHelper.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.info(" Token Validation Failed");
            }
        }

        filterChain.doFilter(request, response);
    }
}
