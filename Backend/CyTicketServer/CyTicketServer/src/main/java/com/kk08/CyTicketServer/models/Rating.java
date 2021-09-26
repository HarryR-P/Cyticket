package com.kk08.CyTicketServer.models;


import com.kk08.CyTicketServer.helpers.IdHelper;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.UUID;

/**
 * Entity used to model the Ratings table
 *
 * @author robertb3@iastate.edu
 * @author Robert Brustkern
 */
@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowId")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer rowId;

    @Column(name = "targetUUID")
    @NotFound(action = NotFoundAction.IGNORE)
    private UUID targetUUID;  // Who the rating is for

    @Column(name = "authorUUID")
    @NotFound(action = NotFoundAction.IGNORE)
    private UUID authorUUID; // Who wrote the rating

    @Column(name = "title")
    @NotFound(action = NotFoundAction.IGNORE)
    private String title; // Title of rating

    @Column(name = "comment")
    @NotFound(action = NotFoundAction.IGNORE)
    private String comment; // Actual description

    @Column(name = "rateValue")
    @NotFound(action = NotFoundAction.IGNORE)
    private Double rateValue;

    @Column(name = "ratingId")
    @NotFound(action = NotFoundAction.IGNORE)
    private int ratingId;

    public Rating() {
        this.ratingId = IdHelper.assignRatingId();

    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public UUID getTargetUUID() {
        return targetUUID;
    }

    public void setTargetUUID(UUID targetUUID) {
        this.targetUUID = targetUUID;
    }

    public UUID getAuthorUUID() {
        return authorUUID;
    }

    public void setAuthorUUID(UUID authorUUID) {
        this.authorUUID = authorUUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRateValue() {
        return rateValue;
    }

    public void setRateValue(Double rateValue) {
        this.rateValue = rateValue;
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int id) {
        ratingId = id;
    }
}
