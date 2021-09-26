package com.kk08.CyTicketServer.WebSockets;

import com.kk08.CyTicketServer.models.SingleDM;
import com.kk08.CyTicketServer.repos.SingleDMRepository;
import com.kk08.CyTicketServer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

// Value 1 = new post
@ServerEndpoint(value = "/websocket/{uuid}")
@Component
public class ForumSocketServer {

    private static SingleDMRepository singledmRepo;

    @Autowired
    public void setMessageRepository(SingleDMRepository repo) {
        singledmRepo = repo;  // we are setting the static variable
    }


    private MessageService messageService = new MessageService();

    private static Map<Session, UUID> sessionUUIDMap = new Hashtable<>();
    private static Map<UUID, Session> uuidSessionMap = new Hashtable<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("uuid") String stringUUID) throws IOException {
        System.out.println("Connection being opened...");

        UUID uuid = UUID.fromString(stringUUID);

        sessionUUIDMap.put(session, uuid);
        uuidSessionMap.put(uuid, session);

    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        System.out.println("Message Recieved");
        if (message.equals("UPDATE_REQUEST")) {
            broadcast("UPDATE_READY");
        } else {
            System.out.println("Doing this");
            UUID recipientUUID = messageService.extractUUID(message);
            String formatedMessage = messageService.extractMessage(message);
            System.out.println("Recipient: " + recipientUUID + " | Message: " + formatedMessage);

            SingleDM savedMessage = new SingleDM();
            savedMessage.setMSG(formatedMessage);
            savedMessage.setUserId1(sessionUUIDMap.get(session));
            savedMessage.setUserId2(recipientUUID);
            savedMessage.setDate(LocalDateTime.now());
            savedMessage.setDMId((sessionUUIDMap.get(session)).toString() + " " + recipientUUID);

            singledmRepo.save(savedMessage);

            sendMessageToUser(formatedMessage, recipientUUID);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("Closing Connection...");

        UUID uuid = sessionUUIDMap.get(session);
        sessionUUIDMap.remove(session);
        uuidSessionMap.remove(uuid);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("ERROR: " + throwable.toString());
    }

    public void broadcast(String message) {
        sessionUUIDMap.forEach((session, uuid)->{
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                System.out.println("Exception: " + e.getMessage().toString());
                e.printStackTrace();
            }
        });
    }

    public void sendMessageToUser(String message, UUID recipientId) {
        try {
            uuidSessionMap.get(recipientId).getBasicRemote().sendText(message);
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

}
