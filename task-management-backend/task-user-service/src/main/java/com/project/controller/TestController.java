package com.project.controller;

import com.project.response.AuthResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @GetMapping("/health")
    public ResponseEntity<AuthResponse> healthCheck() {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setMessage("Service is running successfully");
        authResponse.setStatus(true);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    
    @GetMapping("/status")
    public ResponseEntity<String> statusCheck() {
        return new ResponseEntity<>("Task User Service is up and running", HttpStatus.OK);
    }
}