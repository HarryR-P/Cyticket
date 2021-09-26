package com.example.cyticketclient;

import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.example.cyticketclient.interfaces.IUserListener;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.interfaces.ServerCallInterface;
import com.example.cyticketclient.net_utils.Const;
import com.example.cyticketclient.net_utils.GlobalUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class GlobalUserUnitTest {

    @Mock
    private ServerCallInterface serverCall;


    @Mock
    private GlobalUser user;

    @Mock
    private IUserListener listener;

    @Before
    public void setup(){
        user = new GlobalUser(serverCall);
    }

    @Test
    public void GlobalUserSetUser_isCorrect() {

        user.setUser("Admin","test",listener);
        verify(serverCall).get(eq(Const.URL_USER_GET_REQUEST + "?userName=Admin&password=test"),(ListenerInterface) anyObject());
    }

    @Test
    public void GlobalUserSetUsername_isCorrect() {
        user.setUser("Admin","test",listener);

        assertEquals(user.getUserName(),"Admin");
    }

    @Test
    public void GlobalUserGetPass_isCorrect() {
        user.setUser("Admin","test",listener);
        assertEquals("test",user.getPassword());
    }

    @Test
    public void GlobalUserOnSuccess_isCorrect() {
        JSONArray array = mock(JSONArray.class);
        JSONObject obj = mock(JSONObject.class);
        try {
            when(array.getJSONObject(0)).thenReturn(obj);
            when(obj.getString("userId")).thenReturn("testId");
            when(obj.getString("firstName")).thenReturn("testFirst");
            when(obj.getString("lastName")).thenReturn("testLast");
            when(obj.getString("email")).thenReturn("testEmail");
            when(obj.getString("averageRating")).thenReturn("1.5");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        user.setUser("Admin","test",listener);
        user.onSuccess(array);
        String[] actual = {user.getUUID(),user.getFirstName(),user.getLastName(),user.getEmail(),user.getAvgRating() + ""};
        String[] expected = {"testId","testFirst","testLast","testEmail","1.5"};

        assertArrayEquals(expected,actual);
    }

    @Test
    public void GlobalUserSetFirstName_isCorrect() {
        user.setFirstName("test");

        assertEquals(user.getFirstName(),"test");
    }

    @Test
    public void GlobalUserSetEmail_isCorrect() {
        user.setEmail("test");

        assertEquals(user.getEmail(),"test");
    }

    @Test
    public void GlobalUserSetRating_isCorrect() {
        user.setAvgRating((float) 1.5);

        assertEquals(1.5,user.getAvgRating(),0.1);
    }
}