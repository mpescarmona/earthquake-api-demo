package com.mpescarmona.earthquake.api.helper

import spock.lang.Specification
import spock.lang.Unroll

class EarthquakeUrlHelperTest extends Specification {
    EarthquakeUrlHelper earthquakeUrlHelper = new EarthquakeUrlHelper()

    void setup() {
        earthquakeUrlHelper.setEarthquakeBaseUrl("http://fakequakes/query")
        earthquakeUrlHelper.setEarthquakeFormat("format=any")
        earthquakeUrlHelper.setSearchByDatesStartTime("startTime")
        earthquakeUrlHelper.setSearchByDatesEndTime("endTime")
        earthquakeUrlHelper.setSearchByMagnitudesMinMagnitude("minMagnitude")
        earthquakeUrlHelper.setSearchByMagnitudesMaxMagnitude("maxMagnitude")
    }

    def "buildEarthQuakeBaseUrlAndFormat"() {
        given:
        String expectedUrl

        when:
        expectedUrl = earthquakeUrlHelper.buildEarthQuakeBaseUrlAndFormat()

        then:
        expectedUrl == "http://fakequakes/query?format=any"
    }

    @Unroll
    def "BuildEarthquakeUrlByDates(#startTime, #endTime) == #expectedUrl"(String startTime, String endTime, String expectedUrl) {
        expect:
        earthquakeUrlHelper.buildEarthquakeUrlByDates(startTime, endTime) == expectedUrl

        where:
        startTime    | endTime      | expectedUrl
        "2019-11-20" | "2019-11-21" | "http://fakequakes/query?format=any&startTime=2019-11-20&endTime=2019-11-21"
        "2019-11-20" | null         | "http://fakequakes/query?format=any&startTime=2019-11-20"
        null         | "2019-11-21" | "http://fakequakes/query?format=any&endTime=2019-11-21"
        null         | null         | "http://fakequakes/query?format=any"
    }

    @Unroll
    def "BuildEarthquakeUrlByMagnitudes(#minMagnituda, #maxMagnitude) == #expectedUrl"(String minMagnitude, String maxMagnitude, String expectedUrl) {
        expect:
        earthquakeUrlHelper.buildEarthquakeUrlByMagnitudes(minMagnitude, maxMagnitude) == expectedUrl

        where:
        minMagnitude | maxMagnitude | expectedUrl
        "4.1"        | "5.3"        | "http://fakequakes/query?format=any&minMagnitude=4.1&maxMagnitude=5.3"
        "6.2"        | null         | "http://fakequakes/query?format=any&minMagnitude=6.2"
        null         | "7.4"        | "http://fakequakes/query?format=any&maxMagnitude=7.4"
        null         | null         | "http://fakequakes/query?format=any"
    }
}
