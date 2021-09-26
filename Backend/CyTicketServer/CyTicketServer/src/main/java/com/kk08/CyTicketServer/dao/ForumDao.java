package com.kk08.CyTicketServer.dao;

import com.kk08.CyTicketServer.models.ForumPost;
import com.kk08.CyTicketServer.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ForumDao {

    int insertForumPost(UUID id, ForumPost post);

    List<ForumPost> listAllPosts();

    //Optional<ForumPost> deletePostById(UUID id);

    int deletePostById(UUID id);

    int updateUserById(UUID id, User user);
}
