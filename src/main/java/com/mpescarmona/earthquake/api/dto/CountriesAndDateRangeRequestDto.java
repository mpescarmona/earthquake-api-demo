package com.mpescarmona.earthquake.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountriesAndDateRangeRequestDto {
    String countryOne;
    String countryTwo;
    String startTime;
    String endTime;
}
