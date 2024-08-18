package com.example.EmailSender.service.impl;
import com.example.EmailSender.domain.Role;
import com.example.EmailSender.dto.AuthRequest;
import com.example.EmailSender.dto.AuthResponse;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        String email = userDTO.getEmail();
        String username = userDTO.getUsername();

        // Check if a user with the given email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalStateException(String.format("User with the email address '%s' already exists.", email));
        }

        // Check if a user with the given username already exists
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalStateException(String.format("User with the username '%s' already exists.", username));
        }

        // Map the UserDTO to User entity
        User user = userMapper.userDTOToUser(userDTO);

        // Ensure name and surname are not null or empty
        if (userDTO.getName() == null || userDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (userDTO.getSurname() == null || userDTO.getSurname().isEmpty()) {
            throw new IllegalArgumentException("Surname cannot be null or empty.");
        }
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());

        // Handle the roles sent in the request
        Set<Role> roles = new HashSet<>();
        if (userDTO.getRoles() != null && !userDTO.getRoles().isEmpty()) {
            for (RoleDTO roleDTO : userDTO.getRoles()) {
                if (roleDTO.getName() == null || roleDTO.getName().isEmpty()) {
                    throw new IllegalArgumentException("Role name cannot be null or empty.");
                }
                String roleName = roleDTO.getName();

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

        // Set the roles on the user
        user.setRoles(roles);

        // Set the username
        user.setUsername(username);

        // Save the user and return the DTO
        User savedUser = userRepository.save(user);
        return userMapper.userToUserDTO(savedUser);
    }

    @Override
    public Optional<UserDTO> getUserByUsername(String username) {
        User user=userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("User not found: " + username));
        return Optional.of(userMapper.userToUserDTO(user));
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        User user=userRepository.findByUsername(email).orElseThrow(()-> new ResourceNotFoundException("User not found: " + email));
        return Optional.of(userMapper.userToUserDTO(user));
    }

    @Override
    public Optional<UserDTO> getUserByUuid(String uuid) {
        User user=userRepository.findByUsername(uuid).orElseThrow(()-> new ResourceNotFoundException("User not found: " + uuid));
        return Optional.of(userMapper.userToUserDTO(user));
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
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
//            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

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

