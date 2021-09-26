package com.kk08.CyTicketServer.api;

import com.kk08.CyTicketServer.models.Rating;
import com.kk08.CyTicketServer.models.User;
import com.kk08.CyTicketServer.repos.RatingRepository;
import com.kk08.CyTicketServer.repos.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller used to control the Ratings
 *
 * @author robertb3@iastate.edu
 * @author Robert Brustkern
 */
@RestController
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    @ApiOperation(value = "Simply adds a new rating to a user and the database ", response = String.class, tags = "Add Rating")
    @RequestMapping(method = RequestMethod.POST, path = "/ratings/add")
    public String addRating(Rating rating) {
        ratingRepository.save(rating);
        updateUserRating(rating.getTargetUUID());
        return "Rating Successfully Saved!";
    }

    // Temporary method to get all the ratings
    @ApiOperation(value = "Gets all of the ratings in the database ", response = String.class, tags = "Get Rating")
    @RequestMapping(method = RequestMethod.GET, path = "/ratings")
    public List<Rating> getAllRatings() {
        List<Rating> results = ratingRepository.findAll();
        return results;
    }

    // Gets all of the ratings that a user has created
    @ApiOperation(value = "Gets all the ratings that a single user creates ", response = Rating.class, tags = "Get Rating")
    @RequestMapping(method = RequestMethod.GET, path = "/ratings/author/{authorUUID}")
    public List<Rating> getAllRatingsByAuthor(@PathVariable("authorUUID") UUID id) {
        List<Rating> results = ratingRepository.findByAuthorUUID(id);
        return results;
    }

    // Gets all of the ratings for the target user
    @ApiOperation(value = "Gets all the ratings that are about a single user ", response = String.class, tags = "Get Rating")
    @RequestMapping(method = RequestMethod.GET, path = "/ratings/target/{targetUUID}")
    public List<Rating> getAllRatingsByTarget(@PathVariable("targetUUID")UUID id) {
        List<Rating> results = ratingRepository.findByTargetUUID(id);
        return results;
    }

    @ApiOperation(value = "Deletes a specific rating by its id ", response = Integer.class, tags = "Delete Rating")
    @RequestMapping(method = RequestMethod.DELETE, path = "/ratings/delete/{ratingId}")
    public int deleteRating(@PathVariable("ratingId") int ratingId) {
        List<Rating> ratings = ratingRepository.findByRatingId(ratingId);

        if (ratings.isEmpty()) {
            return 1;
        }

        ratingRepository.delete(ratings.get(0));
        updateUserRating(ratings.get(0).getTargetUUID());
        return 1;
    }

    @ApiOperation(value = "Updates a specific rating in the database with new information ", response = Integer.class, tags = "Update Rating")
    @RequestMapping(method = RequestMethod.PUT, path = "/ratings/update/{ratingId}")
    public int updateRating(@PathVariable("ratingId") int ratingId, @RequestParam(required = false, name = "targetUUID")UUID target,
                            @RequestParam(required = false, name = "authorUUID")UUID author, @RequestParam(required = false, name = "title")String title,
                            @RequestParam(required = false, name = "comment")String comment, @RequestParam(required = false, name = "rateValue")double rateValue) {
        List<Rating> ratings = ratingRepository.findByRatingId(ratingId);

        if (!ratings.isEmpty()) {
            Rating updateRating = ratings.get(0);

            if (target != null) {updateRating.setTargetUUID(target);}
            if (author != null) {updateRating.setAuthorUUID(author);}
            if (title != null) {updateRating.setTitle(title);}
            if (comment != null) {updateRating.setComment(comment);}

            updateRating.setRateValue(rateValue);
            ratingRepository.save(updateRating);

            updateUserRating(updateRating.getTargetUUID());
        }

        return 1;

    }

    public void updateUserRating(UUID targetId) {
        List<Rating> ratings = ratingRepository.findByTargetUUID(targetId);
        List<User> target = userRepository.findByUUID(targetId);

        if (!target.isEmpty()) {
            //userRepository.delete(target.get(0));
            target.get(0).updateRating(ratings);
            userRepository.save(target.get(0));

        }
    }

}
