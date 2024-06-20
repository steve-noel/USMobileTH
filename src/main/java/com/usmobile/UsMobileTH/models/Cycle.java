package com.usmobile.UsMobileTH.models;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Cycle
 *
 * This class defines the structure of a document in the 'cycle' collection.
 */
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Document
@ToString
public class Cycle {

    @Id
    private String id;

    @NotNull
    private String mdn;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    @NotNull
    private String userId;
}
