package com.usmobile.UsMobileTH.controllers;

import javax.inject.Inject;

import com.usmobile.UsMobileTH.dtos.user.UserCreateRequest;
import com.usmobile.UsMobileTH.dtos.user.UserResponse;
import com.usmobile.UsMobileTH.dtos.user.UserUpdatedRequest;
import com.usmobile.UsMobileTH.models.User;
import com.usmobile.UsMobileTH.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * CycleController
 *
 * This class defines the REST API for the 'user' collection.
 * The API can be found at the "/user" URI.
 */
@RestController
@RequestMapping(value = "user")
@Slf4j
public class UserController {

    private final UserService service;

    @Inject
    public UserController(final UserService service){
        this.service = service;
    }

    /**
     * This method creates a new user in the user collection.
     * The URI for this method is '/user'
     * @param request the first name, last name, email and password of the user.
     * @return the created users  '{id, firstName, lastName, email}'
     */
    @RequestMapping(method = RequestMethod.POST)
    public UserResponse createANewUser(@RequestBody @Validated UserCreateRequest request) {
        log.info("Creating a new user {}",request);
        final User created = service.createUser(request);
        return toUserResponse(created);
    }

    /**
     * This method updates an existing user in the user collection.
     * The URI for this method is '/user'
     * @param request the userId, firstname, last name, email of the user.
     * @return the updated users '{id, firstName, lastName, email}'
     */
    @RequestMapping(method = RequestMethod.PUT)
    public UserResponse updateUser(@RequestBody @Validated UserUpdatedRequest request) {
        log.info("Updating user request {}",request);
        final User updated = service.updateUser(request);
        return toUserResponse(updated);
    }

    /**
     * This method converts a {@link User} object into a {@link UserResponse} for presentation purposes.
     * @param user the user document.
     * @return the UserResponse object.
     */
    private UserResponse toUserResponse(User user) {
        return  UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail()).build();
    }
}
