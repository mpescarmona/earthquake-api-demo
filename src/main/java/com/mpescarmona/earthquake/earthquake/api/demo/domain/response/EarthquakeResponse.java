package com.mpescarmona.earthquake.earthquake.api.demo.domain.response;

import com.mpescarmona.earthquake.earthquake.api.demo.domain.Feature;
import com.mpescarmona.earthquake.earthquake.api.demo.domain.Metadata;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EarthquakeResponse {
    String type;
    Metadata metadata;
    List<Feature> features;
    Double[] bbox;
}
