package com.project.service;

import com.project.model.Task;
import com.project.model.TaskStatus;
import com.project.repository.TaskRepository;
import org.hibernate.query.sqm.mutation.internal.TableKeyExpressionCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImplementation implements TaskService{

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Task createTask(Task task, String requesterRole) {
        if (!"ADMIN".equals(requesterRole)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only Admin Can create a task."
            );
        }
        task.setStatus(TaskStatus.PENDING);
        task.setCreatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) throws Exception {
        return taskRepository.findById(id).orElseThrow(()->new Exception("Task not found with id" + id));
    }

    @Override
    public List<Task> getAllTask(TaskStatus status) {
        List<Task> allTask = taskRepository.findAll();
        List<Task> filteredTasks = allTask.stream().filter(
                task -> status == null || task.getStatus().name().equals(status.toString())

        ).collect(Collectors.toList());

        return filteredTasks;
    }

    @Override
    public Task updateTask(Long id, Task updateTask, Long userId) throws Exception {
        Task existingTask = getTaskById(id);
        if(updateTask.getTitle() != null){
            existingTask.setTitle(updateTask.getTitle());
        }

        if(updateTask.getImage() != null){
            existingTask.setImage(updateTask.getImage());
        }
        if(updateTask.getDescription() != null){
            existingTask.setDescription(updateTask.getDescription());
        }
        if(updateTask.getStatus() != null){
            existingTask.setStatus(updateTask.getStatus());
        }
        if(updateTask.getDeadline() != null){
            existingTask.setDeadline(updateTask.getDeadline());
        }
        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) throws Exception {
        getTaskById(id);
        taskRepository.deleteById(id);
    }

    @Override
    public Task assignedToUser(Long userId, Long taskId) throws Exception {
        Task task = getTaskById(taskId);
        task.setAssignedUserId(userId);
        task.setStatus(TaskStatus.ASSIGNED); // Changed to ASSIGNED
        return taskRepository.save(task);
    }

    @Override
    public List<Task> assignedUsersTask(Long userId, TaskStatus status) {
        List<Task> allTask = taskRepository.findByAssignedUserId(userId);
        List<Task> filteredTasks = allTask.stream().filter(
                task -> status == null || task.getStatus().name().equals(status.toString())

        ).collect(Collectors.toList());

        return filteredTasks;
    }

    @Override
    public Task completeTask(Long taskId) throws Exception {

        Task task = getTaskById(taskId);
        task.setStatus(TaskStatus.DONE);
        return taskRepository.save(task);
    }
}
