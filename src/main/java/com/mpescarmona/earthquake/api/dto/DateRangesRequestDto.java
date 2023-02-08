package com.mpescarmona.earthquake.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateRangesRequestDto {
    String startTime1;
    String endTime1;
    String startTime2;
    String endTime2;
}
