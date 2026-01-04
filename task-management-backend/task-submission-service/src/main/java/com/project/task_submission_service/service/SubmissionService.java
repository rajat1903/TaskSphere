package com.project.task_submission_service.service;

import com.project.task_submission_service.model.Submission;

import java.util.List;

public interface SubmissionService {
    Submission submitTask(Long taskId, String githubLink, Long userId, String jwt) throws Exception;

    Submission getTaskSubmissionById(Long submissionId) throws Exception;

    List<Submission> getAllTaskSubmissions();

    List<Submission> getTaskSubmissionByTaskId(Long TaskId);

    Submission acceptDeclineSubmission(Long id, String status) throws Exception;
}
