package com.mpescarmona.earthquake.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateAndMagnitudeRangesByCountryRequestDto {
    String startTime;
    String endTime;
    String minMagnitude;
    String maxMagnitude;
    String country;
}
