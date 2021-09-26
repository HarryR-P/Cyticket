package com.kk08.CyTicketServer.repos;

import com.kk08.CyTicketServer.models.Rating;
import com.kk08.CyTicketServer.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    // Finds rating by who the target is
    /**
     * Searches the database for the rating/s with the passed in UUID as the target
     * @param id UUID of the rating to be found
     * @return A list a Ratings that match the UUID target param
     */
    default List<Rating> findByTargetUUID(UUID id) {
        List<Rating> tempResults = new ArrayList<>();

        for (Rating rating : this.findAll()) {
            if (rating.getTargetUUID().equals(id)) {
                tempResults.add(rating);
            }
        }

        return tempResults;
    }

    // Finds rating by who the author is
    /**
     * Searches the database for the rating/s with the passed in UUID as the author
     * @param id UUID of the rating to be found
     * @return A list a Ratings that match the UUID author param
     */
    default List<Rating> findByAuthorUUID(UUID id) {
        List<Rating> tempResults = new ArrayList<>();

        for (Rating rating : this.findAll()) {
            if (rating.getAuthorUUID().equals(id)) {
                tempResults.add(rating);
            }
        }

        return tempResults;
    }

    // Finds rating by what the rating id is
    /**
     * Searches the database for the rating/s with the passed in id as the ratingId
     * @param id Id of the rating to be found
     * @return A list a Ratings that match the rating id param
     */
    default List<Rating> findByRatingId(int id) {
        List<Rating> tempResults = new ArrayList<>();

        for (Rating rating : this.findAll()) {
            if (rating.getRatingId() == id) {
                tempResults.add(rating);
            }
        }

        return tempResults;
    }
}
