package ede.desafiogat.domain.mappers;

import ede.desafiogat.domain.models.Email;
import ede.desafiogat.gmail.dto.EmailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmailMapper {
    EmailMapper INSTANCE = Mappers.getMapper(EmailMapper.class);

    Email toModel(EmailDTO emailDTO);
    EmailDTO toDTO(Email email);
}
