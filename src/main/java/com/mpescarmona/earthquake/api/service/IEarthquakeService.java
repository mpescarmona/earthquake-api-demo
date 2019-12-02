package com.mpescarmona.earthquake.api.service;

import com.mpescarmona.earthquake.api.domain.response.EarthquakeResponse;

public interface IEarthquakeService {
    /**
     * Gets earthquakes by date range
     *
     * @param startTime The start date of range
     * @param endTime   The end date of range
     * @return The earthquake response containing the earthquakes. @See {@link EarthquakeResponse}
     */
    EarthquakeResponse getEarthquakesByDateRange(String startTime, String endTime);

    /**
     * Gets earthquakes by two date ranges
     *
     * @param startTimeRange1 The start date of first range
     * @param endTimeRange1   The end date of first range
     * @param startTimeRange2 The start date of second range
     * @param endTimeRange2   The end date of second range
     * @return The earthquake response containing the earthquakes. @See {@link EarthquakeResponse}
     */
    EarthquakeResponse getEarthquakesByDateRanges(String startTimeRange1, String endTimeRange1,
                                                  String startTimeRange2, String endTimeRange2);

    /**
     * Gets earthquakes by magnitude range
     *
     * @param minMagnitude The first magnitude of range
     * @param maxMagnitude The second magnitude of range
     * @return The earthquake response containing the earthquakes. @See {@link EarthquakeResponse}
     */
    EarthquakeResponse getEarthquakesByMagnitudeRange(String minMagnitude, String maxMagnitude);

    /**
     * Gets earthquakes by country
     *
     * @param country The country
     * @return The earthquake response containing the earthquakes. @See {@link EarthquakeResponse}
     */
    EarthquakeResponse getEarthquakesByCountry(String country);

    /**
     * Gets earthquakes occurred in the given countries and between the date range
     *
     * @param countryOne The first country
     * @param countryTwo The second country
     * @param startTime  The start date of range
     * @param endTime    The end date of range
     * @return The earthquake response containing the earthquakes. @See {@link EarthquakeResponse}
     */
    EarthquakeResponse getEarthquakesByCountriesAndDateRange(String countryOne, String countryTwo,
                                                             String startTime, String endTime);
}
