package ru.test.netris.exception;

import lombok.Getter;

public class ApiIntegrationException extends Exception {

    @Getter
    private String errorResponse;

    public ApiIntegrationException(String errorResponse) {
        this.errorResponse = errorResponse;
    }
}
