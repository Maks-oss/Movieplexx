package com.example.backend.controller;

import com.example.backend.data.request.LoginRequest;
import com.example.backend.data.response.LoginResponse;
import com.example.backend.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final Logger logger = Logger.getLogger("Authentication");
    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        logger.info("User credentials: " + loginRequest);
        Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);
        String token = JwtUtil.generateToken(authenticationResponse.getName());
        Long expirationTime = JwtUtil.getExpirationTime();
        return ResponseEntity.ok(new LoginResponse(token, expirationTime));
    }
}
