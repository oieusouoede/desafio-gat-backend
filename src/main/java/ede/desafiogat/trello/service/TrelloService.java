package ede.desafiogat.trello.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import ede.desafiogat.trello.dto.BoardDTO;
import ede.desafiogat.trello.dto.CardDTO;
import ede.desafiogat.trello.dto.ListDTO;
import ede.desafiogat.trello.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@Data
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TrelloService {

    private static final String TRELLO_CREDENTIALS_PATH = "/trello-credentials.json";
    private static final String BASE_URL = "https://api.trello.com/";
    private static String API_KEY;
    private static String USER_TOKEN;

    private JSONParser parser;

    // Link pra obter o token
    // https://trello.com/1/authorize?expiration=30days&name=GAT+API&scope=read,write&response_type=token&key=806a81615df0426dbe3e72d8561c5b19

    public UserDTO getTrelloAccess() {

        UserDTO user = null;

        try {
            InputStream is = TrelloService.class.getResourceAsStream(TRELLO_CREDENTIALS_PATH);

            JSONObject jsonCredentials = (JSONObject) parser.parse(new InputStreamReader(is));

            API_KEY = (String) jsonCredentials.get("api_key");
            USER_TOKEN = (String) jsonCredentials.get("user_token");

            HttpClient httpClient = HttpClients.custom()
                    .disableCookieManagement()
                    .build();
            Unirest.setHttpClient(httpClient);

            HttpResponse<String> response = Unirest.get(BASE_URL + "1/members/me/")
                    .queryString("key", API_KEY)
                    .queryString("token", USER_TOKEN)
                    .asString();

            JSONObject jsonresp = (JSONObject) parser.parse(response.getBody());
            String userId = (String) jsonresp.get("id");
            String userFullName = (String) jsonresp.get("fullName");
            String username = (String) jsonresp.get("username");

            user = new UserDTO(userId, userFullName, username);

        } catch (IOException | ParseException | UnirestException e){
            System.out.println("Quebrou no get trello access: "+ e.getMessage());
        }
        return user;
    }

    public BoardDTO createBoard(String name) throws UnirestException, ParseException {

        HttpResponse<String> response = Unirest.post(BASE_URL + "1/boards/")
                .queryString("name", name)
                .queryString("defaultLists", "false")
                .queryString("key", API_KEY)
                .queryString("token", USER_TOKEN)
                .asString();

        JSONObject jsonresp = (JSONObject) parser.parse(response.getBody());
        String boardID = (String) jsonresp.get("id");
        String boardName = (String) jsonresp.get("name");

        return new BoardDTO(boardID, boardName);
    }

    public ListDTO createList (String listName, String boardID) throws UnirestException, ParseException {
        HttpResponse<String> response = Unirest.post(BASE_URL + "1/lists/")
                .queryString("name", listName)
                .queryString("idBoard", boardID)
                .queryString("key", API_KEY)
                .queryString("token", USER_TOKEN)
                .asString();

        JSONObject jsonresp = (JSONObject) parser.parse(response.getBody());
        String mailListID = (String) jsonresp.get("id");
        String mailListName = (String) jsonresp.get("name");

        return new ListDTO(mailListID, mailListName);
    }

    public CardDTO createCard (String listId, String mailId, String mailSubject, String mailContent) throws UnirestException, ParseException {

        HttpResponse<String> response = Unirest.post(BASE_URL + "1/cards/")
                .header("Accept", "application/json")
                .queryString("name", mailSubject)
                .queryString("desc", mailContent)
                .queryString("idList", listId)
                .queryString("key", API_KEY)
                .queryString("token", USER_TOKEN)
                .asString();

        JSONObject jsonresp = (JSONObject) parser.parse(response.getBody());
        String cardId = (String) jsonresp.get("id");
        String cardTitle = (String) jsonresp.get("name");

        return new CardDTO(cardId, cardTitle, mailId, listId);
    }
}
