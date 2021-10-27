package ede.desafiogat.service;

import com.google.api.services.gmail.Gmail;
import com.mashape.unirest.http.exceptions.UnirestException;
import ede.desafiogat.gmail.dto.EmailDTO;
import ede.desafiogat.gmail.service.GmailService;
import ede.desafiogat.service.dto.LogDTO;
import ede.desafiogat.trello.dto.BoardDTO;
import ede.desafiogat.trello.dto.CardDTO;
import ede.desafiogat.trello.dto.BoardListDTO;
import ede.desafiogat.trello.dto.UserDTO;
import ede.desafiogat.trello.service.TrelloService;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MailCardService {

    private GmailService gmailService;
    private TrelloService trello;
    private LogService logService;
    private static final String QUERY_PARAM = "trello";

    private static BoardListDTO MAIL_LIST;
    private static BoardListDTO READ_MAIL_LIST_ID;

    private static Gmail GMAIL;

    public void initialization() throws GeneralSecurityException, IOException, UnirestException, ParseException {

        GMAIL = gmailService.getGmailService();
        UserDTO authenticatedUser = trello.getTrelloAccess();
        logService.registerLogin(authenticatedUser);

        BoardDTO boardDTO = trello.returnBoardDTO("GAT");
        logService.registerBoard(boardDTO);

        BoardListDTO unreadListDTO = trello.returnListDTO("Emails não lidos", boardDTO);
        logService.registerList(unreadListDTO);
        MAIL_LIST = unreadListDTO;

        BoardListDTO readListDTO = trello.returnListDTO("Emails lidos", boardDTO);
        logService.registerList(readListDTO);
        READ_MAIL_LIST_ID = readListDTO;

    }

    public void getTrelloRelatedMail() throws GeneralSecurityException, IOException {

        try {
            Long lastLogDate = logService.getLastCheck();
            Long newLogDate = Instant.now().getEpochSecond();
            List<EmailDTO> emailDTOList = gmailService.getMail(lastLogDate, QUERY_PARAM, GMAIL);
            if (emailDTOList.isEmpty()) {
                System.out.println("\n" + LocalDateTime.now() + " -- Nenhum novo email encontrado. Nada a fazer.");
            } else {
                logService.registerMail(emailDTOList);
                getMailCreateCard(emailDTOList, newLogDate);
            }
        } catch (UnirestException e){
            System.out.println("Peguei unirest exception");
        } catch (ParseException e) {
            System.out.println("Peguei parse exception");
        }

    }

    private void getMailCreateCard (List<EmailDTO> emailDTOList, Long newLogDate) throws UnirestException, ParseException {

        List<LogDTO> logDTOList = new ArrayList<>();
        for (EmailDTO emailDTO : emailDTOList){
            String content = new StringBuilder()
                    .append("Remetente: ")
                    .append(emailDTO.getEmailSender())
                    .append("\nData: ")
                    .append(emailDTO.getEmailDate())
                    .append("\nConteúdo: \n")
                    .append(emailDTO.getEmailMessage())
                    .toString();

            CardDTO cardDTO = trello.createCard(MAIL_LIST, emailDTO.getEmailSubject(), content);

            logService.registerCard(cardDTO);
            logService.createLogDTO(newLogDate, emailDTO, cardDTO);
        }
        System.out.println("\n" + LocalDateTime.now() + " -- " + emailDTOList.size() + " cards(s) criados(s)");
    }
}
