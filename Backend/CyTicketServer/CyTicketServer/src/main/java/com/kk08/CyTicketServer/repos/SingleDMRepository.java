package com.kk08.CyTicketServer.repos;

import com.kk08.CyTicketServer.models.SingleDM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository used to help the SingleDM's Controller
 *
 * @author carsonc@iastate.edu
 * @author Carson Campbell
 */

public interface SingleDMRepository extends JpaRepository<SingleDM, Integer> {

    /**
     * Finds List of SingleDMs within the database based on an integer representing
     * the SingleDM's connected DMId.
     * @param id DMId connected to SingleDM.
     * @return A List of SingleDMs connected to DMList by specified DMId
     */
    default List<SingleDM> findByDMId(String id) {
        List<SingleDM> tempResults = new ArrayList<>();

        for (SingleDM dm : this.findAll()) {
            if (dm.getDMId().equals(id)) {
                tempResults.add(dm);
            }
        }

        return tempResults;
    }

    /**
     * Finds List of SingleDMs within the database based on a userId representing
     * the SingleDM's connected User.
     * @param id UUID of User.
     * @return A List of SingleDMs connected to the specified User
     */
    default List<SingleDM> findByUserID(UUID id) {
        List<SingleDM> tempResults = new ArrayList<>();

        for (SingleDM dm : this.findAll()) {
            if (dm.getUserId1().equals(id)) {
                tempResults.add(dm);
            }
        }

        return tempResults;
    }

    /**
     * Finds List of SingleDMs within the database based on a String representing
     * the SingleDM's message.
     * @param str String representing the SingleDMs message.
     * @return A List of SingleDMs that have specified message
     */
    default List<SingleDM> findByString(String str) {
        List<SingleDM> tempResults = new ArrayList<>();

        for (SingleDM dm : this.findAll()) {
            if (dm.getMSG().equals(str)) {
                tempResults.add(dm);
            }
        }

        return tempResults;
    }

}
