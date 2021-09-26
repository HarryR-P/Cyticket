package com.kk08.classes;

import java.io.Serializable;
import java.util.UUID;

public class UserObject implements Serializable {

    private final String userName;
    private final UUID id;

    public UserObject(String username) {
        this.userName = username;
        this.id = UUID.randomUUID();
    }

    public String getUserName() {
        return userName;
    }

    public UUID getID() {
        return id;
    }

}
