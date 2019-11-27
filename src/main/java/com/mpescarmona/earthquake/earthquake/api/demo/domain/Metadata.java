package com.mpescarmona.earthquake.earthquake.api.demo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Metadata {
    Long generated;
    String url;
    String title;
    Integer status;
    String api;
    Integer count;
}
