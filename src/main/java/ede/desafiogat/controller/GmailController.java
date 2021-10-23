package ede.desafiogat.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import ede.desafiogat.gmail.service.GmailService;
import ede.desafiogat.models.Email;
import ede.desafiogat.trello.service.TrelloService;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GmailController {

    private GmailService gmailService;
    private TrelloService trelloService;

    @GetMapping("/getmails")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void getMails() throws IOException, GeneralSecurityException {

        List<Email> emails = gmailService.getTrelloMail();
        for (Email email : emails) {
            System.out.println(email);
        }
    }

    @GetMapping("/trello")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void trello() throws IOException, ParseException, UnirestException {
        trelloService.getTrelloAccess();
    }
}
