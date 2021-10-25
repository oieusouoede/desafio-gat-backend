package ede.desafiogat.events;

import com.mashape.unirest.http.exceptions.UnirestException;
import ede.desafiogat.service.MailCardService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class ScheduledTasks {

    @Autowired
    private MailCardService service;

    @Scheduled(fixedRate = 15000, initialDelay = 20000)
    public void verifyMailbox () throws GeneralSecurityException, UnirestException, IOException, ParseException {

        service.getTrelloRelatedMail();
    }
}
