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
public class GATController {

    private LogService log;

    //Implementar outros endpoints

    // Mantenho esse?
    @GetMapping("/getall")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void getMailList() {
        System.out.println("Nada a fazer");
    }

    // Get Logs

    // Get cards (?)

}
