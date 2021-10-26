package ede.desafiogat.domain.mappers;

import ede.desafiogat.domain.models.Log;
import ede.desafiogat.service.dto.LogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LogMapper {

    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);

    Log toModel(LogDTO logDTO);
    LogDTO toDTO(Log log);
}
