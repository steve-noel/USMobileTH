package com.usmobile.UsMobileTH.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * UserCreateRequest
 *
 * This class defines the input to the
 * {@link com.usmobile.UsMobileTH.controllers.UserController#updateUser(UserUpdatedRequest)} method.
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserUpdatedRequest {
    @NotNull
    private String userId;

    private String firstName;

    private String lastName;

    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

}
