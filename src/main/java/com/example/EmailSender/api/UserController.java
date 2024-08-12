package com.example.EmailSender.api;

import com.example.EmailSender.dto.UserDTO;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<UserDTO> getUserByUuid(@PathVariable String uuid) {
        return userService.getUserByUuid(uuid)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with UUID: " + uuid));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String uuid, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(uuid, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable String uuid) {
        userService.deleteUser(uuid);
        return ResponseEntity.noContent().build();
    }
}
