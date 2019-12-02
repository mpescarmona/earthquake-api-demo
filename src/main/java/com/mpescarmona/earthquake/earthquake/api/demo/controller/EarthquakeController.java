package com.mpescarmona.earthquake.earthquake.api.demo.controller;

import com.mpescarmona.earthquake.earthquake.api.demo.domain.response.EarthquakeResponse;
import com.mpescarmona.earthquake.earthquake.api.demo.service.IEarthquakeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "HackerNews Controller")
@Slf4j
@RestController
@AllArgsConstructor
public class EarthquakeController {

    private IEarthquakeService earthquakeService;

    /**
     * Retrieves the earthquakes between a date range
     *
     * @param startTime The start date to get the earthquakes
     * @param endTime   The end date to get the earthquakes
     * @return A response containing the EarthquakeResponse {@link EarthquakeResponse}
     */
    @ApiOperation(value = "Get all the earthquakes between a date range")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all the earthquakes in the specified date range")})
    @GetMapping(path = "/daterange",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEarthquakesByDateRange(@RequestParam String startTime, @RequestParam String endTime) {
        log.info("action=getEarthquakesByDateRange");
        EarthquakeResponse response = earthquakeService.getEarthquakesByDateRange(startTime, endTime);
        log.info("action=getEarthquakesByDateRange, earthquakes={}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves the earthquakes between two date ranges
     *
     * @param startTime1 The start date of first range to get the earthquakes
     * @param endTime1   The end date of first range to get the earthquakes
     * @param startTime2 The start date of second range to get the earthquakes
     * @param endTime2   The end date of second range to get the earthquakes
     * @return A response containing the EarthquakeResponse {@link EarthquakeResponse}
     */
    @ApiOperation(value = "Get all the earthquakes between two date ranges")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all the earthquakes in the two specified date ranges")})
    @GetMapping(path = "/dateranges",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEarthquakesByDateRanges(@RequestParam String startTime1, @RequestParam String endTime1,
                                                        @RequestParam String startTime2, @RequestParam String endTime2) {
        log.info("action=getEarthquakesByDateRanges");
        EarthquakeResponse response = earthquakeService.getEarthquakesByDateRanges(startTime1, endTime1, startTime2, endTime2);
        log.info("action=getEarthquakesByDateRanges, earthquakes={}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves the earthquakes between a magnitude range
     *
     * @param minMagnitude The minimum magnitude to get the earthquakes
     * @param maxMagnitude The maximum magnitude to get the earthquakes
     * @return A response containing the EarthquakeResponse {@link EarthquakeResponse}
     */
    @ApiOperation(value = "Get all the earthquakes between two magnitude values")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all the earthquakes in the two specified magnitude values")})
    @GetMapping(path = "/magnituderange",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEarthquakesByMagnitudeRange(@RequestParam String minMagnitude, @RequestParam String maxMagnitude) {
        log.info("action=getEarthquakesByMagnitudeRange");
        EarthquakeResponse response = earthquakeService.getEarthquakesByMagnitudeRange(minMagnitude, maxMagnitude);
        log.info("action=getEarthquakesByMagnitudeRange, earthquakes={}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves the earthquakes occurred in the given country
     *
     * @param country The country to get the earthquakes
     * @return A response containing the EarthquakeResponse {@link EarthquakeResponse}
     */
    @ApiOperation(value = "Get all the earthquakes in the specified country")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all the earthquakes in the specified country")})
    @GetMapping(path = "/country",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEarthquakesByCountry(@RequestParam String country) {
        log.info("action=getEarthquakesByCountry");
        EarthquakeResponse response = earthquakeService.getEarthquakesByCountry(country);
        log.info("action=getEarthquakesByCountry, earthquakes={}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves the earthquakes occurred in the two given countries and between a date range
     *
     * @param countryOne The first country to get the earthquakes
     * @param countryTwo The second country to get the earthquakes
     * @param startTime  The first date to get the earthquakes
     * @param endTime    The first date to get the earthquakes
     * @return A response containing the EarthquakeResponse {@link EarthquakeResponse}
     */
    @ApiOperation(value = "Get all the earthquakes in the specified countries and between a date range")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all the earthquakes in the specified countries and between a date range")})
    @GetMapping(path = "/countries",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEarthquakesByCountriesAndDateRange(@RequestParam String countryOne, @RequestParam String countryTwo, @RequestParam String startTime, @RequestParam String endTime) {
        log.info("action=getEarthquakesByCountriesAndDateRange");
        EarthquakeResponse response = earthquakeService.getEarthquakesByCountriesAndDateRange(countryOne, countryTwo, startTime, endTime);
        log.info("action=getEarthquakesByCountriesAndDateRange, earthquakes={}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
