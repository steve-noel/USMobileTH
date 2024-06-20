package com.usmobile.UsMobileTH.dtos.cycle;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * CycleHistoryDTO
 *
 * This class defines the items inside the
 * {@link CycleHistoryResponse history} property.
 *
 */
@Setter
@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CycleHistoryDTO {

    private String cycleId;

    private Instant startDate;

    private Instant endDate;

}
