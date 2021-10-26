package ede.desafiogat.service;

import ede.desafiogat.domain.mappers.BoardMapper;
import ede.desafiogat.domain.mappers.BoardListMapper;
import ede.desafiogat.domain.models.Board;
import ede.desafiogat.domain.models.BoardList;
import ede.desafiogat.gmail.dto.EmailDTO;
import ede.desafiogat.repositories.BoardListRepository;
import ede.desafiogat.repositories.BoardRepository;
import ede.desafiogat.trello.dto.BoardDTO;
import ede.desafiogat.trello.dto.CardDTO;
import ede.desafiogat.trello.dto.BoardListDTO;
import ede.desafiogat.trello.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class LogService {

    List<EmailDTO> receivedMail;
    List<CardDTO> createdCards;
    List<String> logs;

    private BoardRepository boardRepository;
    private BoardListRepository listRepository;

    private final BoardMapper boardMapper = BoardMapper.INSTANCE;
    private final BoardListMapper listMapper = BoardListMapper.INSTANCE;

    public void registerBoard (BoardDTO boardDTO) {
        Board board = boardMapper.toModel(boardDTO);
        Board createdBoard = boardRepository.save(board);
        System.out.println("\n" + LocalDateTime.now() + " -- Board criada no Trello: " + createdBoard);
    }

    public String registerList (BoardListDTO listDTO) {
        BoardList list = listMapper.toModel(listDTO);
        BoardList createdList = listRepository.save(list);
        System.out.println("\n" + LocalDateTime.now() + " -- Lista criada no Trello: " + createdList);
        return createdList.getListId();
    }

    public void registerNewMail(List<EmailDTO> newMail) {
        System.out.println("\n" + LocalDateTime.now() + " -- " + newMail.size() + " Novos emails encontrados");
        receivedMail.addAll(newMail);
        saveLastCheck();
    }

    // Salva a data da última consulta
    private void saveLastCheck (){
        Long now = Instant.now().getEpochSecond();
        logs.add(Long.toString(now));
    }

    public void initializeLog () {
        System.out.println("\n" + LocalDateTime.now() + " -- Inicializando registro");
        logs.add(null);
    }

    public String getLastCheck () {
        return logs.get(logs.size() - 1);
    }





    public void registerLogin(UserDTO authenticatedUser) {
        System.out.println("\n" + LocalDateTime.now() + " -- Autenticado com sucesso na Trello API:\n");
        System.out.println("ID do usuário: " + authenticatedUser.getUserId());
        System.out.println("Nome do usuário: " + authenticatedUser.getFullName());
        System.out.println("Username: " + authenticatedUser.getUserName());
    }


    public void registerNewCards(List<CardDTO> cards) {
        createdCards.addAll(cards);
        System.out.println("\n" + LocalDateTime.now() + " -- " + cards.size() + " cards criados no Trello");
        cards.forEach(System.out::println);
    }

}
