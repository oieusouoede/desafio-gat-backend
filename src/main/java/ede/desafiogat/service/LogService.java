package ede.desafiogat.service;

import ede.desafiogat.domain.mappers.*;
import ede.desafiogat.domain.models.*;
import ede.desafiogat.repositories.*;
import ede.desafiogat.gmail.dto.EmailDTO;
import ede.desafiogat.service.dto.LogDTO;
import ede.desafiogat.trello.dto.BoardDTO;
import ede.desafiogat.trello.dto.CardDTO;
import ede.desafiogat.trello.dto.BoardListDTO;
import ede.desafiogat.trello.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LogService {

    List<CardDTO> createdCards;
    List<String> logs;

    private BoardRepository boardRepository;
    private BoardListRepository listRepository;
    private LogRepository logRepository;
    private EmailRepository emailRepository;
    private CardRepository cardRepository;

    private final BoardMapper boardMapper = BoardMapper.INSTANCE;
    private final BoardListMapper listMapper = BoardListMapper.INSTANCE;
    private final LogMapper logMapper = LogMapper.INSTANCE;
    private final EmailMapper emailMapper = EmailMapper.INSTANCE;
    private final CardMapper cardMapper = CardMapper.INSTANCE;

    public void registerBoard (BoardDTO boardDTO) {
        Board board = boardMapper.toModel(boardDTO);
        Board createdBoard = boardRepository.save(board);
        System.out.println("\n" + LocalDateTime.now() + " -- Board criada no Trello: " + createdBoard);
    }

    public void registerList (BoardListDTO listDTO) {
        BoardList list = listMapper.toModel(listDTO);
        BoardList createdList = listRepository.save(list);
        System.out.println("\n" + LocalDateTime.now() + " -- Lista criada no Trello: " + createdList);
    }

    public EmailDTO registerMail(EmailDTO emailDTO) {
        Email email = emailMapper.toModel(emailDTO);
        Email createdEmail = emailRepository.save(email);
        return emailMapper.toDTO(createdEmail);
    }

    public CardDTO registerCard(CardDTO cardDTO) {
        Card newCard = cardMapper.toModel(cardDTO);
        Card createdCard = cardRepository.save(newCard);
        return cardMapper.toDTO(createdCard);
    }

    public Long getLastCheck () {
        Long lastLogDate = null;
        Log lastLog = logRepository.findFirstByOrderByIdDesc();
        if (lastLog != null) {
            lastLogDate = lastLog.getLogDate();
        }
        return lastLogDate;
    }

    public void registerLogin(UserDTO authenticatedUser) {
        System.out.println("\n" + LocalDateTime.now() + " -- Autenticado com sucesso na Trello API:\n");
        System.out.println("ID do usuário: " + authenticatedUser.getUserId());
        System.out.println("Nome do usuário: " + authenticatedUser.getFullName());
        System.out.println("Username: " + authenticatedUser.getUserName());
    }

    public void createLogDTO (Long logDate, EmailDTO emailDTO, CardDTO cardDTO){

        //


        LocalDateTime readableDate = Instant.ofEpochSecond(logDate)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        String logMessage = new StringBuilder()
                .append(readableDate)
                .append(" -- Found email with id: ")
                .append(emailDTO.getEmailId())
                .append(", Created card with id: ")
                .append(cardDTO.getCardId())
                .toString();

        LogDTO newLogDTO = new LogDTO(cardDTO, emailDTO, logMessage, logDate);
        Log newLog = logMapper.toModel(newLogDTO);
        logRepository.save(newLog);
        //EmailDTO createdEmailDTO = registerMail(emailDTO);
        //CardDTO createdCardDTO = registerCard(cardDTO);
    }

}
