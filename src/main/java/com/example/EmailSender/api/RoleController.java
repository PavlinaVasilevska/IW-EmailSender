package com.example.EmailSender.api;
import com.example.EmailSender.dto.RoleDTO;
import com.example.EmailSender.infrastructure.EndPoints;
import com.example.EmailSender.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(EndPoints.ROLES)
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RoleDTO> createRole(@RequestBody @Valid RoleDTO roleDTO) {
        RoleDTO createdRole = roleService.createRole(roleDTO);
        return ResponseEntity.ok(createdRole);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Optional<RoleDTO>> getRoleByUuid(@PathVariable String uuid) {
        Optional<RoleDTO> role = roleService.getRoleByUuid(uuid);
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable String uuid, @RequestBody @Valid RoleDTO roleDTO) {
        RoleDTO updatedRole = roleService.updateRole(uuid, roleDTO);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteRole(@PathVariable String uuid) {
        roleService.deleteRole(uuid);
        return ResponseEntity.noContent().build();
    }
}
