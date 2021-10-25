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
import java.time.ZoneId;
import java.util.List;

@Service
@AllArgsConstructor
public class LogService {

    List<EmailDTO> receivedMail;
    List<CardDTO> createdCards;
    List<String> logs;

    // Salva nos registros (db)
    public void registerNewMail(List<EmailDTO> newMail) {

        Long now = Instant.now().getEpochSecond();

        LocalDateTime moment = LocalDateTime.ofInstant(Instant.ofEpochSecond(now), ZoneId.systemDefault());

        System.out.println("\n" + moment + " -- " + newMail.size() + " Novos emails encontrados");
        receivedMail.addAll(newMail);

        logs.add(Long.toString(now));
    }

    public void printAllMail(){
        System.out.println("\n######   Esses são todos os emails encontrados:");
        receivedMail.stream().forEach(System.out::println);
    }

    public void initializeLog () {
        Long now = Instant.now().getEpochSecond();
        LocalDateTime moment = LocalDateTime.ofInstant(Instant.ofEpochSecond(now), ZoneId.systemDefault());
        System.out.println("\n" + moment + " -- Inicializando registro");
        logs.add(null);
    }

    public String getLastCheck () {
        return logs.get(logs.size() - 1);
    }

    public void registerNewBoard (BoardDTO board) {
        Long now = Instant.now().getEpochSecond();
        LocalDateTime moment = LocalDateTime.ofInstant(Instant.ofEpochSecond(now), ZoneId.systemDefault());
        System.out.println("\n" + moment + " -- Board criada no Trello: " + board);
    }
    public void registerNewList (ListDTO list) {
        Long now = Instant.now().getEpochSecond();
        LocalDateTime moment = LocalDateTime.ofInstant(Instant.ofEpochSecond(now), ZoneId.systemDefault());
        System.out.println("\n" + moment + " -- Lista criada no Trello: " + list);
    }

    public void registerLogin(UserDTO authenticatedUser) {
        Long now = Instant.now().getEpochSecond();
        LocalDateTime moment = LocalDateTime.ofInstant(Instant.ofEpochSecond(now), ZoneId.systemDefault());
        System.out.println("\n" + moment + " -- Autenticado com sucesso na Trello API:");
        System.out.println("ID do usuário: " + authenticatedUser.getUserId());
        System.out.println("Nome do usuário: " + authenticatedUser.getFullName());
        System.out.println("Username: " + authenticatedUser.getUserName());
    }


    public void registerNewCards(List<CardDTO> cards) {
        createdCards.addAll(cards);
        Long now = Instant.now().getEpochSecond();
        LocalDateTime moment = LocalDateTime.ofInstant(Instant.ofEpochSecond(now), ZoneId.systemDefault());
        System.out.println("\n" + moment + " -- " + cards.size() + " cards criados no Trello:");
        cards.stream().forEach(System.out::println);

    }
}
