package com.project.task_submission_service.controller;

import com.project.task_submission_service.model.Submission;
import com.project.task_submission_service.model.UserDto;
import com.project.task_submission_service.service.SubmissionService;
import com.project.task_submission_service.service.TaskService;
import com.project.task_submission_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submission")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<Submission> submitTask(

            @RequestParam Long task_id,
            @RequestParam String github_link,
            @RequestHeader ("Authorization") String jwt


    ) throws Exception{

        UserDto user = userService.getUserProfile(jwt);
        Submission submission = submissionService.submitTask(task_id, github_link, user.getId(), jwt);
        return new ResponseEntity<>(submission, HttpStatus.CREATED);

    }



    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(
            @PathVariable Long id,
            @RequestHeader ("Authorization") String jwt // Keep if authorization is needed, otherwise remove
    ) throws Exception{
        // UserDto user = userService.getUserProfile(jwt); // Remove if not used for authorization logic
        Submission submission = submissionService.getTaskSubmissionById(id);
        return new ResponseEntity<>(submission, HttpStatus.OK); // Changed to HttpStatus.OK
    }




    @GetMapping("/tasks")
    public ResponseEntity <List<Submission>> getAllTaskSubmissions(
            @RequestHeader ("Authorization") String jwt // Keep if authorization is needed, otherwise remove
    ) throws Exception{
        // UserDto user = userService.getUserProfile(jwt); // Remove if not used for authorization logic
        List<Submission> submission = submissionService.getAllTaskSubmissions();
        return new ResponseEntity<>(submission, HttpStatus.OK); // Changed to HttpStatus.OK
    }



    @GetMapping("/task/{taskId}")
    public ResponseEntity <List<Submission>> getAllSubmissions(

            @PathVariable Long taskId,
            @RequestHeader ("Authorization") String jwt


    ) throws Exception{

        UserDto user = userService.getUserProfile(jwt);
        List<Submission> submission = submissionService.getTaskSubmissionByTaskId(taskId);
        if (submission == null || submission.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(submission, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Submission> acceptOrDeclineSubmission(

            @PathVariable Long id,
            @RequestParam("status") String status,
            @RequestHeader ("Authorization") String jwt


    ) throws Exception{

        UserDto user = userService.getUserProfile(jwt);
        Submission submission = submissionService.acceptDeclineSubmission(id,status);
        return new ResponseEntity<>(submission, HttpStatus.OK);

    }

}
