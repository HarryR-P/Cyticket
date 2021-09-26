package com.kk08.CyTicketServer;

import com.kk08.CyTicketServer.api.UserController;
import com.kk08.CyTicketServer.models.User;
import com.kk08.CyTicketServer.repos.UserRepository;
import com.kk08.CyTicketServer.service.UserService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CyTicketServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
public class TestingUserController {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

   @Test
   public void getUserTest() {
        UUID userId = UUID.fromString("b9c34c1f-3768-41f6-bcc1-abb9ce81922f");
        Response response = RestAssured.given().when().get("/users/id/" + userId.toString());

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();

        try {
            JSONArray returnArr = new JSONArray((returnString));
            JSONObject returnObj = returnArr.getJSONObject(0);
            assertEquals("Hopper", returnObj.get("userName"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

   }

   // Test the login method
    @Test
    public void userLoginTest() {
        String userName = "Hopper";
        String password = "Elliot";
        UUID userId = UUID.fromString("b9c34c1f-3768-41f6-bcc1-abb9ce81922f");
        Response response = RestAssured.given().param("userName", userName).param("password", password).when().get("/users/login");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();

        try {
            JSONArray returnArr = new JSONArray((returnString));
            JSONObject returnObj = returnArr.getJSONObject(0);
            assertEquals(userId.toString(), returnObj.get("userId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
