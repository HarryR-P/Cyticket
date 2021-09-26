package com.kk08.CyTicketServer.dataAccess;

import com.kk08.CyTicketServer.dao.UserDao;
import com.kk08.CyTicketServer.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Temporary "Database" that will hold the user information

//@Repository("userDao")
public class UserDataAccessService {
//public class UserDataAccessService implements UserDao {
    /**private static List<User> DB = new ArrayList<>();


    @Override
    public int insertUser(UUID id, User user) {
        DB.add(new User(user.getUserName(), user.getEmail()));
        return 1;
    }

    @Override
    public List<User> selectAllUsers() {
        return DB;
    }

    @Override
    public Optional<User> selectUserById(UUID id) {
        return DB.stream().filter(user -> user.getUserId().equals(id)).findFirst();
    }

    @Override
    public int deleteUserById(UUID id) {
        Optional<User> userMaybe = selectUserById(id);

        if (!userMaybe.isPresent()) {
            return 0;
        }

        DB.remove(userMaybe.get());
        return 1;
    }

    @Override
    public int updateUserById(UUID id, User update) {
        return selectUserById(id).map(user -> {
            int indexOfUserToUpdate = DB.indexOf(user);
            if (indexOfUserToUpdate >= 0) {
                DB.set(indexOfUserToUpdate, new User(update.getUserName(), update.getEmail()));
                return 1;
            }
            return 0;
        }).orElse(0);
    }
    **/
}
