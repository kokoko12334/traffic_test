package com.example.Application.dto.responseformat;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseFormat<T> {
    private final HttpStatus status;
    private final T data;
    private final String message;

    public ResponseFormat(HttpStatus status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
