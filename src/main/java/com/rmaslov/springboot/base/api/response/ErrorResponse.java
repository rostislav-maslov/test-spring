package com.rmaslov.springboot.base.api.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ErrorResponse extends OkResponse<String>{

    private String error;
    private HttpStatus statusHttp;

    public static ErrorResponse of(String errorString, HttpStatus status) {
        ErrorResponse error = new ErrorResponse();
        error.setResult(null);
        error.setError(errorString);
        error.setStatus(Status.ERROR);
        error.setStatusHttp(status);
        return error;
    }
}
