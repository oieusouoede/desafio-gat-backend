package ede.desafiogat.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import ede.desafiogat.service.LogService;
import ede.desafiogat.service.MailCardService;
import ede.desafiogat.trello.service.TrelloService;
import lombok.AllArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GmailController {

    private MailCardService service;
    private TrelloService trelloService;
    private LogService log;

    // Isso aqui ta fazendo o papel de inicializar tudo
    @GetMapping("/init")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void getInit() throws IOException, GeneralSecurityException, UnirestException, ParseException {
        service.initializeStuff();
    }

    @GetMapping("/getall")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void getMailList() {
        log.printAllMail();
    }

}
