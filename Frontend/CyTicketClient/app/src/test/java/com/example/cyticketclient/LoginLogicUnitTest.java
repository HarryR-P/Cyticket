package com.example.cyticketclient;


import android.content.Context;
import android.widget.TextView;

import com.example.cyticketclient.app.AppController;
import com.example.cyticketclient.interfaces.IUserListener;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.logic.LoginLogic;
import com.example.cyticketclient.net_utils.Const;
import com.example.cyticketclient.net_utils.GlobalUser;
import com.example.cyticketclient.networking.ServerCall;

import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class LoginLogicUnitTest {

    @Mock
    private Context context;

    @Mock
    private TextView errorMsg;

    @Mock
    private LoginLogic logic;

    @Mock
    private ServerCall call;

    @Mock
    private GlobalUser user;

    @Mock
    private AppController app;

    @Before
    public void setup(){
        logic = new LoginLogic(call,user,app);
    }

    @Test
    public void CheckUser_isCorrect(){
        logic.checkUser("test","test",context,errorMsg);

        verify(call).get(eq(Const.URL_USER_GET_REQUEST + "?userName=" + "test" + "&password=" + "test"),(ListenerInterface) anyObject());
    }

    @Test
    public void onSuccessFail_isCorrect(){
        logic.checkUser("test","test",context,errorMsg);
        JSONArray array = mock(JSONArray.class);
        when(array.length()).thenReturn(0);
        logic.onSuccess(array);

        verify(errorMsg).setText(eq("Incorrect username/password. user: Admin pass: test"));
    }

    @Test
    public void onSuccess_isCorrect(){
        logic.checkUser("test","test",context,errorMsg);
        JSONArray array = mock(JSONArray.class);
        AppController app = mock(AppController.class);
        when(array.length()).thenReturn(5);
        logic.onSuccess(array);

        verify(user).setUser(eq("test"),eq("test"),(IUserListener) anyObject());
    }
}
