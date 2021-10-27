package ede.desafiogat.service.dto;

import ede.desafiogat.gmail.dto.EmailDTO;
import ede.desafiogat.trello.dto.CardDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogDTO {
   // private Long id;
    private CardDTO card;
    private EmailDTO email;
    private String logMessage;
    private Long logDate;
}
