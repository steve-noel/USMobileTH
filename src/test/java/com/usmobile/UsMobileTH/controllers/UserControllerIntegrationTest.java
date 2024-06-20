package com.usmobile.UsMobileTH.controllers;

import static com.usmobile.UsMobileTH.utils.TestUtils.randomUserCreateRequest;
import static com.usmobile.UsMobileTH.utils.TestUtils.randomUserUpdateRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.function.Consumer;

import com.usmobile.UsMobileTH.dtos.user.UserCreateRequest;
import com.usmobile.UsMobileTH.dtos.user.UserResponse;
import com.usmobile.UsMobileTH.dtos.user.UserUpdatedRequest;
import com.usmobile.UsMobileTH.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UserRepository repository;

    private UserCreateRequest newUserRequest;
    private UserUpdatedRequest userUpdatedRequest;
    private ResponseEntity<UserResponse> returnedUserCreateResponse;
    private ResponseEntity<UserResponse> returnedUserUpdateResponse;

    @Test
    public void shouldCreateANewUser(){
        givenANewUserRequest();
        whenCreateNewUser();
        thenTheCorrectNewUserIsCreated();
    }

    @Test
    public void shouldUpdateAnExistingUser(){
        givenANewUserRequest();
        whenCreateNewUser();
        thenTheCorrectNewUserIsCreated();

        givenUpdateUserRequest((ur) -> ur.setUserId(returnedUserCreateResponse.getBody().getId()));
        whenUpdateUser();
        thenUserIsUpdated();
    }

    @Test
    public void updateUserNotFound(){
        givenUpdateUserRequest((usr) -> {});
        whenUpdateUser();
    }

    private void givenUpdateUserRequest(final Consumer<UserUpdatedRequest> consumer) {
        userUpdatedRequest = randomUserUpdateRequest(consumer);
    }

    private void givenANewUserRequest() {
        newUserRequest = randomUserCreateRequest();
    }

    private void whenCreateNewUser() {
        HttpEntity<UserCreateRequest> body = new HttpEntity<>(newUserRequest);
        returnedUserCreateResponse = testRestTemplate
                .exchange("/user", HttpMethod.POST, body, UserResponse.class);
    }

    private void whenUpdateUser() {
        HttpEntity<UserUpdatedRequest> body = new HttpEntity<>(userUpdatedRequest);
        returnedUserUpdateResponse = testRestTemplate
                .exchange("/user", HttpMethod.PUT, body, UserResponse.class);
    }

    private void thenTheCorrectNewUserIsCreated() {
        assertNotNull(returnedUserCreateResponse);
        assertTrue(returnedUserCreateResponse.getStatusCode().is2xxSuccessful());
        final UserResponse response = returnedUserCreateResponse.getBody();
        assertNotNull(response);

        assertEquals(newUserRequest.getEmail(),response.getEmail());
        assertEquals(newUserRequest.getFirstName(),response.getFirstName());
        assertEquals(newUserRequest.getLastName(),response.getLastName());

    }

    private void thenUserIsUpdated() {
        assertNotNull(returnedUserUpdateResponse);
        assertTrue(returnedUserUpdateResponse.getStatusCode().is2xxSuccessful());
        final UserResponse response = returnedUserUpdateResponse.getBody();
        assertNotNull(response);

        assertEquals(userUpdatedRequest.getEmail(),response.getEmail());
        assertEquals(userUpdatedRequest.getFirstName(),response.getFirstName());
        assertEquals(userUpdatedRequest.getLastName(),response.getLastName());
    }
}
