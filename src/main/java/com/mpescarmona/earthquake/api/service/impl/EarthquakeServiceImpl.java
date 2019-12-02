package com.mpescarmona.earthquake.api.service.impl;

import com.mpescarmona.earthquake.api.domain.Feature;
import com.mpescarmona.earthquake.api.domain.response.EarthquakeResponse;
import com.mpescarmona.earthquake.api.helper.EarthquakeUrlHelper;
import com.mpescarmona.earthquake.api.service.IEarthquakeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@NoArgsConstructor
@AllArgsConstructor
public class EarthquakeServiceImpl implements IEarthquakeService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private EarthquakeUrlHelper earthquakeUrlHelper;

    @Override
    public EarthquakeResponse getEarthquakesByDateRange(String startTime, String endTime) {
        log.info("action=getEarthquakesByDateRange, startTime={}, endTime={}", startTime, endTime);
        EarthquakeResponse earthquakeResponse = null;
        try {
            String url = earthquakeUrlHelper.buildEarthquakeUrlByDates(startTime, endTime);
            log.info("action=getEarthquakesByDateRange, url={}", url);

            ResponseEntity<EarthquakeResponse> response = callEarthquakeService(url);
            if (response.getStatusCode().is2xxSuccessful()) {
                earthquakeResponse = response.getBody();
            }

            log.info("action=getEarthquakesByDateRange, result={}", response);
        } catch (Exception ex) {
            log.error("action=getEarthquakesByDateRange, error={}", ex.getMessage());
        }
        return earthquakeResponse;
    }

    @Override
    public EarthquakeResponse getEarthquakesByDateRanges(String startTimeRange1, String endTimeRange1,
                                                         String startTimeRange2, String endTimeRange2) {
        log.info("action=getEarthquakesByDateRanges, startTimeRange1={}, endTimeRange1={}, startTimeRange2={}, " +
                "endTimeRange2={}", startTimeRange1, endTimeRange1, startTimeRange2, endTimeRange2);
        EarthquakeResponse responseRangeOne = getEarthquakesByDateRange(startTimeRange1, endTimeRange1);
        EarthquakeResponse responseRangeTwo = getEarthquakesByDateRange(startTimeRange2, endTimeRange2);

        List<Feature> allFeatures = new ArrayList<>(responseRangeOne.getFeatures());
        allFeatures.addAll(responseRangeTwo.getFeatures());
        responseRangeOne.setFeatures(allFeatures);
        Integer total = responseRangeOne.getMetadata().getCount() + responseRangeTwo.getMetadata().getCount();
        responseRangeOne.getMetadata().setCount(total);

        log.info("action=getEarthquakesByDateRanges, result={}", responseRangeOne);
        return responseRangeOne;
    }

    @Override
    public EarthquakeResponse getEarthquakesByMagnitudeRange(String minMagnitude, String maxMagnitude) {
        log.info("action=getEarthquakesByMagnitudeRanges, minMagnitude={}, maxMagnitude={}", minMagnitude, maxMagnitude);
        String url = earthquakeUrlHelper.buildEarthquakeUrlByMagnitudes(minMagnitude, maxMagnitude);
        log.info("action=getEarthquakesByMagnitudeRanges, url={}", url);
        EarthquakeResponse earthquakeResponse = null;
        try {
            ResponseEntity<EarthquakeResponse> response = callEarthquakeService(url);

            if (response.getStatusCode().is2xxSuccessful()) {
                earthquakeResponse = response.getBody();
            }

            log.info("action=getEarthquakesByMagnitudeRanges, result={}", response);
        } catch (Exception ex) {
            log.error("action=getEarthquakesByMagnitudeRanges, error={}", ex.getMessage());
        }
        return earthquakeResponse;
    }

    @Override
    public EarthquakeResponse getEarthquakesByCountry(String country) {
        log.info("action=getEarthquakesByCountry, country={}", country);
        String url = earthquakeUrlHelper.buildEarthQuakeBaseUrlAndFormat();
        log.info("action=getEarthquakesByCountry, url={}", url);
        EarthquakeResponse earthquakeResponse = null;
        try {
            ResponseEntity<EarthquakeResponse> response = callEarthquakeService(url);

            if (response.getStatusCode().is2xxSuccessful()) {
                earthquakeResponse = response.getBody();
            }

            log.info("action=getEarthquakesByCountry, result={}", response);
        } catch (Exception ex) {
            log.error("action=getEarthquakesByCountry, error={}", ex.getMessage());
        }
        List<Feature> filteredFeatures = earthquakeResponse.getFeatures().stream()
                .filter(feature -> feature.getProperties().getPlace().toLowerCase().contains(country.toLowerCase()))
                .collect(Collectors.toList());

        earthquakeResponse.setFeatures(filteredFeatures);
        earthquakeResponse.getMetadata().setCount(filteredFeatures.size());

        return earthquakeResponse;
    }

    @Override
    public EarthquakeResponse getEarthquakesByCountriesAndDateRange(String countryOne, String countryTwo, String startTime, String endTime) {
        log.info("action=getEarthquakesByCountriesAndDateRange, countryOne={}, countryTwo={}, startTime={}, endTime={}", countryOne, countryTwo, startTime, endTime);
        EarthquakeResponse response = getEarthquakesByDateRange(startTime, endTime);

        List<Feature> filteredFeatures = response.getFeatures().stream()
                .filter(feature -> feature.getProperties().getPlace().toLowerCase().contains(countryOne.toLowerCase()) ||
                        feature.getProperties().getPlace().toLowerCase().contains(countryTwo.toLowerCase()))
                .collect(Collectors.toList());

        response.setFeatures(filteredFeatures);
        response.getMetadata().setCount(filteredFeatures.size());

        log.info("action=getEarthquakesByCountriesAndDateRange, result={}", response);
        return response;
    }

    private HttpEntity<String> buildEarthquakeRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return new HttpEntity<>(headers);
    }

    private ResponseEntity<EarthquakeResponse> callEarthquakeService(String url) {
        HttpEntity<String> entity = buildEarthquakeRequestHeader();
        return restTemplate.exchange(url, HttpMethod.GET, entity, EarthquakeResponse.class);
    }
}
