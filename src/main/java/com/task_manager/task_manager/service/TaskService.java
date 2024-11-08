package com.task_manager.task_manager.service;

import com.task_manager.task_manager.dto.TaskDTO;
import com.task_manager.task_manager.mapper.TaskMapper;
import com.task_manager.task_manager.model.Task;
import com.task_manager.task_manager.model.User;
import com.task_manager.task_manager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final Logger logger = LoggerFactory.getLogger(TaskService.class);

    @Transactional
    public List<TaskDTO> getTasksByUser(User user) {
        try {
            List<Task> tasks = taskRepository.findByUser(user);
            return tasks.stream()
                    .map(taskMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error fetching tasks for user: {}", user.getUsername(), e);
            throw e;
        }
    }

    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO, User user) {
        try {
            Task task = taskMapper.toEntity(taskDTO, user);
            Task savedTask = taskRepository.save(task);
            return taskMapper.toDto(savedTask);
        } catch (Exception e) {
            logger.error("Error creating task for user: {}", user.getUsername(), e);
            throw e;
        }
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO taskDTO, User user) {
        try {
            Task existingTask = taskRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

            if (!existingTask.getUser().getId().equals(user.getId())) {
                logger.warn("User {} attempted to update task {} not owned by them.", user.getUsername(), id);
                throw new RuntimeException("You do not have permission to update this task.");
            }

            taskMapper.updateEntity(existingTask, taskDTO);
            Task updatedTask = taskRepository.save(existingTask);
            return taskMapper.toDto(updatedTask);
        } catch (Exception e) {
            logger.error("Error updating task with id: {}", id, e);
            throw e;
        }
    }

    @Transactional
    public void deleteTask(Long id, User user) {
        try {
            Task existingTask = taskRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

            if (!existingTask.getUser().getId().equals(user.getId())) {
                logger.warn("User {} attempted to delete task {} not owned by them.", user.getUsername(), id);
                throw new RuntimeException("You do not have permission to delete this task.");
            }

            taskRepository.deleteById(id);
            logger.info("Task with id: {} deleted by user: {}", id, user.getUsername());
        } catch (Exception e) {
            logger.error("Error deleting task with id: {}", id, e);
            throw e;
        }
    }
}
