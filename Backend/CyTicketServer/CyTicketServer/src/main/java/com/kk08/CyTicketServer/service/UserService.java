package com.kk08.CyTicketServer.service;

import com.kk08.CyTicketServer.dao.UserDao;
import com.kk08.CyTicketServer.models.User;
import com.kk08.CyTicketServer.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service used to manage the user uniqueness methods
 *
 * @author robertb3@iastate.edu
 * @author Robert Brustkern
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /***
     * This method queries the database to determine of the email or username of a new user already exists.
     * @param email - The email field of the new user
     * @param username - The username field of the new user
     * @return Returns 1 if email already exists, 2 if username already exists, and 0 if it is safe to add.
     */
    public int userNameExists(String email, String username) {
        List<User> emails = userRepository.findByEmail(email);
        List<User> usernames = userRepository.findByUserName(username);

        if (emails.size() == 1) {
            return 1;
        } else if (usernames.size() == 1) {
            return 2;
        } else {
            return 0;
        }
    }


}

