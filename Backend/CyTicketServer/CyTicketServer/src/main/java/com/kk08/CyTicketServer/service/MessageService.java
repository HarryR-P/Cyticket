package com.kk08.CyTicketServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MessageService {

    public UUID extractUUID(String content) {
        String[] segments = content.split("~");

        UUID uuid;

        try {
            uuid = UUID.fromString(segments[0]);
        } catch(Exception e) {
            return null;
        }

        return uuid;
    }

    public String extractMessage(String content) {
        String[] segments = content.split("~");
        String retVal = "";

        for (int i = 1; i < segments.length; i++) {
            retVal += segments[i];
        }

        return retVal;
    }

}
