package com.kk08.CyTicketServer.helpers;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * This is the helper class that manages all the Rating and Forum custom ids
 *
 * @author robertb3@iastate.edu
 * @author Robert Brustkern
 */
public class IdHelper {
    private static int forumId = 0;
    private static int ratingId = 0;

    /**
     * Sets the forum id to the given parameter
     * @param forumId
     */
    public static void setForumId(int forumId) {
        IdHelper.forumId = forumId;
    }

    /**
     * Sets the rating id to the given parameter
     * @param ratingId
     */
    public static void setRatingId(int ratingId) {
        IdHelper.ratingId = ratingId;
    }

    /**
     * @return The current Forum id
     */
    public static int getForumCurrentId() {
        return forumId;
    }

    /**
     *
     * @return The current Rating id
     */
    public static int getRatingCurrentId() {
        return ratingId;
    }

    /**
     * Increments the rating id by 1 and returns the previous id
     * @return The next id available for use
     */
    public static int assignRatingId() {
        int retVal = ratingId;
        ratingId++;
        return retVal;
    }

    /**
     * Increments the forum id by 1 and returns the previous id
     * @return The next id available for use
     */
    public static int assignForumInd() {
        int retVal = forumId;
        forumId++;
        return retVal;
    }

    public IdHelper() {
        try {
            loadIds();
        } catch (Exception e) {
            System.out.println("Error loadingIds");
        }
    }

    /**
     * Reads in the ids from the text file and parses them into usable Integers
     * @throws URISyntaxException
     */
    public static void loadIds() throws URISyntaxException {
        BufferedReader reader;
        URL res = IdHelper.class.getClassLoader().getResource("IDs.txt");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();

        try {
            reader = new BufferedReader(new FileReader(absolutePath));
            String line = reader.readLine();

            for (int i = 0; i < 2; i++) {
                String value;
                if (i == 0) {
                    value = line.substring(8);
                    forumId = Integer.valueOf(value);
                } else {
                    value = line.substring(9);
                    ratingId = Integer.valueOf(value);
                }

                line = reader.readLine();
            }

            reader.close();

        } catch (IOException ex) {
            System.out.println("Couldn't find file");
        }

        //System.out.println("IDS ARE: " + ratingId + ", " + forumId);
    }

    /**
     * Takes the current stats for the rating and forum ids and saves them to the existing text file
     * @throws URISyntaxException
     */
    public static void saveIds() throws URISyntaxException {
        BufferedWriter writer;
        URL res = IdHelper.class.getClassLoader().getResource("IDs.txt");
        File file = Paths.get(res.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();

        try {
            writer = new BufferedWriter(new FileWriter(absolutePath));
            String line;

            for (int i = 0; i < 2; i++) {
                String value;
                if (i == 0) {
                    line = "forumId:" + forumId + "\n";
                } else {
                    line = "ratingId:" + ratingId;
                }

                writer.write(line);
            }

            writer.close();

        } catch (IOException ex) {
            System.out.println("Couldn't find file");
        }
    }
}
