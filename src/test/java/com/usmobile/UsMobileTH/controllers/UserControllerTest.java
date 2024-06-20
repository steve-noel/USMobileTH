package com.usmobile.UsMobileTH.controllers;

import static com.usmobile.UsMobileTH.utils.TestUtils.randomUser;
import static com.usmobile.UsMobileTH.utils.TestUtils.randomUserCreateRequest;
import static com.usmobile.UsMobileTH.utils.TestUtils.randomUserUpdateRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.usmobile.UsMobileTH.dtos.user.UserCreateRequest;
import com.usmobile.UsMobileTH.dtos.user.UserResponse;
import com.usmobile.UsMobileTH.dtos.user.UserUpdatedRequest;
import com.usmobile.UsMobileTH.models.User;
import com.usmobile.UsMobileTH.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private UserController controller;

    private User userCreated;
    private UserResponse createdUserResponse;
    private User userUpdated;
    private UserResponse updatedUserResponse;

    @Test
    public void createANewUser() {
        givenUserServiceCreatesANewUSer();
        whenCreateANewUser();
        thenAUserResponseIsReturned();
    }

    @Test
    public void updateUser() {
        givenUserServiceUpdatesAUser();
        whenUpdateAUser();
        thenAUserResponseIsReturnedForUpdate();
    }

    public void givenUserServiceCreatesANewUSer(){
        userCreated = randomUser();
        when(service.createUser(any(UserCreateRequest.class))).thenReturn(userCreated);
    }

    public void givenUserServiceUpdatesAUser(){
        userUpdated = randomUser();
        when(service.updateUser(any(UserUpdatedRequest.class))).thenReturn(userUpdated);
    }

    public void whenCreateANewUser(){
        createdUserResponse = controller.createANewUser(randomUserCreateRequest());
    }

    public void whenUpdateAUser(){
        updatedUserResponse = controller.updateUser(randomUserUpdateRequest());
    }

    private void thenAUserResponseIsReturned() {
        assertEquals(UserResponse.builder()
                .id(userCreated.getId())
                .firstName(userCreated.getFirstName())
                .lastName(userCreated.getLastName())
                .email(userCreated.getEmail()).build(), createdUserResponse);
    }

    private void thenAUserResponseIsReturnedForUpdate() {
        assertEquals(UserResponse.builder()
                .id(userUpdated.getId())
                .firstName(userUpdated.getFirstName())
                .lastName(userUpdated.getLastName())
                .email(userUpdated.getEmail()).build(), updatedUserResponse);
    }
}
