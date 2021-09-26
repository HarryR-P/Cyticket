package com.kk08.CyTicketServer.service;

import com.kk08.CyTicketServer.dao.ForumDao;
import com.kk08.CyTicketServer.dao.UserDao;
import com.kk08.CyTicketServer.models.ForumPost;
import com.kk08.CyTicketServer.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ForumService {
    //private final ForumDao forumDao;

    /**
    @Autowired
    public ForumService(@Qualifier("forumDao")UserDao userDao) {
        this.forumDao = forumDao;
    }

    public int addPost(ForumPost forumPost) {
        return forumDao;
    }

    public List<User> getAllUsers() {
        return forumDao.selectAllUsers();
    }

    public Optional<User> getUserById(UUID id) {
        return forumDao.selectUserById(id);
    }

    public int deleteUser(UUID id) {
        return forumDao.deleteUserById(id);
    }

    public int updateUser(UUID id, User newUser) {
        return forumDao.updateUserById(id, newUser);
    }
    **/
}
