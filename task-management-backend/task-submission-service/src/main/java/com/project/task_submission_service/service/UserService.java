package com.project.task_submission_service.service;

import com.project.task_submission_service.model.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "USER-SERVICE")  // service registered in Eureka / or configured in gateway
public interface UserService {

    // ✅ Get logged-in user profile
    @GetMapping("/api/user/profile")
    UserDto getUserProfile(@RequestHeader("Authorization") String jwt);

    // ✅ Get all users
    @GetMapping("/api/user")
    List<UserDto> getAllUsers(@RequestHeader("Authorization") String jwt);

    // ✅ Get single user by ID
    @GetMapping("/api/user/{id}")
    UserDto getUserById(@PathVariable("id") Long id,
                        @RequestHeader("Authorization") String jwt);
}
