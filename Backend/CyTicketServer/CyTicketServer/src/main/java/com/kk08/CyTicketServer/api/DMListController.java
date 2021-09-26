package com.kk08.CyTicketServer.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kk08.CyTicketServer.models.DMList;
import com.kk08.CyTicketServer.models.ForumPost;
import com.kk08.CyTicketServer.repos.DMListRepository;
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
 * and access the DMList table
 *
 * @author carsonc@iastate.edu
 * @author Carson Campbell
 */

//@RequestMapping("api/v1/dmlist")
@Api(value = "DMListController", description = "Spring API for the ForumPost entity")
@RestController
public class DMListController {

    @Autowired
    private DMListRepository dmlistRepository;

    @ApiOperation(value = "Adds a new DMList and links it to a particular User based on the userId passed in.", response = String.class, tags = "Add DMList")
    @RequestMapping(method = RequestMethod.POST, path = "/dmlist/add")
    public String addPost(DMList post) {
        dmlistRepository.save(post);
        return "New Post " + post.getListId() + " Saved";
    }

    @ApiOperation(value = "Returns all DMLists.", response = ForumPost.class, tags = "Get DMLists")
    @RequestMapping(method = RequestMethod.GET, path = "/dmlist")
    public List<DMList> getAllOwners() {
        List<DMList> results = dmlistRepository.findAll();
        return results;
    }

    @ApiOperation(value = "Returns DMList based on its DMListId.", response = ForumPost.class, tags = "Get DMLists")
    @RequestMapping(method = RequestMethod.GET, path = "/dmlist/{ListId}")
    public List<DMList> findOwnerById(@PathVariable("ListId") Integer id) {
        List<DMList> results = dmlistRepository.findByListId(id);
        return results;
    }

    @ApiOperation(value = "Returns DMList based on its userId.", response = ForumPost.class, tags = "Get DMLists")
     @RequestMapping(method = RequestMethod.GET, path = "/dmlist/user/{userId}")
     public List<DMList> getMSG(@PathVariable(value="userId") UUID id) {
     List<DMList> results = dmlistRepository.findByUserID(id);
     return results;
     }

    @ApiOperation(value = "Update the indicated DMList", response = Integer.class, tags = "Update DMList")
    @ApiResponses(value = {

            @ApiResponse(code = 1, message = "DMList updated"),
            @ApiResponse(code = 0, message = "No DMList at indicated ListId")
    })
    @RequestMapping(method = RequestMethod.PUT, path = "/dmlist/update/{ListId}")
    public int updateForumPost(@PathVariable("ListId") Integer ListId, @RequestParam(required = false, name = "DMNum")String DMNum, @RequestParam(required = false, name = "userId")UUID userId) {
        List<DMList> list = dmlistRepository.findByListId(ListId);

        if (list.isEmpty()) {
            return 0;
        } else {
            // Simple Test of the update
            DMList updateForum = list.get(0);

            if (ListId != null) { updateForum.setListId(ListId); }
            if (DMNum != null) { updateForum.setAmount(Integer.parseInt(DMNum)); }
            if (userId != null) { updateForum.setUserId(userId); }

            dmlistRepository.save(updateForum);
        }

        return 1;
    }

    @ApiOperation(value = "Delete DMList based on indicated DMListId", response = Integer.class, tags = "Delete DMList")
    @ApiResponses(value = {
            @ApiResponse(code = 1, message = "DMList Deleted")
    })
    @RequestMapping(method = RequestMethod.DELETE, path = "/dmlist/delete/{ListId}")
    public int deleteUser(@PathVariable("ListId") Integer id) {
        List<DMList> list = dmlistRepository.findByListId(id);

        if (list.isEmpty()) {
            return 1;
        }

        dmlistRepository.delete(list.get(0));
        return 1;
    }

}