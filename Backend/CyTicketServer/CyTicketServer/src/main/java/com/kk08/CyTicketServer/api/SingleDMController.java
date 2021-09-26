package com.kk08.CyTicketServer.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kk08.CyTicketServer.models.DMList;
import com.kk08.CyTicketServer.models.ForumPost;
import com.kk08.CyTicketServer.models.SingleDM;
import com.kk08.CyTicketServer.repos.SingleDMRepository;
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
 * and access the SingleDM table
 *
 * @author carsonc@iastate.edu
 * @author Carson Campbell
 */

//@RequestMapping("api/v1/singleDM")
@Api(value = "SingleDMController", description = "Spring API for the ForumPost entity")
@RestController
public class SingleDMController {

    @Autowired
    private SingleDMRepository SingleDMRepository;

    @ApiOperation(value = "Adds a new SingleDM and links it to a particular DMList based on the DMId passed in.", response = String.class, tags = "Add SingleDM")
    @RequestMapping(method = RequestMethod.POST, path = "/singleDM/add")
    public String addPost(SingleDM post) {
        SingleDMRepository.save(post);
        return "New Post " + post.getDMId() + " Saved";
    }

    @ApiOperation(value = "Returns all SingleDMs.", response = ForumPost.class, tags = "Get SingleDMs")
    @RequestMapping(method = RequestMethod.GET, path = "/singleDM")
    public List<SingleDM> getAllOwners() {
        List<SingleDM> results = SingleDMRepository.findAll();
        return results;
    }

    @ApiOperation(value = "Returns SingleDM based on its DMId.", response = ForumPost.class, tags = "Get SingleDMs")
    @RequestMapping(method = RequestMethod.GET, path = "/singleDM/{DMId}")
    public List<SingleDM> findOwnerById(@PathVariable("DMId") String id) {
        List<SingleDM> results = SingleDMRepository.findByDMId(id);
        return results;
    }

    //@RequestMapping(method = RequestMethod.GET, path = "/singleDM/msg/{messages}/{pos}")
    //public List<SingleDM> getTag(@PathVariable(value="messages") String[] s, @PathVariable(value="pos") int p) {
    //    List<SingleDM> results = SingleDMRepository.findByString(s[p]);
    //    return results;
    //}

    @ApiOperation(value = "Returns SingleDM based on a userId referenced at userId1.", response = ForumPost.class, tags = "Get SingleDMs")
    @RequestMapping(method = RequestMethod.GET, path = "/singleDM/user1/{userId1}")
    public List<SingleDM> getUserId1(@PathVariable(value="userId1") UUID id) {
        List<SingleDM> results = SingleDMRepository.findByUserID(id);
        return results;
    }

    @ApiOperation(value = "Returns SingleDM based on a userId referenced at userId2.", response = ForumPost.class, tags = "Get SingleDMs")
     @RequestMapping(method = RequestMethod.GET, path = "/singleDM/user2/{userId2}")
     public List<SingleDM> getUserId2(@PathVariable(value="userId2") UUID id) {
     List<SingleDM> results = SingleDMRepository.findByUserID(id);
     return results;
     }

    @RequestMapping(method = RequestMethod.GET, path = "/singleDM/messages/{userId1}/{userId2}")
    public List<SingleDM> getConversationMessages(@PathVariable(value="userId1") UUID id1, @PathVariable(value="userId2") UUID id2) {

        List<SingleDM> id1Results = SingleDMRepository.findByUserID(id1);
        List<SingleDM> id2Results = SingleDMRepository.findByUserID(id2);

        List<SingleDM> overallResults = SingleDMRepository.findByUserID(id1);

        for (SingleDM list : id1Results) {
            if (list.getUserId2() == (id2)) {
                overallResults.add(list);
            }
        }

        for (SingleDM list : id1Results) {
            if (list.getUserId1() == (id1)) {
                overallResults.add(list);
            }
        }

        return overallResults;
    }

    @ApiOperation(value = "Update the indicated SingleDM", response = Integer.class, tags = "Update SingleDM")
    @ApiResponses(value = {

            @ApiResponse(code = 1, message = "SingleDM updated"),
            @ApiResponse(code = 0, message = "No SingleDM at indicated DMId")
    })
    @RequestMapping(method = RequestMethod.PUT, path = "/singleDM/update/{DMId}")
    public int updateForumPost(@PathVariable("DMId") String DMId, @RequestParam(required = false, name = "messages")String msg) {
        List<SingleDM> forums = SingleDMRepository.findByDMId(DMId);

        if (forums.isEmpty()) {
            return 0;
        } else {
            // Simple Test of the update
            SingleDM updateForum = forums.get(0);

            if (DMId != null) { updateForum.setDMId(DMId); }
            if (msg != null) { updateForum.setMSG(msg); }


            SingleDMRepository.save(updateForum);
        }

        return 1;
    }

    @ApiOperation(value = "Delete SingleDM based on indicated DMId", response = Integer.class, tags = "Delete SingleDM")
    @ApiResponses(value = {
            @ApiResponse(code = 1, message = "DMList Deleted")
    })
    @RequestMapping(method = RequestMethod.DELETE, path = "/singleDM/delete/{DMId}")
    public int deleteUser(@PathVariable("DMId") String id) {
        List<SingleDM> forums = SingleDMRepository.findByDMId(id);

        if (forums.isEmpty()) {
            return 1;
        }

        SingleDMRepository.delete(forums.get(0));
        return 1;
    }

}