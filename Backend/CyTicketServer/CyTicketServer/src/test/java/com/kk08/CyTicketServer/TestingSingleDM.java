package com.kk08.CyTicketServer;

import com.kk08.CyTicketServer.api.SingleDMController;
import com.kk08.CyTicketServer.models.SingleDM;
import com.kk08.CyTicketServer.repos.SingleDMRepository;
import com.kk08.CyTicketServer.service.ForumService;

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
@RunWith(SpringRunner.class)
public class TestingSingleDM {

    @LocalServerPort
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void getDMTest() {
        UUID userId1 = UUID.fromString("604cdcd1-54a4-4c1c-8ea1-cc1411d29750");
        Response response = RestAssured.given().when().get("/singleDM/user1/" + userId1);

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String returnString = response.getBody().asString();

        try {
            JSONArray returnArr = new JSONArray((returnString));
            JSONObject returnObj = returnArr.getJSONObject(0);
            assertEquals(1, returnObj.get("rowId"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
