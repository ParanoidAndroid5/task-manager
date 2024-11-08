package com.task_manager.task_manager.service;

import com.task_manager.task_manager.dto.UserDTO;
import com.task_manager.task_manager.mapper.UserMapper;
import com.task_manager.task_manager.model.User;
import com.task_manager.task_manager.model.enums.UserType;
import com.task_manager.task_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserDTO registerUser(String username, String email, String password, UserType userType) {
        String encodedPassword = passwordEncoder.encode(password);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setEmail(email);
        userDTO.setUsertype(userType);

        User user = userMapper.toEntity(userDTO, encodedPassword);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public Optional<UserDTO> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return Optional.of(userMapper.toDto(user.get()));
        }
        return Optional.empty();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
