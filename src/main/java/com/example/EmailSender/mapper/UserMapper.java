package com.example.EmailSender.mapper;
import com.example.EmailSender.domain.Role;
import com.example.EmailSender.domain.User;
import com.example.EmailSender.dto.RoleDTO;
import com.example.EmailSender.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


import java.util.Set;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);

    Set<RoleDTO> toDtoSet(Set<Role> roles);

    Set<Role> toEntitySet(Set<RoleDTO> roleDTOs);

    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    void updateUserFromDto(UserDTO userDTO, @MappingTarget User user);

}