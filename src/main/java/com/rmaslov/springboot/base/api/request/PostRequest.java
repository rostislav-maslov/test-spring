package com.rmaslov.springboot.base.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PostRequest {
    @Getter
    @Setter
    public static class Inner{
        private String param;
    }

    private String param;
    private Integer paramInt;
    private Double paramDouble;
    private Inner paramInner;

}
