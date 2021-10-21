package ede.desafiogat.controller;

import ede.desafiogat.gmail.service.GmailService;
import ede.desafiogat.models.Email;
import lombok.AllArgsConstructor;
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

    @GetMapping("/getmails")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void GetMails () throws IOException, GeneralSecurityException {

        List<Email> emails = gmailService.processMessages();
        for (Email email : emails){
            System.out.println(email);
        }
    }
}
