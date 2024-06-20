package com.usmobile.UsMobileTH.dtos.cycle;

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
 * CycleHistoryResponse
 *
 * This class defines the response returned from the
 * {@link com.usmobile.UsMobileTH.controllers.CycleController#getCycleHistory(String, String)} () UserCycleHistory} method.
 *
 */
@Setter
@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CycleHistoryResponse {
    @NotNull
    private List<CycleHistoryDTO> history;
}
