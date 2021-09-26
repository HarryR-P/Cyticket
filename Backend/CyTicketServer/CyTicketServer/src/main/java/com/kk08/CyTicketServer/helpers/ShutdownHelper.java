package com.kk08.CyTicketServer.helpers;

import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

/**
 * Helper to manage the shutdown hook for the server
 *
 * @author robertb3@iastate.edu
 * @author Robert Brustkern
 */
@Component
public class ShutdownHelper {

    /**
     * This method automatically gets called whenever the Springboot server detects a shutdown.
     * It then calls the saveIds() method of the IdHelper to preserve the id values.
     */
    @PreDestroy
    public void destroy() {
        try {
            IdHelper.saveIds();
        } catch (Exception e) {
            System.out.println("Failed to save ids");
        }
    }

}
