package com.mpescarmona.earthquake.earthquake.api.demo.domain.response;

import com.mpescarmona.earthquake.earthquake.api.demo.domain.Feature;
import com.mpescarmona.earthquake.earthquake.api.demo.domain.Metadata;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class EarthquakeResponse {
    String type;
    Metadata metadata;
    List<Feature> features;
    Double[] bbox;
}
