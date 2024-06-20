package com.usmobile.UsMobileTH.dtos.user;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * UserResponse
 *
 * This class defines the output of the
 * {@link com.usmobile.UsMobileTH.controllers.UserController#createANewUser(UserCreateRequest)} method.
 *
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserResponse {
    @NotNull
    private String id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private String email;

}
