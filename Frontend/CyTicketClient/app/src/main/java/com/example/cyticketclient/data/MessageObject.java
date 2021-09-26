package com.example.cyticketclient.data;

/**
 * Class for creating a MessageObject.
 * @author Logan Kroeze
 */
public class MessageObject{

    private String sender;
    private String receiver;
    private String time;
    private String date;
    private String message;
    private int messageId;

    /**
     * MessageObject constructor.
     */
    public MessageObject() {}

    /**
     * Sets sender to username of sender.
     * @param sender Username of the sender.
     */
    public void setSender(String sender)
    {
        this.sender = sender;
    }

    /**
     * Returns sender.
     * @return Username of sender.
     */
    public String getSender()
    {
        return sender;
    }

    /**
     * Set receiver to username of receiver.
     * @param receiver Username of receiver.
     */
    public void setReceiver(String receiver)
    {
        this.receiver = receiver;
    }

    /**
     * Returns receiver.
     * @return Username of receiver.
     */
    public String getReceiver()
    {
        return receiver;
    }

    /**
     * Sets time.
     * @param time Date and time.
     */
    public void setTime(String time)
    {
        this.time = time;
    }

    /**
     * Returns time.
     * @return Date and time message was sent.
     */
    public String getTime()
    {
        return time;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getDate()
    {
        return date;
    }

    /**
     * Sets message.
     * @param message Message body.
     */
    public void setMessage(String message)
    {
        this.message = message;
    }

    /**
     * Returns message.
     * @return Message body.
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Sets message ID.
     * @param messageId Message ID.
     */
    public void setMessageId(int messageId)
    {
        this.messageId = messageId;
    }

    /**
     * Returns Message ID.
     * @return Message ID.
     */
    public int getMessageId()
    {
        return messageId;
    }

}
