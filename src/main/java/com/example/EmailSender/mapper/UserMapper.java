package com.example.EmailSender.mapper;
import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.domain.Role;
import com.example.EmailSender.domain.User;
import com.example.EmailSender.dto.EmailJobDTO;
import com.example.EmailSender.dto.RoleDTO;
import com.example.EmailSender.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


import java.util.Set;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDto(User user);

    User toEntity(UserDTO userDTO);

    Set<RoleDTO> toDtoSet(Set<Role> roles);

    Set<Role> toEntitySet(Set<RoleDTO> roleDTOs);


}