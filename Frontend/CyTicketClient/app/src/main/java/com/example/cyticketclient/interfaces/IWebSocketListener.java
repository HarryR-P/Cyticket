package com.example.cyticketclient.interfaces;

import org.java_websocket.handshake.ServerHandshake;

public interface IWebSocketListener {

    void onOpen(ServerHandshake ServerHandshake);
    void onMessage(String msg);
    void onClose(int errorCode,String reason,boolean remote);
    void onError(Exception error);
}
