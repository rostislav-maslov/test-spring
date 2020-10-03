package com.rmaslov.springboot.base.controllers;

import com.rmaslov.springboot.base.api.request.PostRequest;
import com.rmaslov.springboot.base.api.response.IndexResponse;
import com.rmaslov.springboot.base.routes.ExampleRoutes;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class HelloWorldApiController {

    @GetMapping(value = "/")
    public IndexResponse index() {
        return IndexResponse.builder().message("Hello, World!").build();
    }

    @GetMapping(value = ExampleRoutes.GET)
    public IndexResponse getExample() {
        return IndexResponse.builder().message("ExampleRoutes.GET Response").build();
    }

    @GetMapping(value = ExampleRoutes.GET_PARAM)
    public IndexResponse getExampleWithParam(
            @RequestParam String param1,
            @RequestParam(value = "p_a_r_a_m_2") String param2,
            @RequestParam(required = false) String param3,
            @RequestParam(required = false, defaultValue = "def") String param4) {
        return IndexResponse.builder().message(
                param1 + ", " +
                        param2 + ", " +
                        Optional.ofNullable(param3).toString() + ", " +
                        param4
        ).build();
    }

    @GetMapping(value = ExampleRoutes.GET_PATH)
    public IndexResponse getExampleWithPath(
            @PathVariable String pathVariable) {
        return IndexResponse.builder().message(
                pathVariable
        ).build();
    }

    @PostMapping(value = ExampleRoutes.POST)
    public IndexResponse postExample(@RequestBody PostRequest request) {
        return IndexResponse.builder().message(
                request.toString()
        ).build();
    }

    @PutMapping(value = ExampleRoutes.PUT)
    public IndexResponse putExample(@PathVariable String id, @RequestBody PostRequest request) {
        return IndexResponse.builder().message(
                id + " " + request.toString()
        ).build();
    }

    @DeleteMapping(value = ExampleRoutes.DELETE)
    public IndexResponse putExample(@PathVariable String id) {
        return IndexResponse.builder().message(
                id
        ).build();
    }
}
