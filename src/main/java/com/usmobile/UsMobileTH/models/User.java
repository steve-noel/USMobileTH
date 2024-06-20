package com.usmobile.UsMobileTH.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * User
 *
 * This class defines the structure of a document in the 'user' collection.
 */
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Document
@ToString
public class User {
    @Id
    private String id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Indexed(unique = true)
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;

    @NotNull
    @Size(min = 8)
    private String password;

}

