package com.example.cyticketclient.data;

import java.util.UUID;

/***
 * Used to store all of the information for each forum posts
 */
public class PostObject {
    private String title;
    private String body;
    private int postId;
    private UUID authorId;
    private float rating;
    private String price;
    private String userName;

    public PostObject() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public UUID getAuthorId() {
        return authorId;
    }

    public void setAuthorId(UUID authorId) {
        this.authorId = authorId;
    }

    public void setPostRating(float rating){
        this.rating = rating;
    }

    public float getPostRating(){
        return rating;
    }

    public void setPrice(String price){
        this.price = price;
    }

    public String getPrice(){
        return price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
