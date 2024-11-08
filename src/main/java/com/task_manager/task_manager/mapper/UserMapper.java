package com.task_manager.task_manager.mapper;

import com.task_manager.task_manager.dto.UserDTO;
import com.task_manager.task_manager.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setUsertype(user.getUsertype());
        return dto;
    }

    public User toEntity(UserDTO dto, String password) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(password);
        user.setUsertype(dto.getUsertype());
        return user;
    }
}
