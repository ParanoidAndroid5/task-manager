package com.task_manager.task_manager.dto;

import lombok.Data;

@Data
public class TaskDTO {
    private Long id;
    private String title;
    private String description;
    private String priority;
    private String status;
    private String username;
}