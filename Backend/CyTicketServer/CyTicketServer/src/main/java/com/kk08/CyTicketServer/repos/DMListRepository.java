package com.kk08.CyTicketServer.repos;

import com.kk08.CyTicketServer.models.DMList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository used to help the DMList's Controller
 *
 * @author carsonc@iastate.edu
 * @author Carson Campbell
 */

public interface DMListRepository extends JpaRepository<DMList, Integer> {

    /**
     * Finds a DMList within the database based on an integer representing
     * the DMList's DMListId.
     * @param id DMListId of the DMList you want returned
     * @return A DMList with the specified DMListId value
     */
    default List<DMList> findByListId(Integer id) {
        List<DMList> tempResults = new ArrayList<>();

        for (DMList list : this.findAll()) {
            if (list.getListId() == (id)) {
                tempResults.add(list);
            }
        }

        return tempResults;
    }

    /**
     * Finds a DMList within the database based on an UUID representing
     * the DMList's connected userId.
     * @param id userId of User.
     * @return A List of DMLists that are connected to the specified User
     */
    default List<DMList> findByUserID(UUID id) {
        List<DMList> tempResults = new ArrayList<>();

        for (DMList list : this.findAll()) {
            if (list.getUserId().equals(id)) {
                tempResults.add(list);
            }
        }

        return tempResults;
    }

}
