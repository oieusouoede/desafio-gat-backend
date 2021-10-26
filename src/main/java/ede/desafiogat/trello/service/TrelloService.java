package ede.desafiogat.trello.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import ede.desafiogat.domain.models.BoardList;
import ede.desafiogat.trello.dto.BoardDTO;
import ede.desafiogat.trello.dto.CardDTO;
import ede.desafiogat.trello.dto.BoardListDTO;
import ede.desafiogat.trello.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    public BoardDTO returnBoardDTO(String name) throws UnirestException, ParseException {

        JSONObject boardJson = checkIfBoardExists();

        if (boardJson == null) {
            boardJson = createBoard(name);
        }

        String boardID = (String) boardJson.get("id");
        String boardName = (String) boardJson.get("name");

        return new BoardDTO(boardID, boardName);
    }

    public BoardListDTO returnListDTO(String listName, BoardDTO boardDTO) throws UnirestException {

        BoardListDTO boardList = null;

        try {
            JSONObject listJson = checkIfListExists(listName, boardDTO.getBoardId());
            if (listJson == null) {
                listJson = createList(listName, boardDTO.getBoardId());
            }

            String mailListID = (String) listJson.get("id");
            String mailListName = (String) listJson.get("name");
            boardList = new BoardListDTO(mailListID, mailListName, boardDTO);
        } catch (ParseException e){
            System.out.println("Quebrou tentando retornar a lista");
        }

        return boardList;
    }

    private JSONObject checkIfBoardExists() throws UnirestException, ParseException {

        HttpResponse<String> response = Unirest.get(BASE_URL + "1/search")
                .header("Accept", "application/json")
                .queryString("modelTypes", "boards")
                .queryString("query", "GAT")
                .queryString("key", API_KEY)
                .queryString("token", USER_TOKEN)
                .asString();

        JSONObject jsonresp = (JSONObject) parser.parse(response.getBody());
        JSONArray boardsFound = (JSONArray) jsonresp.get("boards");

        if (boardsFound.isEmpty()){
            return null;
        } else {
            return (JSONObject) boardsFound.get(0);
        }
    }

    private JSONObject createBoard (String name) throws UnirestException, ParseException {

        HttpResponse<String> response = Unirest.post(BASE_URL + "1/boards/")
                .queryString("name", name)
                .queryString("defaultLists", "false")
                .queryString("key", API_KEY)
                .queryString("token", USER_TOKEN)
                .asString();

        return (JSONObject) parser.parse(response.getBody());
    }

    private JSONObject checkIfListExists(String listName, String boardId) throws UnirestException {
        JSONObject foundList = null;
        try {
            HttpResponse<String> response = Unirest.get(BASE_URL + "1/boards/" + boardId + "/lists")
                    .queryString("key", API_KEY)
                    .queryString("token", USER_TOKEN)
                    .asString();

            JSONArray listsFound = (JSONArray) parser.parse(response.getBody());

            if (listsFound.isEmpty()) {
                foundList = null;
            } else {
                for (int i = 0; i < listsFound.size(); i++) {
                    JSONObject obj = (JSONObject) listsFound.get(i);
                    String objListName = (String) obj.get("name");
                    if (objListName.equals(listName)) {
                        foundList = obj;
                    }
                }
            }

        } catch (ParseException e){
            System.out.println("Prse exceptions no checkIfListExists()");
        }
        return foundList;
    }

    public JSONObject createList (String listName, String boardID) throws UnirestException, ParseException {
        HttpResponse<String> response = Unirest.post(BASE_URL + "1/lists/")
                .queryString("name", listName)
                .queryString("idBoard", boardID)
                .queryString("key", API_KEY)
                .queryString("token", USER_TOKEN)
                .asString();

        return (JSONObject) parser.parse(response.getBody());
    }

    public CardDTO createCard (BoardListDTO list, String mailSubject, String mailContent) throws UnirestException, ParseException {

        HttpResponse<String> response = Unirest.post(BASE_URL + "1/cards/")
                .header("Accept", "application/json")
                .queryString("name", mailSubject)
                .queryString("desc", mailContent)
                .queryString("idList", list.getListId())
                .queryString("key", API_KEY)
                .queryString("token", USER_TOKEN)
                .asString();

        JSONObject jsonresp = (JSONObject) parser.parse(response.getBody());
        String cardId = (String) jsonresp.get("id");
        String cardTitle = (String) jsonresp.get("name");

        return new CardDTO(cardId, list, cardTitle, mailContent);
    }

}
