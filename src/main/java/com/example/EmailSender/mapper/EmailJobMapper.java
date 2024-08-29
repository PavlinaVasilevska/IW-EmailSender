package com.example.EmailSender.mapper;

import com.example.EmailSender.domain.EmailJob;
import com.example.EmailSender.dto.EmailJobDTO;
import com.example.EmailSender.enumeration.FrequencyEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;


@Mapper(componentModel = "spring", uses = {UserMapper.class, EmailTemplateMapper.class})
public interface EmailJobMapper {

    EmailJobDTO toDto(EmailJob emailJob);
    EmailJob toEntity(EmailJobDTO emailJobDTO);

    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    void updateFromDto(EmailJobDTO emailJobDTO, @MappingTarget EmailJob emailJob);

    @Named("toFrequencyEnum")
    default FrequencyEnum mapToFrequencyEnum(String frequency) {
        return FrequencyEnum.valueOf(frequency.toUpperCase());
    }

    @Named("fromFrequencyEnum")
    default String mapFromFrequencyEnum(FrequencyEnum frequencyEnum) {
        return frequencyEnum.name();
    }
    }