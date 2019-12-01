package com.mpescarmona.earthquake.earthquake.api.demo.helper;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class EarthquakeUrlHelper {
    @Value("${earthquake.baseUrl}")
    private String earthquakeBaseUrl;
    @Value("${earthquake.format}")
    private String earthquakeFormat;
    @Value("${earthquake.searchByDatesStartTime}")
    private String searchByDatesStartTime;
    @Value("${earthquake.searchByDatesEndTime}")
    private String searchByDatesEndTime;
    @Value("${earthquake.searchByMagnitudesMinMagnitude}")
    private String searchByMagnitudesMinMagnitude;
    @Value("${earthquake.searchByMagnitudesMaxMagnitude}")
    private String searchByMagnitudesMaxMagnitude;

    private StringBuilder getEarthQuakeBaseUrlAndFormatStringBuilder() {
        StringBuilder sb = new StringBuilder(earthquakeBaseUrl);
        if (earthquakeFormat != null) {
            sb.append("?")
                    .append(earthquakeFormat);
        }
        return sb;
    }

    /**
     * Returns the base url and format of the EathQuake USGS service
     *
     * @return the base url and format of the EathQuake USGS service
     */
    public String buildEarthQuakeBaseUrlAndFormat() {
        return getEarthQuakeBaseUrlAndFormatStringBuilder().toString();
    }

    /**
     * Builds the url used to get data by date ranges from EarthQuake USGS service
     *
     * @param startTime The start date value string
     * @param endTime   The end date value string
     * @return The fully composed url string to be used against the EarthQuake USGS service
     */
    public String buildEarthquakeUrlByDates(String startTime, String endTime) {
        StringBuilder sb = getEarthQuakeBaseUrlAndFormatStringBuilder();
        if (searchByDatesStartTime != null && startTime != null) {
            sb.append(sb.indexOf("?") == -1 ? "?" : "&")
                    .append(searchByDatesStartTime)
                    .append("=")
                    .append(startTime);
        }
        if (searchByDatesEndTime != null && endTime != null) {
            sb.append(sb.indexOf("?") == -1 ? "?" : "&")
                    .append(searchByDatesEndTime)
                    .append("=")
                    .append(endTime);
        }
        return sb.toString();
    }

    /**
     * Builds the url used to get data by magnitude ranges from EarthQuake USGS service
     *
     * @param minMagnitude The minimum magnitude string
     * @param maxMagnitude The maximum magnitude string
     * @return The fully composed url string to be used against the EarthQuake USGS service
     */
    public String buildEarthquakeUrlByMagnitudes(String minMagnitude, String maxMagnitude) {
        StringBuilder sb = getEarthQuakeBaseUrlAndFormatStringBuilder();
        if (searchByMagnitudesMinMagnitude != null && minMagnitude != null) {
            sb.append(sb.indexOf("?") == -1 ? "?" : "&")
                    .append(searchByMagnitudesMinMagnitude)
                    .append("=")
                    .append(minMagnitude);
        }
        if (searchByMagnitudesMaxMagnitude != null && maxMagnitude != null) {
            sb.append(sb.indexOf("?") == -1 ? "?" : "&")
                    .append(searchByMagnitudesMaxMagnitude)
                    .append("=")
                    .append(maxMagnitude);
        }

        return sb.toString();
    }
}
