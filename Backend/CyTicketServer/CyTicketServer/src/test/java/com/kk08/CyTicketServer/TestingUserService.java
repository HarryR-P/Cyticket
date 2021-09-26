package com.kk08.CyTicketServer;

import com.kk08.CyTicketServer.models.User;
import com.kk08.CyTicketServer.repos.UserRepository;
import com.kk08.CyTicketServer.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = CyTicketServerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class TestingUserService {

    @TestConfiguration
    static class UserContextConfiguration {

        @Bean
        public UserService uService() {
            return new UserService();
        }

      @Bean
      UserRepository getRepo() { return mock(UserRepository.class); }

    }

    @Autowired
    private UserService us;

    @Autowired
    private UserRepository repo;

    @Test
    public void testUserNameExistsCase1() throws Exception {
        List<User> l = new ArrayList<>();
        User test = new User();
        test.setUserName("Test");
        test.setPassword("Test");
        test.setEmail("Test@email.com");
        test.setFirstName("First");
        test.setLastName("Last");

        l.add(test);

        when(repo.findAll()).thenReturn(l);
        when(repo.findByUserName((String)any(String.class))).thenReturn(l);
        when(repo.findByEmail((String)any(String.class))).thenReturn(l);

        assertEquals(1, us.userNameExists("Test", "Test@email.com"));
    }

    @Test
    public void testUserNameExistsCase2() throws Exception {
        List<User> l = new ArrayList<>();
        User test = new User();
        test.setUserName("Different");
        test.setPassword("Test");
        test.setEmail("Test@email.com");
        test.setFirstName("First");
        test.setLastName("Last");

        l.add(test);

        when(repo.findAll()).thenReturn(l);
        when(repo.findByUserName((String)any(String.class))).thenReturn(l);
        when(repo.findByEmail((String)any(String.class))).thenReturn(new ArrayList<>());

        assertEquals(2, us.userNameExists("Different", "diff@email.com"));
    }

    @Test
    public void testUserNameExistsCase3() throws Exception {
        List<User> l = new ArrayList<>();
        User test = new User();
        test.setUserName("Different");
        test.setPassword("Test");
        test.setEmail("Test@email.com");
        test.setFirstName("First");
        test.setLastName("Last");

        l.add(test);

        when(repo.findAll()).thenReturn(l);
        when(repo.findByUserName((String)any(String.class))).thenReturn(new ArrayList<>());
        when(repo.findByEmail((String)any(String.class))).thenReturn(new ArrayList<>());

        assertEquals(0, us.userNameExists("Completely", "unique@email.com"));
    }


}
