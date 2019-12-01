package com.mpescarmona.earthquake.earthquake.api.demo.service;

import com.mpescarmona.earthquake.earthquake.api.demo.domain.response.EarthquakeResponse;

public interface IEarthquakeService {
    EarthquakeResponse getEarthquakesByDateRange(String startTime, String endTime);

    EarthquakeResponse getEarthquakesByDateRanges(String startTimeRange1, String endTimeRange1,
                                                  String startTimeRange2, String endTimeRange2);
    EarthquakeResponse getEarthquakesByMagnitudeRanges(String minMagnitude, String maxMagnitude);
    EarthquakeResponse getEarthquakesByCountry(String country);
    EarthquakeResponse getEarthquakesByCountriesAndDateRange(String countryOne, String countryTwo,
                                                             String startTime, String endTime);
}
