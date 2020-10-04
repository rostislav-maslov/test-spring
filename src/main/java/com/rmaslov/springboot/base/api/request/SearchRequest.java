package com.rmaslov.springboot.base.api.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequest {
    @ApiParam(name = "query", value = "Search by first name, last name and email", required = false)
    protected String query = null;
    @ApiParam(name = "size", value = "List size", required = false)
    protected Long size = 100l;
    @ApiParam(name = "skip", value = "Skip first in search", required = false)
    protected Long skip = 0l;
}
