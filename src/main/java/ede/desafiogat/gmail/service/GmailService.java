package ede.desafiogat.gmail.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartHeader;
import ede.desafiogat.models.Email;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;

import java.util.ArrayList;
import java.util.Collections;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class GmailService {

    private static final String APPLICATION_NAME = "Desafio-GAT";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_LABELS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    // Disponibiliza o serviço do gmail
    public static Gmail getGmailService() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        return service;
    }

    // Processar os emails
    public List <Email> processMessages() throws IOException, GeneralSecurityException {
        List <Email> emailObjects = new ArrayList<>();
        String user = "me";
        // pegar id, sender, subject, content e date
        List<Message> messageSnippets = getMessagesSnippets();
        for (Message message : messageSnippets){
            message = getGmailService().users().messages().get(user,message.getId()).setFormat("FULL").execute();
            String messageId = message.getId();
            MessagePart messagePart = message.getPayload();

            String sender = getFromHeader("From", messagePart);
            String subject = getFromHeader("Subject", messagePart);
            String content = getContent(message);
            Date date = new Date(message.getInternalDate());

            emailObjects.add(new Email(messageId, sender, subject, content, date));
        }
        return emailObjects;
    }

    private String getFromHeader(String field, MessagePart messagePart) {
        String fieldValue = "";
        if (messagePart != null){
            List <MessagePartHeader> headers = messagePart.getHeaders();
            for (MessagePartHeader header : headers){
                if (header.getName().equals(field)){
                    fieldValue = header.getValue().trim();
                    break;
                }
            }
        }
        return fieldValue;
    }

    private String getContent(Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            getPlainTextFromMessageParts(message.getPayload().getParts(), stringBuilder);
            if (stringBuilder.length() == 0) {
                stringBuilder.append(message.getPayload().getBody().getData());
            }
            byte[] bodyBytes = Base64.decodeBase64(stringBuilder.toString());
            String text = new String(bodyBytes, "UTF-8");
            return text;
        } catch (UnsupportedEncodingException e) {
            System.out.println("UnsupportedEncoding: " + e.toString());
            return message.getSnippet();
        }
    }

    private void getPlainTextFromMessageParts(List<MessagePart> messageParts, StringBuilder stringBuilder) {
        if (messageParts != null) {
            for (MessagePart messagePart : messageParts) {
                if (messagePart.getMimeType().equals("text/plain")) {
                    stringBuilder.append(messagePart.getBody().getData());
                }
                if (messagePart.getParts() != null) {
                    getPlainTextFromMessageParts(messagePart.getParts(), stringBuilder);
                }
            }
        }
    }

    // Buscar todas as mensagens
    private List<Message> getMessagesSnippets() throws IOException, GeneralSecurityException{
        List messagesSnippets = new ArrayList();
        Gmail service = getGmailService();
        String user = "me";
        String query = "trello";

        ListMessagesResponse response = service
                .users()
                .messages()
                .list(user)
                .setQ(query)
                .execute();

        while (response.getMessages() != null){
            messagesSnippets.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(user).setQ(query).setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        return messagesSnippets;
    }

    // Busca as credenciais
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {

        InputStream in = GmailService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

}