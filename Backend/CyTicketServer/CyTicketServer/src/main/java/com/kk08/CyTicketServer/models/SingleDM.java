package com.kk08.CyTicketServer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

/**
 * Spring model used to build and
 * define the singleDM table
 *
 * @author carsonc@iastate.edu
 * @author Carson Campbell
 */

@Entity
@Table(name = "singleDM")
public class SingleDM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowId")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer rowId;

    @Column(name = "DMId")
    @NotFound(action = NotFoundAction.IGNORE)
    private String DMId;

    @Column(name = "userId1")
    @NotFound(action = NotFoundAction.IGNORE)
    private UUID userId1;

    @Column(name = "userId2")
    @NotFound(action = NotFoundAction.IGNORE)
    private UUID userId2;

    @Column(name = "messages")
    @NotFound(action = NotFoundAction.IGNORE)
    private String messages;

    @Column(name = "date")
    @NotFound(action = NotFoundAction.IGNORE)
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "dmlist_id")
    private DMList dmlist;

    public SingleDM() { }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public UUID getUserId1() {
        return userId1;
    }

    public void setUserId1(UUID userId1) { this.userId1 = userId1; }

    public UUID getUserId2() {
        return userId2;
    }

    public void setUserId2(UUID userId2) {
        this.userId2 = userId2;
    }

    public String getDMId() {
        return DMId;
    }

    public void setDMId(String DMId) {
        this.DMId = DMId;
    }

    public String getMSG() {return messages;}

    public void setMSG(String messages) {
        this.messages = messages;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public DMList getDmlist() {
        return dmlist;
    }

    public void setDmlist(DMList dmlist) {
        this.dmlist = dmlist;
    }
}