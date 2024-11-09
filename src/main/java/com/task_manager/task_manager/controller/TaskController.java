package com.task_manager.task_manager.controller;

import com.task_manager.task_manager.dto.TaskDTO;
import com.task_manager.task_manager.model.User;
import com.task_manager.task_manager.service.TaskService;
import com.task_manager.task_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getUserTasks(@RequestParam String username) {
        try {
            User user = userService.findByUsername(username);
            List<TaskDTO> tasks = taskService.getTasksByUser(user);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            logger.error("Error retrieving tasks for user: {}", username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO, @RequestParam String username) {
        try {
            User user = userService.findByUsername(username);
            TaskDTO createdTask = taskService.createTask(taskDTO, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
        } catch (Exception e) {
            logger.error("Error creating task for user: {}", username, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO, @RequestParam String username) {
        try {
            User user = userService.findByUsername(username);
            TaskDTO updatedTask = taskService.updateTask(id, taskDTO, user);
            return ResponseEntity.ok(updatedTask);
        } catch (RuntimeException e) {
            logger.warn("Unauthorized task update attempt by user: {}", username, e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (Exception e) {
            logger.error("Error updating task with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id, @RequestParam String username) {
        try {
            User user = userService.findByUsername(username);
            taskService.deleteTask(id, user);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.warn("Unauthorized task delete attempt by user: {}", username, e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            logger.error("Error deleting task with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
