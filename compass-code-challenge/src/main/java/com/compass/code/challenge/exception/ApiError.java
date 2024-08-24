package com.compass.code.challenge.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {
    private long timestamp;
    private int status;
    private String error;
    private String path;
    private String message;
}
