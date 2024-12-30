package com.example.Application.dto.responseformat;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class ApiResponse<T> extends ResponseEntity<ResponseFormat<T>> {

    private ApiResponse(ResponseFormat<T> body, HttpStatus status) {
        super(body, status);
    }

    public static <T> ApiResponse<T> success(T data) {
        ResponseFormat<T> response = new ResponseFormat<>(HttpStatus.OK, data, "성공");
        return new ApiResponse<>(response, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> customSuccess(String message, T data) {
        ResponseFormat<T> response = new ResponseFormat<>(HttpStatus.OK, data, message);
        return new ApiResponse<>(response, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> notFound(String message) {
        ResponseFormat<T> response = new ResponseFormat<>(HttpStatus.NOT_FOUND, null, message);
        return new ApiResponse<>(response, HttpStatus.NOT_FOUND);
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus status) {
        ResponseFormat<T> response = new ResponseFormat<>(status, null, message);
        return new ApiResponse<>(response, status);
    }
}
