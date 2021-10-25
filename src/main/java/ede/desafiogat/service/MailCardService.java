package ede.desafiogat.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import ede.desafiogat.gmail.dto.EmailDTO;
import ede.desafiogat.gmail.service.GmailService;
import ede.desafiogat.trello.service.TrelloService;
import ede.desafiogat.trello.dto.BoardDTO;
import ede.desafiogat.trello.dto.CardDTO;
import ede.desafiogat.trello.dto.ListDTO;
import ede.desafiogat.trello.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MailCardService {

    private GmailService gmail;
    private TrelloService trello;
    private LogService logService;
    private static String QUERY_PARAM = "trello";

    public void initializeStuff () throws GeneralSecurityException, IOException, UnirestException, ParseException {

        // Inicialização
        logService.initializeLog();
        UserDTO authenticatedUser = trello.getTrelloAccess();
        logService.registerLogin(authenticatedUser);

        // Nessa parte eu vou precisar verificar se já tem um board no database
        BoardDTO newBoard = trello.createBoard("GAT");
        logService.registerNewBoard(newBoard);
        ListDTO newTrelloList = trello.createList("Emails recebidos", newBoard.getBoardId());
        logService.registerNewList(newTrelloList);

        // Primeira varredura da caixa de entrada
        String firstCheck = logService.getLastCheck();
        getTrelloRelatedMail(firstCheck, newTrelloList.getListId());


    }

    public void getTrelloRelatedMail(String lastMailCheck, String listId) throws GeneralSecurityException, IOException, UnirestException, ParseException {

        List<EmailDTO> emails = gmail.getMail(lastMailCheck, QUERY_PARAM);
        logService.registerNewMail(emails);
        List<CardDTO> cards = getMailCreateCard(listId, emails);
        logService.registerNewCards(cards);


    }

    public List<CardDTO> getMailCreateCard (String listId, List<EmailDTO> mailList) throws UnirestException, ParseException {

        List<CardDTO> createdCards = new ArrayList<>();

        for (EmailDTO mail : mailList){
            String content = new StringBuilder()
                    .append("Remetente: ")
                    .append(mail.getSender())
                    .append("\nData: ")
                    .append(mail.getMessageDate())
                    .append("\nConteúdo: \n")
                    .append(mail.getMessage())
                    .toString();

            createdCards.add(trello.createCard(listId, mail.getId(), mail.getSubject(), content));
        }
        return createdCards;

    }

}
