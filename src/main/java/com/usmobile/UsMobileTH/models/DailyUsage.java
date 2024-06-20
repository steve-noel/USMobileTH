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
 * DailyUsage
 *
 * This class defines the structure of a document in the 'daily_usage' collection.
 */
@Getter
@Setter
@EqualsAndHashCode
@Builder
@Document(collection = "daily_usage")
@ToString
public class DailyUsage {

    @Id
    private String id;

    @NotNull
    private String mdn;

    @NotNull
    private String userId;

    @NotNull
    private Instant usageDate;

    @NotNull
    private Integer usedInMb;
}
