package com.example.cyticketclient;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.example.cyticketclient.data.MessageObject;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class MessagesTest {

    @Mock
    private MessageObject message;

    @Before
    public void setup() {
        message = new MessageObject();
    }

    @Test
    public void setSender_isCorrect() {
        message.setSender("test");

        assertEquals(message.getSender(), "test");
    }

    @Test
    public void setReceiver_isCorrect() {
        message.setReceiver("test");

        assertEquals(message.getReceiver(), "test");
    }

    @Test
    public void setMessage_isCorrect() {
        message.setMessage("test");

        assertEquals(message.getMessage(), "test");
    }

    @Test
    public void setMessageId_isCorrect() {
        message.setMessageId(123);

        assertEquals(message.getMessageId(), 123);
    }

    @Test
    public void setTime_isCorrect() {
        message.setTime("12:00");

        assertEquals(message.getTime(), "12:00");
    }

    @Test
    public void setDate_isCorrect() {
        message.setDate("08-12-2020");

        assertEquals(message.getDate(), "08-12-2020");
    }
}
