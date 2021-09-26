package com.kk08.CyTicketServer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kk08.CyTicketServer.helpers.IdHelper;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.UUID;

/**
 * Spring model used to build and
 * define the forumposts table
 *
 * @author carsonc@iastate.edu
 * @author Carson Campbell
 */

@Entity
@Table(name = "forumposts")
public class ForumPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowId")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer rowId;

    @Column(name = "postId")
    @NotFound(action = NotFoundAction.IGNORE)
    private int postId;

    @Column(name = "title")
    @NotFound(action = NotFoundAction.IGNORE)
    private String title;

    @Column(name = "postBody")
    @NotFound(action = NotFoundAction.IGNORE)
    private String postBody;

    @Column(name = "userId")
    @NotFound(action = NotFoundAction.IGNORE)
    private UUID userId;

    @Column(name = "tag")
    @NotFound(action = NotFoundAction.IGNORE)
    private String tag;

    @Column(name = "price")
    @NotFound(action = NotFoundAction.IGNORE)
    private String price;

    @ManyToOne
    //@JoinTable(name = "forumUsers",
      //      joinColumns = @JoinColumn(name = "userId"),
        //    inverseJoinColumns = @JoinColumn(name="userName")
    //)
    @JoinColumn(name = "forumUser_id")
    private User forumUser;

    public ForumPost() { this.postId = IdHelper.assignForumInd(); }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getForumId() {
        return forumUser;
    }

    public void setForumId(User forumUser) {
        this.forumUser = forumUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getPostBody() { return postBody; }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getTag() {return tag;}

    public void setTag(String tag) {
        this.tag = tag;
    }

}