package com.example.cyticketclient.net_utils;

import com.android.volley.VolleyError;
import com.example.cyticketclient.interfaces.IUserListener;
import com.example.cyticketclient.interfaces.ListenerInterface;
import com.example.cyticketclient.interfaces.ServerCallInterface;
import com.example.cyticketclient.networking.ServerCall;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;


public class GlobalUser implements ListenerInterface {

    private String TAG = GlobalUser.class.getSimpleName();
    private String UUID;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private float avgRating;
    private ServerCallInterface call;
    private IUserListener listener;

    /**
     * Constructor for GlobalUser class. Creates new ServerCall.
     */
    public GlobalUser(){
       call = new ServerCall();
    }

    /**
     * Constructor for GlobalUser class. Injects given ServerCall class
     * @param call
     */
    public GlobalUser(ServerCallInterface call){
        this.call = call;
    }

    /**
     * When given a valid username and password the setUser method sets all of the user variables.
     * @param userName
     * @param password
     */
    public void setUser(String userName, String password, IUserListener listener)
    {
        this.password = password;
        this.listener = listener;
        this.userName = userName;
        call.get(Const.URL_USER_GET_REQUEST + "?userName=" + userName + "&password=" + password, this);
    }

//    public void getUserDialog(String username, final ProgressDialog pDialog)
//    {
//        this.userName = userName;
//        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                Log.d(TAG, response.toString());
//                try {
//                    setUUID(response.getJSONObject(0).getString("UUID"));
//                    setFirstName(response.getJSONObject(0).getString("firstName"));
//                    setLastName(response.getJSONObject(0).getString("lastName"));
//                    setEmail(response.getJSONObject(0).getString("email"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                pDialog.hide();
//            }
//        };
//        ServerCall call = new ServerCall();
//        call.getLoading(Const.URL_USER_GET_REQUEST + "/id/" + UUID,listener,pDialog);
//    }

    /**
     * Set userName to given username
     * @param userName
     */
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    /**
     * returns username
     * @return
     */
    public String getUserName(){
        return userName;
    }

    /**
     * Sets firstName to given firstName
     * @param FistName
     */
    public void setFirstName(String FistName)
    {
        this.firstName = FistName;
    }

    /**
     * Returns firstName.
     * @return
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets lastName to given lastName.
     * @param LastName
     */
    public void setLastName(String LastName)
    {
        this.lastName = LastName;
    }

    /**
     * Returns lastName.
     * @return
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Sets email to given email.
     * @param email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Returns Email.
     * @return
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets UUID to given UUID.
     * @param UUID
     */
    public void setUUID(String UUID)
    {
        this.UUID = UUID;
    }

    /**
     * Returns UUID.
     * @return
     */
    public String getUUID()
    {
        return UUID;
    }

    /**
     * Returns Password
     * @return
     */
    public String getPassword(){
        return password;
    }

    /**
     * Sets avgRating with given avgRating.
     * @param avgRating
     */
    public void setAvgRating(float avgRating){
        this.avgRating = avgRating;
    }

    /**
     * Returns AvgRating.
     * @return
     */
    public float getAvgRating(){
        return avgRating;
    }

    @Override
    public void onSuccess(JSONArray response) {
        try {
            setUUID(response.getJSONObject(0).getString("userId"));
            setFirstName(response.getJSONObject(0).getString("firstName"));
            setLastName(response.getJSONObject(0).getString("lastName"));
            setEmail(response.getJSONObject(0).getString("email"));
            setAvgRating(Float.parseFloat(response.getJSONObject(0).getString("averageRating")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listener.onUserSuccess(response);
    }

    @Override
    public void onSuccess(String response) {
    }

    @Override
    public void onFailure(VolleyError error) {
        listener.onUserFail(error);
    }
}
