package com.kk08.CyTicketServer;

import com.kk08.CyTicketServer.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CyTicketServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestingMessageService {

    @TestConfiguration
    static class MessageContextConfiguration {
        @Bean
        public MessageService mService() {
            return new MessageService();
        }
    }

    @Autowired
    private MessageService ms;

    @Test
    public void testExtractMessage() throws Exception {
        UUID uuid = UUID.randomUUID();

        String message = "~Test Message";

        String content = uuid.toString() + message;

        assertEquals("Test Message", ms.extractMessage(content));
    }

    @Test
    public void testExtractMessageEmpty() throws Exception {
        UUID uuid = UUID.randomUUID();

        String message = "~";

        String content = uuid.toString() + message;

        assertEquals("", ms.extractMessage(content));
    }

    @Test
    public void testExtractId() throws Exception {
        UUID uuid = UUID.randomUUID();

        String message = "~Test Message";

        String content = uuid.toString() + message;

        assertEquals(uuid, ms.extractUUID(content));
    }

    @Test
    public void testExtractIdEmpty() throws Exception {
        UUID uuid1 = UUID.randomUUID();

        String message = "~Test Message";

        String content = message;

        assertEquals(null, ms.extractUUID(content));
    }

}
