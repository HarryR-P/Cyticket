package com.kk08;

import com.google.gson.Gson;
import com.kk08.classes.UserObject;

public class Main {

    public static void main(String[] args) {
        Gson gson = new Gson();
        UserObject user = new UserObject("Robbie");

        String converted = gson.toJson(user);

        System.out.println(converted);
    }
}
