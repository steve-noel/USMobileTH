package com.usmobile.UsMobileTH.dtos.daily_usage;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * DailyUsageDTO
 *
 * This class defines the items inside the
 * {@link CurrentCycleDailyUsageResponse} dailyUsage property.
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DailyUsageDTO {
    @NotNull
    private Instant date;

    @NotNull
    private Integer dailyUsage;
}
