package com.kk08.CyTicketServer.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Entity used to model the Users table
 *
 * @author robertb3@iastate.edu
 * @author Robert Brustkern
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rowId")
    @NotFound(action = NotFoundAction.IGNORE)
    private Integer rowId;

    @Column(name = "userId")
    @NotFound(action = NotFoundAction.IGNORE)
    private UUID userId;

    @Column(name = "userName")
    @NotFound(action = NotFoundAction.IGNORE)
    private String userName;

    @Column(name = "email")
    @NotFound(action = NotFoundAction.IGNORE)
    private String email;

    @Column(name = "first")
    @NotFound(action = NotFoundAction.IGNORE)
    private String firstName;

    @Column(name = "last")
    @NotFound(action = NotFoundAction.IGNORE)
    private String lastName;

    @Column(name = "rating")
    @NotFound(action = NotFoundAction.IGNORE)
    private Double averageRating;

    @Column(name = "password")
    @NotFound(action = NotFoundAction.IGNORE)
    private String password;

    @OneToOne
    @JoinColumn(name = "dmlist_id")
    private DMList dmlist;

    @OneToMany(mappedBy = "forumUser")
    private Set<ForumPost> usersForumPosts;

    public User() {
        this.userId = UUID.randomUUID();
        this.averageRating = 0.0;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * Calculatates the current average rating that this user has based on all of the ratings with this user as the target
     * @param ratings A list of all the ratings where this user is the target.
     */
    public void updateRating(List<Rating> ratings) {
        double total = 0;
        double numRatings = 0;

        for (Rating rating : ratings) {
            numRatings++;
            total += rating.getRateValue();
        }

        if (numRatings != 0)
            this.averageRating = total / numRatings;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {return firstName;}

    public void setFirstName(String first) {
        firstName = first;
    }

    public String getLastName() {return lastName;}
    public void setLastName(String last) {
        lastName = last;
    }
}
