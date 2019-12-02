package com.mpescarmona.earthquake.earthquake.api.demo.controller


import com.mpescarmona.earthquake.earthquake.api.demo.domain.Feature
import com.mpescarmona.earthquake.earthquake.api.demo.domain.Metadata
import com.mpescarmona.earthquake.earthquake.api.demo.domain.Properties
import com.mpescarmona.earthquake.earthquake.api.demo.domain.response.EarthquakeResponse
import com.mpescarmona.earthquake.earthquake.api.demo.service.IEarthquakeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.mock.DetachedMockFactory

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [EarthquakeController])
class EarthquakeControllerTest extends Specification {

    @Autowired
    protected MockMvc mockMvc

    @Autowired
    IEarthquakeService earthquakeService

    EarthquakeResponse earthQuakeResponse
    EarthquakeResponse earthQuakeResponseCountry
    EarthquakeResponse earthQuakeResponseCountriesAndDate

    void setup() {
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
        )
        List<Properties> propertiesCountry = Arrays.asList(
                Properties.builder()
                        .place("83km W of Copiapo, Chile")
                        .mag(7.4)
                        .build()
        )
        List<Properties> propertiesCountriesAndDates = Arrays.asList(
                Properties.builder()
                        .place("83km W of Copiapo, Chile")
                        .mag(7.4)
                        .build(),
                Properties.builder()
                        .place("24km ESE of Punta de Burica, Panama")
                        .mag(6.5)
                        .build()
        )
        List<Feature> features = buildFeaturesFromProperties(properties)
        List<Feature> featuresCountry = buildFeaturesFromProperties(propertiesCountry)
        List<Feature> featuresCountries = buildFeaturesFromProperties(propertiesCountriesAndDates)
        earthQuakeResponse = buildEarthquakeResponse(features)
        earthQuakeResponseCountry = buildEarthquakeResponse(featuresCountry)
        earthQuakeResponseCountriesAndDate = buildEarthquakeResponse(featuresCountries)
    }

    def "GetEarthquakesByDateRange responds Unauthorized error without authentication"() {
        given:
        String startTime = '2019-11-28'
        String endTime = '2019-11-29'

        when:
        def results = mockMvc.perform(get('/daterange')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("startTime", startTime)
                .param("endTime", endTime)
        )

        then:
        results.andExpect(status().isUnauthorized())
    }

    @WithMockUser(roles = ['FULL_ACCESS'])
    def "GetEarthquakesByDateRange"() {
        given:
        String startTime = '2019-11-28'
        String endTime = '2019-11-29'

        and:
        earthquakeService.getEarthquakesByDateRange(startTime, endTime) >> earthQuakeResponse

        when:
        def results = mockMvc.perform(get('/daterange')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("startTime", startTime)
                .param("endTime", endTime)
        )

        then:
        results.andExpect(status().is2xxSuccessful())

        and:
        results.andExpect(jsonPath('$.features').exists())
        results.andExpect(jsonPath('$.features').isArray())
    }

    def "GetEarthquakesByDateRanges responds Unauthorized error without authentication"() {
        given:
        String startTime1 = '2019-11-28'
        String endTime1 = '2019-11-29'
        String startTime2 = '2019-11-28'
        String endTime2 = '2019-11-29'

        when:
        def results = mockMvc.perform(get('/dateranges')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("startTime1", startTime1)
                .param("endTime1", endTime1)
                .param("startTime2", startTime2)
                .param("endTime2", endTime2)
        )

        then:
        results.andExpect(status().isUnauthorized())
    }

    @WithMockUser(roles = ['FULL_ACCESS'])
    def "GetEarthquakesByDateRanges"() {
        given:
        String startTime1 = '2019-11-28'
        String endTime1 = '2019-11-29'
        String startTime2 = '2019-11-28'
        String endTime2 = '2019-11-29'

        and:
        earthquakeService.getEarthquakesByDateRanges(startTime1, endTime1, startTime2, endTime2) >> earthQuakeResponse

        when:
        def results = mockMvc.perform(get('/dateranges')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("startTime1", startTime1)
                .param("endTime1", endTime1)
                .param("startTime2", startTime2)
                .param("endTime2", endTime2)
        )

        then:
        results.andExpect(status().is2xxSuccessful())

        and:
        results.andExpect(jsonPath('$.features').exists())
        results.andExpect(jsonPath('$.features').isArray())
    }

    def "GetEarthquakesByMagnitudeRange responds Unauthorized error without authentication"() {
        given:
        String minMagnitude = '6.1'
        String maxMagnitude = '7.4'

        when:
        def results = mockMvc.perform(get('/magnituderange')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("minMagnitude", minMagnitude)
                .param("maxMagnitude", maxMagnitude)
        )

        then:
        results.andExpect(status().isUnauthorized())
    }

    @WithMockUser(roles = ['FULL_ACCESS'])
    def "GetEarthquakesByMagnitudeRange"() {
        given:
        String minMagnitude = '6.1'
        String maxMagnitude = '7.4'

        and:
        earthquakeService.getEarthquakesByMagnitudeRange(minMagnitude, maxMagnitude) >> earthQuakeResponse

        when:
        def results = mockMvc.perform(get('/magnituderange')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("minMagnitude", minMagnitude)
                .param("maxMagnitude", maxMagnitude)
        )

        then:
        results.andExpect(status().is2xxSuccessful())

        and:
        results.andExpect(jsonPath('$.features').exists())
        results.andExpect(jsonPath('$.features').isArray())
    }

    def "GetEarthquakesByCountry responds Unauthorized error without authentication"() {
        given:
        String country = 'Chile'

        when:
        def results = mockMvc.perform(get('/country')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("country", country)
        )

        then:
        results.andExpect(status().isUnauthorized())
    }

    @WithMockUser(roles = ['FULL_ACCESS'])
    def "GetEarthquakesByCountry"() {
        given:
        String country = 'Chile'

        and:
        earthquakeService.getEarthquakesByCountry(country) >> earthQuakeResponseCountry

        when:
        def results = mockMvc.perform(get('/country')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("country", country)
        )

        then:
        results.andExpect(status().is2xxSuccessful())

        and:
        results.andExpect(jsonPath('$.features').exists())
        results.andExpect(jsonPath('$.features').isArray())
    }

    def "GetEarthquakesByCountriesAndDateRange responds Unauthorized error without authentication"() {
        given:
        String countryOne = 'Chile'
        String countryTwo = 'Panama'
        String startTime = '2019-11-28'
        String endTime = '2019-11-29'

        when:
        def results = mockMvc.perform(get('/countries')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("countryOne", countryOne)
                .param("countryTwo", countryTwo)
                .param("startTime", startTime)
                .param("endTime", endTime)
        )

        then:
        results.andExpect(status().isUnauthorized())
    }

    @WithMockUser(roles = ['FULL_ACCESS'])
    def "GetEarthquakesByCountriesAndDateRange"() {
        given:
        String countryOne = 'Chile'
        String countryTwo = 'Panama'
        String startTime = '2019-11-28'
        String endTime = '2019-11-29'

        and:
        earthquakeService.getEarthquakesByCountriesAndDateRange(countryOne, countryTwo, startTime, endTime) >> earthQuakeResponseCountriesAndDate

        when:
        def results = mockMvc.perform(get('/countries')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("countryOne", countryOne)
                .param("countryTwo", countryTwo)
                .param("startTime", startTime)
                .param("endTime", endTime)
        )

        then:
        results.andExpect(status().is2xxSuccessful())

        and:
        results.andExpect(jsonPath('$.features').exists())
        results.andExpect(jsonPath('$.features').isArray())
    }

    @TestConfiguration
    static class StubConfig {
        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        IEarthquakeService earthquakeService() {
            return detachedMockFactory.Stub(IEarthquakeService)
        }
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
