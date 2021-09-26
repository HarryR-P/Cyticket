package com.example.cyticketclient.app;

import com.example.cyticketclient.net_utils.GlobalUser;
import com.example.cyticketclient.net_utils.LruBitmapCache;
import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.cyticketclient.net_utils.LruBitmapCache;

import org.java_websocket.client.WebSocketClient;

public class AppController extends Application {
    public static final String TAG = AppController.class
            .getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mInstance;
    private GlobalUser user;
    private WebSocketClient forumSocket;
    private WebSocketClient DMSocket;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public static synchronized AppController getInstance() {
        return mInstance;
    }
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public GlobalUser getUser()
    {
        return user;
    }

    public void setUser(GlobalUser user)
    {
        this.user = user;
    }

    public WebSocketClient getForumSocket(){
        return forumSocket;
    }

    public void setForumSocket(WebSocketClient socket){
        this.forumSocket = socket;
    }

    public WebSocketClient getDMSocket(){
        return DMSocket;
    }

    public void setDMSocket(WebSocketClient socket){
        this.DMSocket = socket;
    }
}
