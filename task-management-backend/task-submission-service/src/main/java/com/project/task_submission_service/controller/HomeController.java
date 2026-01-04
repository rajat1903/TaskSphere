package com.project.task_submission_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

@RestController
public class HomeController {

    @GetMapping("/submission")
    public ResponseEntity<String> homeController(){
       return new ResponseEntity<>("Welcome to the submission service.", HttpStatus.OK);
    }
}
