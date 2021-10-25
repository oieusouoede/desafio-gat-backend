package ede.desafiogat.service;

import com.mashape.unirest.http.exceptions.UnirestException;
import ede.desafiogat.gmail.dto.EmailDTO;
import ede.desafiogat.gmail.service.GmailService;
import ede.desafiogat.trello.dto.BoardDTO;
import ede.desafiogat.trello.dto.CardDTO;
import ede.desafiogat.trello.dto.ListDTO;
import ede.desafiogat.trello.dto.UserDTO;
import ede.desafiogat.trello.service.TrelloService;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MailCardService {

    private GmailService gmail;
    private TrelloService trello;
    private LogService logService;
    private static final String QUERY_PARAM = "trello";
    private static String MAIL_LIST_ID;

    public void initialization() throws GeneralSecurityException, IOException, UnirestException, ParseException {

        logService.initializeLog();
        UserDTO authenticatedUser = trello.getTrelloAccess();
        logService.registerLogin(authenticatedUser);

        // Nessa parte eu vou precisar verificar se já tem um board no database
        BoardDTO newBoard = trello.createBoard("GAT");
        logService.registerNewBoard(newBoard);
        ListDTO newTrelloList = trello.createList("Emails recebidos", newBoard.getBoardId());
        MAIL_LIST_ID = newTrelloList.getListId();
        logService.registerNewList(newTrelloList);

        // Primeira varredura da caixa de entrada
        getTrelloRelatedMail();
    }

    public void getTrelloRelatedMail() throws GeneralSecurityException, IOException, UnirestException, ParseException {

        List<EmailDTO> emails = gmail.getMail(buildQuery());
        logService.registerNewMail(emails);
        List<CardDTO> cards = getMailCreateCard(MAIL_LIST_ID, emails);
        logService.registerNewCards(cards);
    }

    private List<CardDTO> getMailCreateCard (String listId, List<EmailDTO> mailList) throws UnirestException, ParseException {

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

    private String buildQuery(){

        String lastCheck = logService.getLastCheck();
        String timespan = lastCheck == null ? "" : "after:"+lastCheck;
        String query = timespan + " " + QUERY_PARAM;
        System.out.println("\n" + LocalDateTime.now() + " -- Fazendo nova consulta no Gmail usando a query: " + query);
        return query;
    }

}
