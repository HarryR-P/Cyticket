package com.example.cyticketclient.logic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.cyticketclient.UI.MainActivity;
import com.example.cyticketclient.app.AppController;
import com.example.cyticketclient.data.DatabaseAdapter;
import com.example.cyticketclient.interfaces.IDatabaseListener;
import com.example.cyticketclient.interfaces.IUserListener;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.net_utils.Const;
import com.example.cyticketclient.net_utils.GlobalUser;
import com.example.cyticketclient.networking.ServerCall;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class LoginLogic implements ListenerInterface, IDatabaseListener, IUserListener {

    private boolean userResponse;
    private String username;
    private String password;
    private Context context;
    private TextView errorText;
    private ProgressDialog pDialog;
    private DatabaseAdapter forumData;
    private ServerCall call;
    private GlobalUser user;
    AppController app;

    public LoginLogic() {
        call = new ServerCall();
        user = new GlobalUser();
        app = AppController.getInstance();
    }

    public LoginLogic(ServerCall call,GlobalUser user,AppController app){
        this.call = call;
        this.user = user;
        this.app = app;
    }

    /**
     * Checks whether the user is valid and sets globalUser and goes to mainMethod.
     * @param userName
     * @param password
     * @param c
     * @param errorMsg
     */
    public void checkUser(String userName, String password, Context c, TextView errorMsg)
    {
        this.username = userName;
        this.context = c;
        this.password = password;
        errorText = errorMsg;
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();

        forumData = new DatabaseAdapter(context);

        // goto onSuccess(JSONArray)
        call.get(Const.URL_USER_GET_REQUEST + "?userName=" + userName + "&password=" + password ,this);
    }

    @Override
    public void onSuccess(JSONArray response) {
        pDialog.hide();
        if(response.length() != 0) {
            user.setUser(username,password,this);
            // goto onUserSuccess
            app.setUser(user);

        }else
        {
            errorText.setText("Incorrect username/password. user: Admin pass: test");
        }
    }

    @Override
    public void onSuccess(String response) {
    }

    @Override
    public void onFailure(VolleyError error) {
        pDialog.hide();
        errorText.setText("Incorrect username/password. user: Admin pass: test");
    }

    @Override
    public void onPostDataSuccess(JSONArray response) {
        // goto on RatingDataSuccess
        forumData.refreshRatings(this);
    }

    @Override
    public void onRatingDataSuccess(JSONArray response) {
        // goto mainActivity
        Intent toHub = new Intent(context, MainActivity.class);
        context.startActivity(toHub);
    }

    @Override
    public void onDataFail(VolleyError error) {
    }

    @Override
    public void onUserSuccess(JSONArray response) {
        // goto onPostDataSuccess
        forumData.refreshDatabase(this);
    }

    @Override
    public void onUserFail(VolleyError error) {
    }
}
