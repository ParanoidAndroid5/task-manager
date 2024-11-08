package com.task_manager.task_manager.dto;

import com.task_manager.task_manager.model.enums.UserType;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String email;
    private UserType usertype;
}
