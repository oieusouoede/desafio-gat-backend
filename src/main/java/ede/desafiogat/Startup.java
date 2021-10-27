package ede.desafiogat;

import com.mashape.unirest.http.exceptions.UnirestException;
import ede.desafiogat.service.MailCardService;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class Startup {

    @Autowired
    private MailCardService service;

    @EventListener(ApplicationStartedEvent.class)
    public void onStart() throws GeneralSecurityException, UnirestException, IOException, ParseException {

        service.initialization();

    }

    @Scheduled(fixedRate = 15000, initialDelay = 30000)
    public void verifyMailbox () throws GeneralSecurityException, UnirestException, IOException, ParseException {

        service.getTrelloRelatedMail();
    }

}
