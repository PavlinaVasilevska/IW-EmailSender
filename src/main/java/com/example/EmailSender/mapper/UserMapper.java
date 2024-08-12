package com.example.EmailSender.mapper;
import com.example.EmailSender.domain.Role;
import com.example.EmailSender.domain.User;
import com.example.EmailSender.dto.RoleDTO;
import com.example.EmailSender.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring",  uses = RoleMapper.class)
public interface UserMapper {

    UserDTO userToUserDTO(User user);
    User userDTOToUser(UserDTO userDTO);
    Set<RoleDTO> rolesToRoleDTOs(Set<Role> roles);
    Set<Role> roleDTOsToRoles(Set<RoleDTO> roleDTOs);
}