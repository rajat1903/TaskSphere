package com.project.controller;

import com.project.model.Task;
import com.project.model.TaskStatus;
import com.project.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    @GetMapping("/tasks")
    public ResponseEntity<String> getAssignedUserTask(){

        return new ResponseEntity<>("Welcome to task service.", HttpStatus.OK);
    }
}
