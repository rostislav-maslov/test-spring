package com.rmaslov.springboot.base;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldApiController {

    @GetMapping(value = "/")
    public IndexResponse index() {

        return IndexResponse.builder().message("Hello!").build();
    }
}
