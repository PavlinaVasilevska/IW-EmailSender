package com.example.EmailSender.mapper;

import com.example.EmailSender.domain.Occurrence;
import com.example.EmailSender.dto.OccurrenceDTO;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring", uses = {EmailJobMapper.class})
public interface OccurrenceMapper {

    OccurrenceDTO toDto(Occurrence occurrence);

    Occurrence toEntity(OccurrenceDTO occurrenceDTO);

    List<OccurrenceDTO> toDtoList(List<Occurrence> occurrences);
}