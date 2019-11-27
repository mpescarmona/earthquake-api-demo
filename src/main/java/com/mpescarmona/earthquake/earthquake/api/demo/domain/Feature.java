package com.mpescarmona.earthquake.earthquake.api.demo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Feature {
    String type;
    Properties properties;
    Geometry geometry;
    String id;
}
