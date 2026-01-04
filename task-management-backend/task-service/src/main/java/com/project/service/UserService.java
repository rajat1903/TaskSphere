package com.project.service;

import com.project.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:5001/")
public interface UserService {
    @GetMapping("/api/user/profile") // Corrected path to match UserController
    public UserDto getUserProfile(@RequestHeader("Authorization") String jwt);
}
        