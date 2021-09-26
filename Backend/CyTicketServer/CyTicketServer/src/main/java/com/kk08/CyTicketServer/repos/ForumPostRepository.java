package com.kk08.CyTicketServer.repos;

import com.kk08.CyTicketServer.models.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository used to help the ForumPost's Controller
 *
 * @author carsonc@iastate.edu
 * @author Carson Campbell
 */

public interface ForumPostRepository extends JpaRepository<ForumPost, Integer> {

    /**
     * Finds a ForumPost within the database based on an integer representing
     * the ForumPost's postId.
     * @param id postId of the ForumPost being searched for
     * @return A single ForumPost based on the unique postId
     */
    default ForumPost findByPostId(Integer id) {
        ForumPost tempResults = null;

        for (ForumPost forumPost : this.findAll()) {
            if (forumPost.getPostId() == (id)) {
                tempResults = forumPost;
            }
        }

        return tempResults;
    }

    /**
     * Finds a ForumPost within the database based on an UUID representing
     * the ForumPost's connected userId.
     * @param id UUID of the User that is connected to the ForumPost.
     * @return A List of ForumPosts that are connected to that userId
     */
    default List<ForumPost> findByUserID(UUID id) {
        List<ForumPost> tempResults = new ArrayList<>();

        for (ForumPost forumPost : this.findAll()) {
            if (forumPost.getUserId().equals(id)) {
                tempResults.add(forumPost);
            }
        }

        return tempResults;
    }


    /**
     * Finds a ForumPost within the database based on a String representing
     * the ForumPost's tag.
     * @param str tag String of the ForumPost.
     * @return A List of ForumPosts that have the specified tag value
     */
    default List<ForumPost> findByString(String str) {
        List<ForumPost> tempResults = new ArrayList<>();

        for (ForumPost forumPost : this.findAll()) {
            if (forumPost.getTag().equals(str)) {
                tempResults.add(forumPost);
            }
        }

        return tempResults;
    }

}
