package com.example.EmailSender.service;
import com.example.EmailSender.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;


public interface UserService extends UserDetailsService {

    UserDTO createUser(UserDTO userDTO);

    Optional<UserDTO> getUserByUsername(String username);

    Optional<UserDTO> getUserByEmail(String email);

    Optional<UserDTO> getUserByUuid(String uuid);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(String uuid, UserDTO userDTO);

    void deleteUser(String uuid);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
