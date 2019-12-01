package com.mpescarmona.earthquake.earthquake.api.demo.service.impl

import com.mpescarmona.earthquake.earthquake.api.demo.domain.Feature
import com.mpescarmona.earthquake.earthquake.api.demo.domain.Metadata
import com.mpescarmona.earthquake.earthquake.api.demo.domain.Properties
import com.mpescarmona.earthquake.earthquake.api.demo.domain.response.EarthquakeResponse
import com.mpescarmona.earthquake.earthquake.api.demo.helper.EarthquakeUrlHelper
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class EarthquakeServiceImplTest extends Specification {

    RestTemplate restTemplate = Mock {}
    EarthquakeUrlHelper earthquakeUrlHelper = Mock {}
    EarthquakeServiceImpl earthquakeService = new EarthquakeServiceImpl(restTemplate, earthquakeUrlHelper)

    def "GetEarthquakesByDateRanges"() {
        given:
        EarthquakeResponse response
        String startTime = "2019-11-27"
        String endTime = "2019-11-28"
        String url = "http://fakeearthquakeservice?format=geoformat&startTime=" + startTime + "&endTime=" + endTime

        when:
        earthquakeUrlHelper.buildEarthquakeUrlByDates(startTime, endTime) >> url

        List<Properties> properties = Arrays.asList(
                Properties.builder()
                        .place("83km W of Copiapo, Chile")
                        .mag(4.6)
                        .build()
        );
        List<Feature> features = buildFeaturesFromProperties(properties);
        EarthquakeResponse earthQuakeResponse = buildEarthquakeResponse(features);
        restTemplate.exchange(url, HttpMethod.GET, _, EarthquakeResponse.class) >> new ResponseEntity(earthQuakeResponse, HttpStatus.OK)

        response = earthquakeService.getEarthquakesByDateRange(startTime, endTime)

        then:
        response != null
        response.features.size() == 1
        response.metadata.count == 1
    }

    def "GetEarthquakesByMagnitudeRanges"() {
        given:
        EarthquakeResponse response
        String minMagnitude = "6.1"
        String maxMagnitude = "7.5"
        String url = "http://fakeearthquakeservice?format=geoformat&minMagnitude=" + minMagnitude + "&maxMagnitude=" + maxMagnitude

        when:
        earthquakeUrlHelper.buildEarthquakeUrlByMagnitudes(minMagnitude, maxMagnitude) >> url

        List<Properties> properties = Arrays.asList(
                Properties.builder()
                        .place("83km W of Copiapo, Chile")
                        .mag(7.4)
                        .build(),
                Properties.builder()
                        .place("62km SE of Molibagu, Indonesia")
                        .mag(6.1)
                        .build(),
                Properties.builder()
                        .place("24km ESE of Punta de Burica, Panama")
                        .mag(6.5)
                        .build()
        );
        List<Feature> features = buildFeaturesFromProperties(properties);
        EarthquakeResponse earthQuakeResponse = buildEarthquakeResponse(features);
        restTemplate.exchange(url, HttpMethod.GET, _, EarthquakeResponse.class) >> new ResponseEntity(earthQuakeResponse, HttpStatus.OK)

        response = earthquakeService.getEarthquakesByMagnitudeRanges(minMagnitude, maxMagnitude)

        then:
        response != null
        response.features.size() == 3
        response.metadata.count == 3
    }

    def "TestGetEarthquakesByDateRanges"() {
        given:
        EarthquakeResponse response
        String startTime1 = "2019-11-20"
        String endTime1 = "2019-11-21"
        String url1 = "http://fakeearthquakeservice?format=geoformat&startTime=" + startTime1 + "&endTime=" + endTime1
        String startTime2 = "2019-11-27"
        String endTime2 = "2019-11-28"
        String url2 = "http://fakeearthquakeservice?format=geoformat&startTime=" + startTime2 + "&endTime=" + endTime2

        when:
        earthquakeUrlHelper.buildEarthquakeUrlByDates(startTime1, endTime1) >> url1
        earthquakeUrlHelper.buildEarthquakeUrlByDates(startTime2, endTime2) >> url2

        List<Properties> properties1 = Arrays.asList(
                Properties.builder()
                        .place("62km SE of Molibagu, Indonesia")
                        .mag(6.1)
                        .build(),
                Properties.builder()
                        .place("83km W of Copiapo, Chile")
                        .mag(7.2)
                        .build()
        );
        List<Feature> features1 = buildFeaturesFromProperties(properties1);
        EarthquakeResponse earthQuakeResponse1 = buildEarthquakeResponse(features1)

        List<Properties> properties2 = Arrays.asList(
                Properties.builder()
                        .place("7km N of Zonda, Argentina")
                        .mag(7.1)
                        .build()
        )
        List<Feature> features2 = buildFeaturesFromProperties(properties2)
        EarthquakeResponse earthQuakeResponse2 = buildEarthquakeResponse(features2)

        restTemplate.exchange(url1, HttpMethod.GET, _, EarthquakeResponse.class) >> new ResponseEntity(earthQuakeResponse1, HttpStatus.OK)
        restTemplate.exchange(url2, HttpMethod.GET, _, EarthquakeResponse.class) >> new ResponseEntity(earthQuakeResponse2, HttpStatus.OK)

        response = earthquakeService.getEarthquakesByDateRanges(startTime1, endTime1, startTime2, endTime2)

        then:
        response != null
        response.features.size() == 3
        response.metadata.count == 3
    }

    def "GetEarthquakesByCountry"() {
        given:
        EarthquakeResponse response
        String url = "http://fakeearthquakeservice?format=geoformat";

        when:
        earthquakeUrlHelper.buildEarthQuakeBaseUrlAndFormat() >> url

        List<Properties> properties = Arrays.asList(
                Properties.builder()
                        .place("83km W of Copiapo, Chile")
                        .mag(7.4)
                        .build(),
                Properties.builder()
                        .place("62km SE of Molibagu, Indonesia")
                        .mag(6.1)
                        .build(),
                Properties.builder()
                        .place("24km ESE of Punta de Burica, Panama")
                        .mag(6.5)
                        .build()
        );
        List<Feature> features = buildFeaturesFromProperties(properties);
        EarthquakeResponse earthQuakeResponse = buildEarthquakeResponse(features);
        restTemplate.exchange(url, HttpMethod.GET, _, EarthquakeResponse.class) >> new ResponseEntity(earthQuakeResponse, HttpStatus.OK)

        response = earthquakeService.getEarthquakesByCountry("Chile")

        then:
        response != null
        response.features.size() == 1
        response.metadata.count == 1
    }

    def "GetEarthquakesByCountriesAndDateRanges"() {
        given:
        EarthquakeResponse response
        String startTime = "2019-11-27"
        String endTime = "2019-11-28"
        String url = "http://fakeearthquakeservice?format=geoformat&startTime=" + startTime + "&endTime=" + endTime

        when:
        earthquakeUrlHelper.buildEarthquakeUrlByDates(startTime, endTime) >> url

        List<Properties> properties = Arrays.asList(
                Properties.builder()
                        .place("83km W of Copiapo, Chile")
                        .mag(7.4)
                        .build(),
                Properties.builder()
                        .place("62km SE of Molibagu, Indonesia")
                        .mag(6.1)
                        .build(),
                Properties.builder()
                        .place("24km ESE of Punta de Burica, Panama")
                        .mag(6.5)
                        .build()
        );
        List<Feature> features = buildFeaturesFromProperties(properties);
        EarthquakeResponse earthQuakeResponse = buildEarthquakeResponse(features);
        restTemplate.exchange(url, HttpMethod.GET, _, EarthquakeResponse.class) >> new ResponseEntity(earthQuakeResponse, HttpStatus.OK)

        response = earthquakeService.getEarthquakesByCountriesAndDateRange("Chile", "Indonesia", startTime, endTime);

        then:
        response != null
        response.features.size() == 2
        response.metadata.count == 2
    }

    private EarthquakeResponse buildEarthquakeResponse(List<Feature> features) {
        return EarthquakeResponse.builder()
                .type("Fake")
                .metadata(Metadata.builder()
                        .count(features.size())
                        .build())
                .features(features)
                .build()
    }

    private List<Feature> buildFeaturesFromProperties(List<Properties> properties) {
        List<Feature> features = new ArrayList<>();
        properties.forEach({ property ->
            features.add(
                    Feature.builder()
                            .properties(property)
                            .build()
            )
        })
        return features;
    }
}
