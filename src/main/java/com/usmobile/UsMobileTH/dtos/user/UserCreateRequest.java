package com.usmobile.UsMobileTH.dtos.user;

import com.usmobile.UsMobileTH.dtos.daily_usage.CurrentCycleDailyUsageResponse;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
 * {@link com.usmobile.UsMobileTH.controllers.UserController#createANewUser(UserCreateRequest)} method.
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreateRequest {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;
}
