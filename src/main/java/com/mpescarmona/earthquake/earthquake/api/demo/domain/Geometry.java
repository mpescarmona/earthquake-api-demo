package com.mpescarmona.earthquake.earthquake.api.demo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Geometry {
    String type;
    Double[] coordinates;
}
