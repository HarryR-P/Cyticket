package com.kk08.CyTicketServer.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kk08.CyTicketServer.models.User;
import com.kk08.CyTicketServer.repos.UserRepository;
import com.kk08.CyTicketServer.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller used to control the Users
 *
 * @author robertb3@iastate.edu
 * @author Robert Brustkern
 */
//@RequestMapping("api/v1/user")
@Api(value = "UserController", description = "REST APIs for the User Entity")
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserService uService;

    @ApiOperation(value = "Attempts to register a new user in the system", response = String.class, tags = "Register User")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Not Authorized!"),
            @ApiResponse(code = 403, message = "Forbidden!"),
            @ApiResponse(code = 404, message = "Not Found!")
    })
    @RequestMapping(method = RequestMethod.POST, path = "/users/register")
    public String registerNewUser(User user) {
        switch (uService.userNameExists(user.getEmail(), user.getUserName())) {
            case 1:
                return "Error: Email already taken";
            case 2:
                return "Error: Username already taken";
        }

        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        return "New User has been registered";
    }

    @ApiOperation(value = "Login to a specific user and return that user ", response = User.class, tags = "Login User")
    @RequestMapping(method = RequestMethod.GET, path = "/users/login")
    public List<User> loginUser(@RequestParam("userName")String userName, @RequestParam("password")String password) {
        List<User> potentials = userRepository.findByUserName(userName);
        if (potentials.isEmpty()) {
            System.out.println("Username bad");
            return null;
        }

        String encodedPassword = encoder.encode(password);

        for (User u : potentials) {
            if (encoder.matches(password, u.getPassword())) {
                return potentials;
            }
        }

        System.out.println("password bad");
        return null;
    }

    @ApiOperation(value = "Add a user in a less formal way ", response = String.class, tags = "Add User")
    @RequestMapping(method = RequestMethod.POST, path = "/users/add")
    public String addUser(User user) {
        userRepository.save(user);
        return "New User " + user.getUserName() + " Saved";
    }

    @ApiOperation(value = "Returns all stored users in the system ", response = User.class, tags = "Get Users")
    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<User> getAllUsers() {
        List<User> results = userRepository.findAll();
        return results;
    }

    // Gets user by rowId
    @ApiOperation(value = "Get a specific user based on row id in database ", response = User.class, tags = "Get Users")
    @RequestMapping(method = RequestMethod.GET, path = "/users/{rowId}")
    public Optional<User> findUserById(@PathVariable("rowId") Integer id) {
        Optional<User> results = userRepository.findById(id);
        return results;
    }

    // Gets the user by the UUID
    @ApiOperation(value = "Get a specific user based on the UUID ", response = User.class, tags = "Get Users")
    @RequestMapping(method = RequestMethod.GET, path = "/users/id/{userId}")
    public List<User> findUserByUUID(@PathVariable("userId") UUID id) {
        List<User> results = userRepository.findByUUID(id);
        return results;
    }

    //Gets the user by the UserName
    @ApiOperation(value = "Get a specific user based on the username ", response = User.class, tags = "Get Users")
    @RequestMapping(method = RequestMethod.GET, path = "/users/username/{userName}")
    public List<User> findUserByName(@PathVariable("userName") String userName) {
        List<User> results = userRepository.findByUserName(userName);
        return results;
    }

    // Updates the user information based on UUID
    @ApiOperation(value = "Update a current user with new user information ", response = Integer.class, tags = "Update Users")
    @ApiResponses(value = {
            @ApiResponse(code = 0, message = "User Not Found"),
            @ApiResponse(code = 1, message = "User updated")
    })
    @RequestMapping(method = RequestMethod.PUT, path = "/users/update/{userId}")
    public int updateUser(@PathVariable("userId") UUID id, @RequestParam(required = false, name = "firstName")String first,
                          @RequestParam(required = false, name = "lastName")String last, @RequestParam(required = false, name = "userName")String userName, @RequestParam(required = false, name = "email")String email) {
        List<User> users = userRepository.findByUUID(id);

        if (users.isEmpty()) {
            return 0;
        } else {
            // Simple Test of the update
            User updateUser = users.get(0);

            if (first != null) { updateUser.setFirstName(first); }
            if (last != null) { updateUser.setLastName(last); }
            if (userName != null) { updateUser.setUserName(userName); }
            if (email != null) { updateUser.setEmail(email);}

            userRepository.save(updateUser);
        }

        return 1;
    }

    // Deletes the user information based on UUID
    @ApiOperation(value = "Delete a current user in the database ", response = Integer.class, tags = "Delete Users")
    @ApiResponses(value = {
            @ApiResponse(code = 1, message = "Deletion Successfull")
    })
    @RequestMapping(method = RequestMethod.DELETE, path = "/users/delete/{userId}")
    public int deleteUser(@PathVariable("userId") UUID id) {
        List<User> users = userRepository.findByUUID(id);

        if (users.isEmpty()) {
            return 1;
        }

        userRepository.delete(users.get(0));
        return 1;
    }

}
