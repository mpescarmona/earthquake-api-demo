package com.mpescarmona.earthquake.api.domain.response;

import com.mpescarmona.earthquake.api.domain.Feature;
import com.mpescarmona.earthquake.api.domain.Metadata;
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
