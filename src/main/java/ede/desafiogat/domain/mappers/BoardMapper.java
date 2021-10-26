package ede.desafiogat.domain.mappers;

import ede.desafiogat.domain.models.Board;
import ede.desafiogat.trello.dto.BoardDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BoardMapper {

    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    Board toModel(BoardDTO boardDTO);
    BoardDTO toDTO(Board board);
}
