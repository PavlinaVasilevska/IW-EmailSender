package com.example.EmailSender.mapper;

import com.example.EmailSender.domain.Occurrence;
import com.example.EmailSender.dto.OccurrenceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EmailJobMapper.class})
public interface OccurrenceMapper {

    OccurrenceDTO toDTO(Occurrence occurrence);

    Occurrence toEntity(OccurrenceDTO occurrenceDTO);
}