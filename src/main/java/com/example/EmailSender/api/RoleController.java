package com.example.EmailSender.api;
import com.example.EmailSender.dto.RoleDTO;
import com.example.EmailSender.infrastructure.exception.ResourceNotFoundException;
import com.example.EmailSender.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {


    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@RequestBody @Valid RoleDTO roleDTO) {
        RoleDTO createdRole = roleService.createRole(roleDTO);
        return ResponseEntity.ok(createdRole);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RoleDTO> getRoleByUuid(@PathVariable String uuid) {
        return roleService.getRoleByUuid(uuid)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with uuid: " + uuid));
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable String uuid, @RequestBody RoleDTO roleDTO) {
        RoleDTO updatedRole = roleService.updateRole(uuid, roleDTO);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteRole(@PathVariable String uuid) {
        roleService.deleteRole(uuid);
        return ResponseEntity.noContent().build();
    }
}