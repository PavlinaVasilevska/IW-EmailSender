package com.example.EmailSender.service.impl;
import com.example.EmailSender.domain.Role;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.repository.UserRepository;
import com.example.EmailSender.domain.User;
import com.example.EmailSender.dto.UserDTO;
import com.example.EmailSender.mapper.UserMapper;
import com.example.EmailSender.service.RoleService;
import com.example.EmailSender.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalStateException(String.format("User with the email address '%s' already exists.", userDTO.getEmail()));
        }
        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new IllegalStateException(String.format("User with the username '%s' already exists.", userDTO.getUsername()));
        }

        // Validate roles
        Set<Role> roles = userDTO.getRoles().stream()
                .map(roleDTO -> roleService.getRoleByname(roleDTO.getName()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (roles.isEmpty()) {
            throw new ResourceNotFoundException("Role information is missing.");
        }

        User user = userMapper.toEntity(userDTO);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResourceNotFoundException("User not found: " + username);
        }
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found: " + email);
        }
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO getUserByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with uuid: " + uuid);
        }
        return userMapper.toDto(user);
    }

    @Override
    public User getByUuid(String uuid) {
        User user = userRepository.findByUuid(uuid);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with uuid: " + uuid);
        }
        return user;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(String uuid, UserDTO userDTO) {
        User existingUser = userRepository.findByUuid(uuid);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User not found with uuid: " + uuid);
        }

        if (userDTO.getUuid() != null && !userDTO.getUuid().equals(existingUser.getUuid())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'uuid' field cannot be updated.");
        }

        if (userDTO.getCreatedOn() != null && !userDTO.getCreatedOn().equals(existingUser.getCreatedOn())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'createdOn' field cannot be updated.");
        }
        userMapper.updateUserFromDto(userDTO, existingUser);

        Set<Role> roles = userDTO.getRoles().stream()
                .map(roleDTO -> roleService.getRoleByname(roleDTO.getName()))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (roles.isEmpty()) {
            throw new ResourceNotFoundException("Role information is missing.");
        }

        existingUser.setRoles(roles);

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public void deleteUser(String uuid) {
        User user = userRepository.findByUuid(uuid);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with uuid: " + uuid);
        }
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}


