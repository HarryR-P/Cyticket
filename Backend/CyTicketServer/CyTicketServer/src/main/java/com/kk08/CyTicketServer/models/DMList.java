package com.kk08.CyTicketServer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

/**
 * Spring model used to build and
 * define the dmlist table
 *
 * @author carsonc@iastate.edu
 * @author Carson Campbell
 */

@Entity
@Table(name = "dmlist")
public class DMList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowId")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer rowId;

    @Column(name = "DMListId")
    @NotFound(action = NotFoundAction.IGNORE)
    private int ListId;

    @Column(name = "DMNum")
    @NotFound(action = NotFoundAction.IGNORE)
    private int DMNum;

    @Column(name = "userId")
    @NotFound(action = NotFoundAction.IGNORE)
    private UUID userId;

    @OneToOne(mappedBy = "dmlist")
    private User userDMs;

    @OneToMany(mappedBy = "dmlist")
    private Set<SingleDM> listOfDMs;

    public DMList() { }

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

    public int getListId() {
        return ListId;
    }

    public void setListId(int ListId) {
        this.ListId = ListId;
    }

    public int getAmount() {
        return ListId;
    }

    public void setAmount(int DMNum) {
        this.DMNum = DMNum;
    }

}