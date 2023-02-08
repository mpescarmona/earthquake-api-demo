package com.mpescarmona.earthquake.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.mpescarmona.earthquake.api.configuration.JwtAuthenticationEntryPoint
import com.mpescarmona.earthquake.api.domain.Feature
import com.mpescarmona.earthquake.api.domain.Metadata
import com.mpescarmona.earthquake.api.domain.Properties
import com.mpescarmona.earthquake.api.domain.response.EarthquakeResponse
import com.mpescarmona.earthquake.api.dto.CountriesAndDateRangeRequestDto
import com.mpescarmona.earthquake.api.dto.CountryRequestDto
import com.mpescarmona.earthquake.api.dto.DateRangeRequestDto
import com.mpescarmona.earthquake.api.dto.DateRangesRequestDto
import com.mpescarmona.earthquake.api.dto.MagnitudeRangeRequestDto
import com.mpescarmona.earthquake.api.service.IEarthquakeService
import com.mpescarmona.earthquake.api.service.impl.JwtUserDetailsService
import com.mpescarmona.earthquake.api.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Ignore
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

    @Ignore
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
        def startTime = '2019-11-28'
        def endTime = '2019-11-29'
        def requestBody = DateRangeRequestDto.builder().startTime(startTime).endTime(endTime).build()

        and:
        earthquakeService.getEarthquakesByDateRange(startTime, endTime) >> earthQuakeResponse

        when:
        def results = mockMvc.perform(get('/daterange')
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTU3NTQyMTg4NCwiaWF0IjoxNTc1NDAzODg0fQ.-qLpVMutj4glAbM4y6WcI9mLQtmPyAyg2WUMzKUD7Qle09kafDx4L4BxG8CLWMItJLQCU-v4uvpy0STta8oFHA")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(requestBody))
        )

        then:
        results.andExpect(status().is2xxSuccessful())

        and:
        results.andExpect(jsonPath('$.features').exists())
        results.andExpect(jsonPath('$.features').isArray())
    }

    @Ignore
    def "GetEarthquakesByDateRanges responds Unauthorized error without authentication"() {
        given:
        def startTime1 = '2019-11-28'
        def endTime1 = '2019-11-29'
        def startTime2 = '2019-11-28'
        def endTime2 = '2019-11-29'
        def requestBody = DateRangesRequestDto.builder()
                .startTime1(startTime1).endTime1(endTime1)
                .startTime2(startTime2).endTime2(endTime2)
                .build()

        when:
        def results = mockMvc.perform(get('/dateranges')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(requestBody))
        )

        then:
        results.andExpect(status().isUnauthorized())
    }

    @WithMockUser(roles = ['FULL_ACCESS'])
    def "GetEarthquakesByDateRanges"() {
        given:
        def startTime1 = '2019-11-28'
        def endTime1 = '2019-11-29'
        def startTime2 = '2019-11-28'
        def endTime2 = '2019-11-29'
        def requestBody = DateRangesRequestDto.builder()
                .startTime1(startTime1).endTime1(endTime1)
                .startTime2(startTime2).endTime2(endTime2)
                .build()

        and:
        earthquakeService.getEarthquakesByDateRanges(startTime1, endTime1, startTime2, endTime2) >> earthQuakeResponse

        when:
        def results = mockMvc.perform(get('/dateranges')
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTU3NTQyMTg4NCwiaWF0IjoxNTc1NDAzODg0fQ.-qLpVMutj4glAbM4y6WcI9mLQtmPyAyg2WUMzKUD7Qle09kafDx4L4BxG8CLWMItJLQCU-v4uvpy0STta8oFHA")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(requestBody))
        )

        then:
        results.andExpect(status().is2xxSuccessful())

        and:
        results.andExpect(jsonPath('$.features').exists())
        results.andExpect(jsonPath('$.features').isArray())
    }

    @Ignore
    def "GetEarthquakesByMagnitudeRange responds Unauthorized error without authentication"() {
        given:
        def minMagnitude = '6.1'
        def maxMagnitude = '7.4'
        def requestBody = MagnitudeRangeRequestDto.builder()
                .minMagnitude(minMagnitude).maxMagnitude(maxMagnitude)
                .build()

        when:
        def results = mockMvc.perform(get('/magnituderange')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(requestBody))
        )

        then:
        results.andExpect(status().isUnauthorized())
    }

    @WithMockUser(roles = ['FULL_ACCESS'])
    def "GetEarthquakesByMagnitudeRange"() {
        given:
        def minMagnitude = '6.1'
        def maxMagnitude = '7.4'
        def requestBody = MagnitudeRangeRequestDto.builder()
                .minMagnitude(minMagnitude).maxMagnitude(maxMagnitude)
                .build()

        and:
        earthquakeService.getEarthquakesByMagnitudeRange(minMagnitude, maxMagnitude) >> earthQuakeResponse

        when:
        def results = mockMvc.perform(get('/magnituderange')
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTU3NTQyMTg4NCwiaWF0IjoxNTc1NDAzODg0fQ.-qLpVMutj4glAbM4y6WcI9mLQtmPyAyg2WUMzKUD7Qle09kafDx4L4BxG8CLWMItJLQCU-v4uvpy0STta8oFHA")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(requestBody))
        )

        then:
        results.andExpect(status().is2xxSuccessful())

        and:
        results.andExpect(jsonPath('$.features').exists())
        results.andExpect(jsonPath('$.features').isArray())
    }

    @Ignore
    def "GetEarthquakesByCountry responds Unauthorized error without authentication"() {
        given:
        def country = 'Chile'
        def requestBody = CountryRequestDto.builder()
                .country(country)
                .build()

        when:
        def results = mockMvc.perform(get('/country')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(requestBody))
        )

        then:
        results.andExpect(status().isUnauthorized())
    }

    @WithMockUser(roles = ['FULL_ACCESS'])
    def "GetEarthquakesByCountry"() {
        given:
        def country = 'Chile'
        def requestBody = CountryRequestDto.builder()
                .country(country)
                .build()

        and:
        earthquakeService.getEarthquakesByCountry(country) >> earthQuakeResponseCountry

        when:
        def results = mockMvc.perform(get('/country')
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTU3NTQyMTg4NCwiaWF0IjoxNTc1NDAzODg0fQ.-qLpVMutj4glAbM4y6WcI9mLQtmPyAyg2WUMzKUD7Qle09kafDx4L4BxG8CLWMItJLQCU-v4uvpy0STta8oFHA")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(requestBody))
        )

        then:
        results.andExpect(status().is2xxSuccessful())

        and:
        results.andExpect(jsonPath('$.features').exists())
        results.andExpect(jsonPath('$.features').isArray())
    }

    @Ignore
    def "GetEarthquakesByCountriesAndDateRange responds Unauthorized error without authentication"() {
        given:
        def countryOne = 'Chile'
        def countryTwo = 'Panama'
        def startTime = '2019-11-28'
        def endTime = '2019-11-29'
        def requestBody = CountriesAndDateRangeRequestDto.builder()
                .countryOne(countryOne)
                .countryTwo(countryTwo)
                .startTime(startTime)
                .endTime(endTime)
                .build()

        when:
        def results = mockMvc.perform(get('/countries')
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(requestBody))
        )

        then:
        results.andExpect(status().isUnauthorized())
    }

    @WithMockUser(roles = ['FULL_ACCESS'])
    def "GetEarthquakesByCountriesAndDateRange"() {
        given:
        def countryOne = 'Chile'
        def countryTwo = 'Panama'
        def startTime = '2019-11-28'
        def endTime = '2019-11-29'
        def requestBody = CountriesAndDateRangeRequestDto.builder()
                .countryOne(countryOne)
                .countryTwo(countryTwo)
                .startTime(startTime)
                .endTime(endTime)
                .build()

        and:
        earthquakeService.getEarthquakesByCountriesAndDateRange(countryOne, countryTwo, startTime, endTime) >> earthQuakeResponseCountriesAndDate

        when:
        def results = mockMvc.perform(get('/countries')
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImV4cCI6MTU3NTQyMTg4NCwiaWF0IjoxNTc1NDAzODg0fQ.-qLpVMutj4glAbM4y6WcI9mLQtmPyAyg2WUMzKUD7Qle09kafDx4L4BxG8CLWMItJLQCU-v4uvpy0STta8oFHA")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(asJsonString(requestBody))
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

        @Bean
        JwtUserDetailsService jwtUserDetailsService() {
            return detachedMockFactory.Stub(JwtUserDetailsService)
        }

        @Bean
        JwtTokenUtil jwtTokenUtil() {
            return detachedMockFactory.Stub(JwtTokenUtil)
        }

        @Bean
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
            return detachedMockFactory.Stub(JwtAuthenticationEntryPoint)
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

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
