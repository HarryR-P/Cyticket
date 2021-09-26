package com.example.cyticketclient.networking;

import android.util.Log;

import com.example.cyticketclient.interfaces.IWebSocketListener;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebSocketCall {

    private final String TAG = WebSocketCall.class.getSimpleName();
    private IWebSocketListener AppListener;

    public WebSocketCall(){
    }

    public WebSocketClient getWebSocketClient(String URL, IWebSocketListener listener){
        URI uri;
        this.AppListener = listener;
        try {
            uri = new URI(URL);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                Log.i(TAG,"Opened");
                AppListener.onOpen(handshakedata);
            }

            @Override
            public void onMessage(String message) {
                Log.i(TAG,"Message Received");
                AppListener.onMessage(message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                Log.i(TAG,"Closed " + reason);
                AppListener.onClose(code,reason,remote);
            }

            @Override
            public void onError(Exception ex) {
                Log.i(TAG,"Error " + ex.getMessage());
                AppListener.onError(ex);
            }
        };
    }

}
