package ede.desafiogat.service;

import ede.desafiogat.gmail.dto.EmailDTO;
import ede.desafiogat.trello.dto.BoardDTO;
import ede.desafiogat.trello.dto.CardDTO;
import ede.desafiogat.trello.dto.ListDTO;
import ede.desafiogat.trello.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LogService {

    List<EmailDTO> receivedMail;
    List<CardDTO> createdCards;
    List<String> logs;

    // Salva nos registros (db)
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

    public void registerNewBoard (BoardDTO board) {
        System.out.println("\n" + LocalDateTime.now() + " -- Board criada no Trello: " + board);
    }

    public void registerNewList (ListDTO list) {
        System.out.println("\n" + LocalDateTime.now() + " -- Lista criada no Trello: " + list);
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
