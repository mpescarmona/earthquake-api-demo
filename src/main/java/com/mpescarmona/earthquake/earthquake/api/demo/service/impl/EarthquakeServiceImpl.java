package com.mpescarmona.earthquake.earthquake.api.demo.service.impl;

import com.mpescarmona.earthquake.earthquake.api.demo.domain.response.EarthquakeResponse;
import com.mpescarmona.earthquake.earthquake.api.demo.service.IEarthquakeService;

public class EarthquakeServiceImpl implements IEarthquakeService {
    @Override
    public EarthquakeResponse getEarthquakesByDateRanges(String startTime, String endTime) {
        return null;
    }

    @Override
    public EarthquakeResponse getEarthquakesByDateRanges(String startTimeRange1, String endTimeRange1, String startTimeRange2, String endTimeRange2) {
        return null;
    }

    @Override
    public EarthquakeResponse getEarthquakesByMagnitudeRanges(String minMagnitude, String maxMagnitude) {
        return null;
    }

    @Override
    public EarthquakeResponse getEarthquakesByCountry(String country) {
        return null;
    }

    @Override
    public EarthquakeResponse getEarthquakesByCountriesAndDateRanges(String countryONe, String countryTwo, String startTime, String endTime) {
        return null;
    }
}
