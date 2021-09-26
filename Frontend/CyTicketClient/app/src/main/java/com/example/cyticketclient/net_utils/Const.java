package com.example.cyticketclient.net_utils;

/**
 * Class of networking URLs.
 */
public class Const {



    public static final String URL_USER_GET_REQUEST = "http://coms-309-kk-08.cs.iastate.edu:8080/users/login";
    public static final String URL_USER_POST_REQUEST = "http://coms-309-kk-08.cs.iastate.edu:8080/users/register";
    public static final String URL_GET_TEST = "https://b347c35e-3261-4afa-a8d6-7eb155bd3dfe.mock.pstmn.io/get";
    public static final String URL_POST_TEST = "https://b347c35e-3261-4afa-a8d6-7eb155bd3dfe.mock.pstmn.io/post";
    public static final String URL_FORUM_GET_ALL_REQUEST = "http://coms-309-kk-08.cs.iastate.edu:8080/forumposts";
    public static final String URL_FORUM_POST_REQUEST = "http://coms-309-kk-08.cs.iastate.edu:8080/forumposts/add";
    public static final String URL_DM_GET_ALL_REQUEST = "http://coms-309-kk-08.cs.iastate.edu:8080/singleDM";
    public static final String URL_RATING_GET_TARGET = "http://coms-309-kk-08.cs.iastate.edu:8080/ratings/target/"; // +{targetUUID}
    public static final String URL_RATING_GET_AUTHOR = "http://coms-309-kk-08.cs.iastate.edu:8080/ratings/author/"; // +{authorUUID}
    public static final String URL_RATING_POST = "http://coms-309-kk-08.cs.iastate.edu:8080/ratings/add";
    public static final String URL_FORUM_WEBSOCKET = "ws://coms-309-kk-08.cs.iastate.edu:8080/websocket/"; // +{user UUID}

}
