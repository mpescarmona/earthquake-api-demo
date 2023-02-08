package com.mpescarmona.earthquake.api.controller;

import com.mpescarmona.earthquake.api.domain.response.EarthquakeResponse;
import com.mpescarmona.earthquake.api.dto.CountriesAndDateRangeRequestDto;
import com.mpescarmona.earthquake.api.dto.CountryRequestDto;
import com.mpescarmona.earthquake.api.dto.DateAndMagnitudeRangesByCountryRequestDto;
import com.mpescarmona.earthquake.api.dto.DateRangeRequestDto;
import com.mpescarmona.earthquake.api.dto.DateRangesRequestDto;
import com.mpescarmona.earthquake.api.dto.MagnitudeRangeRequestDto;
import com.mpescarmona.earthquake.api.service.IEarthquakeService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Earthquake Controller")
@Slf4j
@RestController
@AllArgsConstructor
public class EarthquakeController {

    private IEarthquakeService earthquakeService;

    /**
     * Retrieves the earthquakes between a date range
     *
     * @param dateRangeRequestDto The start and end date ranges to get the earthquakes
     * @return A response containing the EarthquakeResponse {@link EarthquakeResponse}
     */
    @ApiOperation(value = "Get all the earthquakes between a date range")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all the earthquakes in the specified date range")})
    @GetMapping(path = "/daterange",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEarthquakesByDateRange(@RequestBody DateRangeRequestDto dateRangeRequestDto) {
        log.info("action=getEarthquakesByDateRange");
        EarthquakeResponse response = earthquakeService.getEarthquakesByDateRange(
                dateRangeRequestDto.getStartTime(),
                dateRangeRequestDto.getEndTime());
        log.info("action=getEarthquakesByDateRange, earthquakes={}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves the earthquakes between two date ranges
     *
     * @param dateRangesRequestDto The start and end dates of first and second  date ranges
     * @return A response containing the EarthquakeResponse {@link EarthquakeResponse}
     */
    @ApiOperation(value = "Get all the earthquakes between two date ranges")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all the earthquakes in the two specified date ranges")})
    @GetMapping(path = "/dateranges",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEarthquakesByDateRanges(@RequestBody DateRangesRequestDto dateRangesRequestDto) {
        log.info("action=getEarthquakesByDateRanges");
        EarthquakeResponse response = earthquakeService.getEarthquakesByDateRanges(
                dateRangesRequestDto.getStartTime1(),
                dateRangesRequestDto.getEndTime1(),
                dateRangesRequestDto.getStartTime2(),
                dateRangesRequestDto.getEndTime2());
        log.info("action=getEarthquakesByDateRanges, earthquakes={}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves the earthquakes between a magnitude range
     *
     * @param magnitudeRangeRequestDto The minimum and maximum magnitude range to get the earthquakes
     * @return A response containing the EarthquakeResponse {@link EarthquakeResponse}
     */
    @ApiOperation(value = "Get all the earthquakes between two magnitude values")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all the earthquakes in the two specified magnitude values")})
    @GetMapping(path = "/magnituderange",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEarthquakesByMagnitudeRange(@RequestBody MagnitudeRangeRequestDto magnitudeRangeRequestDto) {
        log.info("action=getEarthquakesByMagnitudeRange");
        EarthquakeResponse response = earthquakeService.getEarthquakesByMagnitudeRange(
                magnitudeRangeRequestDto.getMinMagnitude(),
                magnitudeRangeRequestDto.getMaxMagnitude());
        log.info("action=getEarthquakesByMagnitudeRange, earthquakes={}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves the earthquakes occurred in the given country
     *
     * @param countryRequestDto The country to get the earthquakes
     * @return A response containing the EarthquakeResponse {@link EarthquakeResponse}
     */
    @ApiOperation(value = "Get all the earthquakes in the specified country")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all the earthquakes in the specified country")})
    @GetMapping(path = "/country",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEarthquakesByCountry(@RequestBody CountryRequestDto countryRequestDto) {
        log.info("action=getEarthquakesByCountry");
        EarthquakeResponse response = earthquakeService.getEarthquakesByCountry(countryRequestDto.getCountry());
        log.info("action=getEarthquakesByCountry, earthquakes={}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves the earthquakes occurred in the two given countries and between a date range
     *
     * @param countriesAndDateRangeRequestDto The first country and second country and the date ranges to get the earthquakes
     * @return A response containing the EarthquakeResponse {@link EarthquakeResponse}
     */
    @ApiOperation(value = "Get all the earthquakes in the specified countries and between a date range")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all the earthquakes in the specified countries and between a date range")})
    @GetMapping(path = "/countries",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEarthquakesByCountriesAndDateRange(
            @RequestBody CountriesAndDateRangeRequestDto countriesAndDateRangeRequestDto) {
        log.info("action=getEarthquakesByCountriesAndDateRange");
        EarthquakeResponse response = earthquakeService.getEarthquakesByCountriesAndDateRange(
                countriesAndDateRangeRequestDto.getCountryOne(),
                countriesAndDateRangeRequestDto.getCountryTwo(),
                countriesAndDateRangeRequestDto.getStartTime(),
                countriesAndDateRangeRequestDto.getEndTime());
        log.info("action=getEarthquakesByCountriesAndDateRange, earthquakes={}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Retrieves the earthquakes between a date range, a magnitude range, and coming from a country
     *
     * @param dateAndMagnitudeRangesByCountryRequestDto The date range and magnitude range by country to get the earthquakes
     * @return A response containing the EarthquakeResponse {@link EarthquakeResponse}
     */
    @ApiOperation(value = "Get all the earthquakes between a date range, a magnitude range, and coming from a country")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Get all the earthquakes in the specified date range, magnitude range, and country")})
    @GetMapping(path = "/daterange/magnituderange/country",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEarthquakesByDateRangeAndMagnitudeRangeByCountry(
            @RequestBody DateAndMagnitudeRangesByCountryRequestDto dateAndMagnitudeRangesByCountryRequestDto) {
        log.info("action=getEarthquakesByDateRangeAndMagnitudeRangeAndCountry");
        EarthquakeResponse response = earthquakeService.getEarthquakesByDateRangeAndMagnitudeRangeAndCountry(
                dateAndMagnitudeRangesByCountryRequestDto.getStartTime(),
                dateAndMagnitudeRangesByCountryRequestDto.getEndTime(),
                dateAndMagnitudeRangesByCountryRequestDto.getMinMagnitude(),
                dateAndMagnitudeRangesByCountryRequestDto.getMaxMagnitude(),
                dateAndMagnitudeRangesByCountryRequestDto.getCountry());
        log.info("action=getEarthquakesByDateRangeAndMagnitudeRangeAndCountry, earthquakes={}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
