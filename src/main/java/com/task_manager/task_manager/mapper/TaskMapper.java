package com.task_manager.task_manager.mapper;

import com.task_manager.task_manager.dto.TaskDTO;
import com.task_manager.task_manager.model.Task;
import com.task_manager.task_manager.model.User;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDTO toDto(Task task) {
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority().name());
        dto.setStatus(task.getStatus().name());
        if (task.getUser() != null) {
            dto.setUsername(task.getUser().getUsername());
        }
        return dto;
    }

    public Task toEntity(TaskDTO dto, User user) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        try {
            task.setPriority(Task.Priority.valueOf(dto.getPriority().toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            task.setPriority(Task.Priority.MEDIUM);
        }

        try {
            task.setStatus(Task.Status.valueOf(dto.getStatus().toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            task.setStatus(Task.Status.TODO);
        }

        task.setUser(user);
        return task;
    }

    public void updateEntity(Task task, TaskDTO dto) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());

        try {
            task.setPriority(Task.Priority.valueOf(dto.getPriority().toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            task.setPriority(Task.Priority.MEDIUM);
        }

        try {
            task.setStatus(Task.Status.valueOf(dto.getStatus().toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException e) {
            task.setStatus(Task.Status.TODO);
        }
    }
}
