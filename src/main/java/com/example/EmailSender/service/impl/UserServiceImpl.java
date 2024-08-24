package com.example.EmailSender.service.impl;
import com.example.EmailSender.domain.Role;
import com.example.EmailSender.dto.RoleDTO;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.mapper.RoleMapper;
import com.example.EmailSender.repository.RoleRepository;
import com.example.EmailSender.repository.UserRepository;
import com.example.EmailSender.domain.User;
import com.example.EmailSender.dto.UserDTO;
import com.example.EmailSender.mapper.UserMapper;
import com.example.EmailSender.service.RoleService;
import com.example.EmailSender.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        String email = userDTO.getEmail();
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();


        User existingUserByEmail = userRepository.findByEmail(email);
        if (existingUserByEmail != null) {
            throw new IllegalStateException(String.format("User with the email address '%s' already exists.", email));
        }

        User existingUserByUsername = userRepository.findByUsername(username);
        if (existingUserByUsername != null) {
            throw new IllegalStateException(String.format("User with the username '%s' already exists.", username));
        }

        if (userDTO.getName() == null || userDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (userDTO.getSurname() == null || userDTO.getSurname().isEmpty()) {
            throw new IllegalArgumentException("Surname cannot be null or empty.");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Email format is invalid.");
        }

        Set<Role> roles = new HashSet<>();
        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            for (RoleDTO roleDTO : userDTO.getRoles()) {
                if (roleDTO.getName() == null || roleDTO.getName().isEmpty()) {
                    throw new IllegalArgumentException("Role name cannot be null or empty.");
                }

                String roleName = roleDTO.getName();
                Role foundRole = roleService.getRoleByname(roleName);

                if (foundRole == null) {
                    throw new ResourceNotFoundException("Role not found with name: " + roleName);
                }

                roles.add(foundRole);
            }
        } else {
            throw new ResourceNotFoundException("Role information is missing.");
        }

        User user = userMapper.toEntity(userDTO);
        user.setUsername(username);
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(email);
        user.setRoles(roles);
        user.setPassword(password);

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
        if (uuid == null || uuid.isEmpty()) {
            throw new IllegalArgumentException("UUID cannot be null or empty.");
        }

        User existingUser = userRepository.findByUuid(uuid);
        if (existingUser == null) {
            throw new ResourceNotFoundException("User not found with uuid: " + uuid);
        }

        String email = userDTO.getEmail();
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Email format is invalid.");
        }

        Set<Role> roles = new HashSet<>();
        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            for (RoleDTO roleDTO : userDTO.getRoles()) {
                String roleName = roleDTO.getName();

                if (roleName == null || roleName.isEmpty()) {
                    continue;
                }

                Role foundRole = roleService.getRoleByname(roleName);
                if (foundRole == null) {
                    throw new ResourceNotFoundException("Role not found with name: " + roleName);
                }
                roles.add(foundRole);
            }
        } else {
            throw new IllegalArgumentException("At least one role must be provided.");
        }

        if (userDTO.getName() != null) {
            existingUser.setName(userDTO.getName());
        }

        if (userDTO.getSurname() != null) {
            existingUser.setSurname(userDTO.getSurname());
        }

        existingUser.setEmail(email);
        existingUser.setRoles(roles);
        existingUser.setPassword(userDTO.getPassword());

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


