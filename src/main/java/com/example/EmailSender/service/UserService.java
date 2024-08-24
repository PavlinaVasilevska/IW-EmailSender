package com.example.EmailSender.service;
import com.example.EmailSender.domain.User;
import com.example.EmailSender.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;


public interface UserService extends UserDetailsService {

    UserDTO createUser(UserDTO userDTO);

   UserDTO getUserByUsername(String username);

    UserDTO getUserByEmail(String email);

    UserDTO getUserByUuid(String uuid);

    User getByUuid(String uuid);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(String uuid, UserDTO userDTO);

    void deleteUser(String uuid);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
