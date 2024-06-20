package com.usmobile.UsMobileTH.services;

import java.util.function.Consumer;

import javax.inject.Inject;

import com.usmobile.UsMobileTH.dtos.user.UserCreateRequest;
import com.usmobile.UsMobileTH.dtos.user.UserUpdatedRequest;
import com.usmobile.UsMobileTH.models.User;
import com.usmobile.UsMobileTH.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * UserService
 *
 * This class defines the business logic for working with the 'user' collection.
 */
@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    @Inject
    public UserService(final UserRepository repository, final PasswordEncoder encoder){
        this.repository = repository;
        this.encoder = encoder;
    }

    /**
     * This method creates a new user.
     * @param request the { first name, last name, email, password} of a user.
     * @return the created user document.
     */
    @Transactional
    public User createUser(final UserCreateRequest request) {
        final String encodePassword = encoder.encode(request.getPassword());
        return repository.save(User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodePassword).build());
    }

    /**
     * This method updates an existing user in the user collection.
     * @param request the { userId, firstname, last name, email} of a user to update.
     * @return the updated user document.
     */
    @Transactional
    public User updateUser(final UserUpdatedRequest request) {
        final User user = repository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User Not Found!"));
        final UserUpdater updater = new UserUpdater();
        updater.setIfNotNullAndNotSame(request.getEmail(),user.getEmail(), user::setEmail);
        updater.setIfNotNullAndNotSame(request.getFirstName(),user.getFirstName(), user::setFirstName);
        updater.setIfNotNullAndNotSame(request.getLastName(),user.getLastName(), user::setLastName);
        updater.setIfNotNullAndNotSame(request.getEmail(),user.getEmail(), user::setEmail);
        if (updater.hasUpdates){
            return repository.save(user);
        }
        return user;
    }

    /**
     * UserUpdater
     *
     * This class defines the logic for updating a user document.
     * It handles ignoring null and duplicate updates.
     */
    public static class UserUpdater {
        private boolean hasUpdates;
        /**
         * This method updates a user documents attribute if it is a valid update(i.e. is not duplicate and not null)
         * @param newValue the new value of the attribute.
         * @param oldValue the old value of the attribute.
         * @param setter the method used to set the new value.
         */
        public <T extends  Object> void setIfNotNullAndNotSame(T newValue, T oldValue, Consumer<T> setter) {
            if(newValue != null && !newValue.equals(oldValue)){
                this.hasUpdates = true;
                setter.accept(newValue);
            }
        }
    }


}
