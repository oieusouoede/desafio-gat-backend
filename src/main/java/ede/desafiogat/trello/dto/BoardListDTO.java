package ede.desafiogat.trello.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDTO {

    private String listId;
    private String listName;
    private BoardDTO board;
    private java.util.List<CardDTO> cards;

}