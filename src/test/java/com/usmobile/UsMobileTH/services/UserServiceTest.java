package com.usmobile.UsMobileTH.services;

import static com.usmobile.UsMobileTH.utils.TestUtils.randomUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.usmobile.UsMobileTH.dtos.user.UserCreateRequest;
import com.usmobile.UsMobileTH.dtos.user.UserUpdatedRequest;
import com.usmobile.UsMobileTH.models.User;
import com.usmobile.UsMobileTH.repositories.UserRepository;
import com.usmobile.UsMobileTH.utils.TestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    private UserCreateRequest userCreateRequest;
    private UserUpdatedRequest userUpdatedRequest;
    private User existingUser;
    private User returneduUpdatedUser;

    @Test
    public void createNewUser() {
        givenAUserCreateRequest();
        givenMockPasswordEncoder();
        whenCreateANewUser();
        thenANewUserIsCreated();
    }

    @Test
    public void updateExistingUserAllFields() {
        givenAUserUpdateRequest();
        givenAnExistingUser();
        givenAUserIsUpdatedIntheDatabase();
        whenUpdateAUser();
        thenTheExistingUserIsUpdated();
    }

    @Test
    public void updateExistingUserNoFieldsUpdated() {
        givenAUserUpdateRequestAllNullFields();
        givenAnExistingUser();
        whenUpdateAUser();
        thenTheExistingUserIsNotUpdated();
    }

    @Test
    public void updateExistingUserEmailOnly() {
        givenAUserUpdateRequestEmailOnly();
        givenAnExistingUser();
        givenAUserIsUpdatedIntheDatabase();
        whenUpdateAUser();
        thenTheExistingUserEmailIsUpdated();
    }

    @Test
    public void updateUserNotFound() {
        assertThrows(RuntimeException.class, () -> {
            givenAUserUpdateRequest();
            whenUpdateAUser();
        });
    }

    public void givenMockPasswordEncoder(){
        when(encoder.encode(userCreateRequest.getPassword())).thenReturn(userCreateRequest.getPassword());
    }

    public void givenAnExistingUser(){
        existingUser = randomUser((user) -> user.setId(userUpdatedRequest.getUserId()));
        when(repository.findById(userUpdatedRequest.getUserId())).thenReturn(Optional.of(existingUser));
    }

    public void givenAUserIsUpdatedIntheDatabase(){
        when(repository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
    }


    public void givenAUserCreateRequest(){
        userCreateRequest = TestUtils.randomUserCreateRequest();
    }

    public void givenAUserUpdateRequest(){
        userUpdatedRequest = TestUtils.randomUserUpdateRequest();
    }

    public void givenAUserUpdateRequestAllNullFields(){
        userUpdatedRequest = TestUtils.randomUserUpdateRequest((up) -> {
            up.setEmail(null);
            up.setFirstName(null);
            up.setLastName(null);
        });
    }

    public void givenAUserUpdateRequestEmailOnly(){
        userUpdatedRequest = UserUpdatedRequest.builder()
                .userId(RandomStringUtils.randomAlphanumeric(10))
                .email(TestUtils.createRandomEmail()).build();
    }

    public void whenCreateANewUser(){
        service.createUser(userCreateRequest);
    }

    public void whenUpdateAUser(){
        returneduUpdatedUser = service.updateUser(userUpdatedRequest);
    }

    private void thenANewUserIsCreated() {
        verify(repository).save(User.builder()
                .firstName(userCreateRequest.getFirstName())
                .lastName(userCreateRequest.getLastName())
                .email(userCreateRequest.getEmail())
                .password(encoder.encode(userCreateRequest.getPassword())).build()
        );
    }

    private void thenTheExistingUserIsUpdated() {
        assertEquals(existingUser.getId(),returneduUpdatedUser.getId());
        assertEquals(userUpdatedRequest.getEmail(),returneduUpdatedUser.getEmail());
        assertEquals(userUpdatedRequest.getFirstName(),returneduUpdatedUser.getFirstName());
        assertEquals(userUpdatedRequest.getLastName(),returneduUpdatedUser.getLastName());
    }

    private void thenTheExistingUserIsNotUpdated() {
        assertEquals(existingUser.getId(),returneduUpdatedUser.getId());
        assertEquals(existingUser.getEmail(),returneduUpdatedUser.getEmail());
        assertEquals(existingUser.getFirstName(),returneduUpdatedUser.getFirstName());
        assertEquals(existingUser.getLastName(),returneduUpdatedUser.getLastName());
    }

    private void thenTheExistingUserEmailIsUpdated() {
        assertEquals(existingUser.getId(),returneduUpdatedUser.getId());
        assertEquals(userUpdatedRequest.getEmail(),returneduUpdatedUser.getEmail());
        assertEquals(existingUser.getFirstName(),returneduUpdatedUser.getFirstName());
        assertEquals(existingUser.getLastName(),returneduUpdatedUser.getLastName());
    }

}
