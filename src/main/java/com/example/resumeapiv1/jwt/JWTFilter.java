package com.example.resumeapiv1.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.resumeapiv1.security.PersonDetailsServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTConfig jwtConfig;
    private PersonDetailsServiceImpl personDetailsService;

    public JWTFilter(JWTConfig jwtConfig, PersonDetailsServiceImpl personDetailsService) {
        this.jwtConfig = jwtConfig;
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
        if (auth != null && !auth.isBlank() && auth.startsWith("Bearer")) {
            String token = auth.substring(7);
            if (!token.isBlank()) {
                try {
                    String claim = jwtConfig.getClaim(token);
                    UserDetails userDetails = personDetailsService.loadUserByUsername(claim);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                                    userDetails.getPassword(),
                                    new ArrayList<>());
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "exception token");
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "token incorrectly");

            }
        }
        filterChain.doFilter(request, response);
    }
}
