package com.kk08.CyTicketServer.repos;

import com.kk08.CyTicketServer.models.Rating;
import com.kk08.CyTicketServer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Searches the database for the user/s with the passed in UUID
     * @param id UUID of the user to be found
     * @return A list a Users that match the UUID param
     */
    default List<User> findByUUID(UUID id) {
        List<User> tempResults = new ArrayList<>();

        for (User user : this.findAll()) {
            if (user.getUserId().equals(id)) {
                tempResults.add(user);
            }
        }

        return tempResults;
    }

    /**
     * Searches the database for the user/s with the passed in username
     * @param name Username of the user to be found
     * @return A list a Users that match the name param
     */
    default List<User> findByUserName(String name) {
        List<User> tempResults = new ArrayList<>();

        for (User user : this.findAll()) {
            if (user.getUserName().equals(name)) {
                tempResults.add(user);
            }
        }

        return tempResults;
    }

    /**
     * Searches the database for the user/s with the passed in email
     * @param email Email of the user to be found
     * @return A list a Users that match the email param
     */
    default List<User> findByEmail(String email) {
        List<User> tempResults = new ArrayList<>();

        for (User user : this.findAll()) {
            if (user.getEmail().equals(email)) {
                tempResults.add(user);
            }
        }

        return tempResults;
    }

}
