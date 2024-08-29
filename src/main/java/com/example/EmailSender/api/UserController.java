package com.example.EmailSender.api;
import com.example.EmailSender.dto.UserDTO;
import com.example.EmailSender.infrastructure.EndPoints;
import com.example.EmailSender.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(EndPoints.USERS)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.status(201).body(createdUser);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        UserDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<UserDTO> getUserByUuid(@PathVariable String uuid) {
        UserDTO user = userService.getUserByUuid(uuid);
        return ResponseEntity.ok(user);

    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String uuid,@Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(uuid, userDTO);
        return ResponseEntity.ok(updatedUser);
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteUser(@PathVariable String uuid) {
        userService.deleteUser(uuid);
        return ResponseEntity.noContent().build();
    }

}
