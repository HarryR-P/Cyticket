package com.example.cyticketclient.data;

import java.util.UUID;

public class RatingObject {

    private UUID targetUUID, authorUUID;
    private String title, comment;
    private Double rateValue;
    private int ratingId;

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

    public void setRateValue(String rateValue) {
        this.rateValue = Double.parseDouble(rateValue);
    }

    public int getRatingId() {
        return ratingId;
    }

    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

}
