package com.usmobile.UsMobileTH.dtos.daily_usage;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/**
 * CurrentCycleDailyUsageResponse
 *
 * This class defines the response returned from the
 * {@link com.usmobile.UsMobileTH.controllers.DailyUsageController#getCurrentCycleDailyUsage(String, String) UserCycleHistory} method.
 *
 */
@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CurrentCycleDailyUsageResponse {
    @NotNull
    private List<DailyUsageDTO> dailyUsage;
}
