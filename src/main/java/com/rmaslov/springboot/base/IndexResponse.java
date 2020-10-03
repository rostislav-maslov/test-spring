package com.rmaslov.springboot.base;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class IndexResponse {
    private String message;
}
