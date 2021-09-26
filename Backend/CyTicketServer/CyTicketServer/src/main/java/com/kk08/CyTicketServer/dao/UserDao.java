package com.kk08.CyTicketServer.dao;

import com.kk08.CyTicketServer.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Depreciated class that used to handle the h2 databse
 */
@Deprecated
public interface UserDao {

    int insertUser(UUID id, User user);

    // Insert someone without an id already attached
    default int insertUser(User user) {
        UUID id = UUID.randomUUID();
        return insertUser(id, user);
    }

    List<User> selectAllUsers();

    Optional<User> selectUserById(UUID id);

    int deleteUserById(UUID id);

    int updateUserById(UUID id, User user);

}
