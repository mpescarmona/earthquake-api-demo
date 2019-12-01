package com.mpescarmona.earthquake.earthquake.api.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Geometry {
    String type;
    Double[] coordinates;
}
