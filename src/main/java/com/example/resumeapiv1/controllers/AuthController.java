package com.example.resumeapiv1.controllers;

import com.example.resumeapiv1.dto.AuthenticationDTO;
import com.example.resumeapiv1.jwt.JWTConfig;
import com.example.resumeapiv1.utill.PersonErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JWTConfig jwtConfig;

    public AuthController(AuthenticationManager authenticationManager, JWTConfig jwtConfig) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> performLogin(@RequestBody AuthenticationDTO authenticationDTO,
                                             HttpServletResponse res) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        jwtConfig.createToken(authenticationDTO.getUsername(), res);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<PersonErrorResponse> badCredential() {
        PersonErrorResponse response = new PersonErrorResponse("bad credentials");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
