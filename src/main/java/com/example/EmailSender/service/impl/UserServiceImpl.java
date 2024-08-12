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
import com.example.EmailSender.service.UserService;
import lombok.RequiredArgsConstructor;
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
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        user.setUuid(UUID.randomUUID().toString());

        // Handle the roles sent in the request
        Set<Role> roles = new HashSet<>();
        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            for (RoleDTO roleDTO : userDTO.getRoles()) {
                String roleName = roleDTO.getName(); // Adjust to match the JSON field name

                // Find the role by its name
                Optional<Role> roleOptional = roleRepository.findByName(roleName);
                if (roleOptional.isEmpty()) {
                    throw new ResourceNotFoundException("Role not found with name: " + roleName);
                }

                roles.add(roleOptional.get());
            }
        } else {
            throw new ResourceNotFoundException("Role information is missing.");
        }

        user.setRoles(roles); // Set the roles on the user

        // Save the user and return the DTO
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDTO(savedUser);
    }


    
    @Override
    public Optional<UserDTO> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::userToUserDTO);
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::userToUserDTO);
    }

    @Override
    public Optional<UserDTO> getUserByUuid(String uuid) {
        return userRepository.findByUuid(uuid)
                .map(userMapper::userToUserDTO);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDTO)
                .toList();
    }

    @Override
    public UserDTO updateUser(String uuid, UserDTO userDTO) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with uuid: " + uuid));

        // Update fields
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        // Handle roles
        if (userDTO.getRoles() != null) {
            Set<Role> roles = userDTO.getRoles().stream()
                    .filter(roleDTO -> roleDTO.getName() != null)  // Filter out roles with null names
                    .map(roleDTO -> roleRepository.findByName(roleDTO.getName())
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleDTO.getName())))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        User updatedUser = userRepository.save(user);
        return userMapper.userToUserDTO(updatedUser);
    }



    @Override
    public void deleteUser(String uuid) {
        User user = userRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with uuid: " + uuid));
        userRepository.delete(user);
    }
}
