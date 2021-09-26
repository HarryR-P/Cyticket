package com.kk08.CyTicketServer.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kk08.CyTicketServer.models.ForumPost;
import com.kk08.CyTicketServer.models.User;
import com.kk08.CyTicketServer.repos.ForumPostRepository;
import com.kk08.CyTicketServer.repos.UserRepository;
import com.kk08.CyTicketServer.service.ForumService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller used to make changes
 * and access the ForumPost table
 *
 * @author carsonc@iastate.edu
 * @author Carson Campbell
 */

//@RequestMapping("api/v1/forumposts")
@Api(value = "ForumPostController", description = "Spring API for the ForumPost entity")
@RestController
public class ForumPostController {

    //Needs to be made
    @Autowired
    private ForumPostRepository forumPostRepository;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Adds a new ForumPost and links it to a particular User based on the userId passed in.", response = String.class, tags = "Add ForumPost")
    @RequestMapping(method = RequestMethod.POST, path = "/forumposts/add")
    public String addPost(ForumPost post) {
        //forumPostRepository.save(post);

        UUID searchId = post.getUserId();

        List<User> tempUsers = userRepository.findByUUID(searchId);

        post.setForumId(tempUsers.get(0));

        forumPostRepository.save(post);

        return "New Post " + post.getPostId() + " Saved";
    }

    @ApiOperation(value = "Returns all ForumPosts.", response = ForumPost.class, tags = "Get ForumPosts")
    @RequestMapping(method = RequestMethod.GET, path = "/forumposts")
    public List<ForumPost> getAllOwners() {
        List<ForumPost> results = forumPostRepository.findAll();
        return results;
    }

    @ApiOperation(value = "Returns ForumPost based on its postId.", response = ForumPost.class, tags = "Get ForumPosts")
    @RequestMapping(method = RequestMethod.GET, path = "/forumposts/{postId}")
    public ForumPost findOwnerById(@PathVariable("postId") Integer id) {
        ForumPost results = forumPostRepository.findByPostId(id);
        return results;
    }

    @ApiOperation(value = "Returns ForumPosts based on their tag.", response = ForumPost.class, tags = "Get ForumPosts")
    @RequestMapping(method = RequestMethod.GET, path = "/forumposts/tag/{tag}")
    public List<ForumPost> getTag(@PathVariable(value="tag") String s) {
        List<ForumPost> results = forumPostRepository.findByString(s);
        return results;
    }

    @ApiOperation(value = "Returns ForumPost based on its userId.", response = ForumPost.class, tags = "Get ForumPosts")
    @RequestMapping(method = RequestMethod.GET, path = "/forumposts/user/{userId}")
    public List<ForumPost> getTag(@PathVariable(value="userId") UUID id) {
        List<ForumPost> results = forumPostRepository.findByUserID(id);
        return results;
    }

    @ApiOperation(value = "Returns User that is connected to a ForumPost based on the ForumPost's postId.", response = User.class, tags = "Get User from ForumPost")
    @RequestMapping(method = RequestMethod.GET, path = "/forumposts/userfrompost/{userId}")
    public List<User> getUserFromPost(@PathVariable(value="userId") UUID userId) {

        List<ForumPost> fUser = null;
        fUser.add(forumPostRepository.findByUserID(userId).get(0));
        List<User> returnUser = null;
        returnUser.add(fUser.get(0).getForumId());

        return returnUser;
    }

    @ApiOperation(value = "Returns username of User that is connected to a ForumPost based on the ForumPost's postId.", response = String.class, tags = "Get User from ForumPost")
    @RequestMapping(method = RequestMethod.GET, path = "/forumposts/username/{forumIdentity}")
    public String getUsername(@PathVariable(value="forumIdentity") int postId) {
        User fUser = forumPostRepository.findByPostId(postId).getForumId();
        String result = fUser.getUserName();
        return result;
    }

    @ApiOperation(value = "Returns rating of User that is connected to a ForumPost based on the ForumPost's postId.", response = String.class, tags = "Get User from ForumPost")
    @RequestMapping(method = RequestMethod.GET, path = "/forumposts/rating/{forumIdentity}")
    public Double getRating(@PathVariable(value="forumIdentity") int postId) {
        User fUser = forumPostRepository.findByPostId(postId).getForumId();
        Double result = fUser.getAverageRating();
        return result;
    }

    /*
    @RequestMapping(method = RequestMethod.PUT, path = "/forumposts/{postId}/user/{rowId}")
    public User addUserRelation(@PathVariable(value="rowId") int userId, @PathVariable(value="postId") int postId) {

        User tempUser = userRepository.findByRowId(userId);
        ForumPost tempPost = forumPostRepository.findByPostId(postId);

        tempPost.setForumId(tempUser);
        userRepository.save(tempUser);

        return tempUser;
    }\*/

    @ApiOperation(value = "Update the indicated ForumPost", response = Integer.class, tags = "Update ForumPost")
    @ApiResponses(value = {

            @ApiResponse(code = 1, message = "ForumPost updated"),
            @ApiResponse(code = 0, message = "No ForumPost at indicated postId")
    })
    @RequestMapping(method = RequestMethod.PUT, path = "/forumposts/update/{postId}")
    public int updateForumPost(@PathVariable("postId") Integer id, @RequestParam(required = false, name = "title")String title,
                          @RequestParam(required = false, name = "postBody")String postBody, @RequestParam(required = false, name = "postId")UUID postId,
                               @RequestParam(required = false, name = "tag")String tag, @RequestParam(required = false, name = "price")String price) {
        ForumPost forums = forumPostRepository.findByPostId(id);

        if (forums == null) {
            return 0;
        } else {
            // Simple Test of the update
            ForumPost updateForum = forums;

            if (title != null) { updateForum.setTitle(title); }
            if (postBody != null) { updateForum.setPostBody(postBody); }
            if (postId != null) { updateForum.setUserId(postId); }
            if (tag != null) { updateForum.setTag(tag);}
            if (price != null) { updateForum.setPrice(price); }

            forumPostRepository.save(updateForum);
        }

        return 1;
    }

    @ApiOperation(value = "Delete ForumPost based on its postId", response = Integer.class, tags = "Delete ForumPost")
    @ApiResponses(value = {
            @ApiResponse(code = 1, message = "ForumPost Deleted")
    })
    @RequestMapping(method = RequestMethod.DELETE, path = "/forumposts/delete/{postId}")
    public int deleteUser(@PathVariable("postId") Integer id) {
        ForumPost forums = forumPostRepository.findByPostId(id);

        if (forums == null) {
            return 1;
        }

        forumPostRepository.delete(forums);
        return 1;
    }

}