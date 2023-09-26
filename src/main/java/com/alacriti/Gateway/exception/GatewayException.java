package com.alacriti.Gateway.exception;

import org.springframework.http.HttpStatus;

public class GatewayException {

	private final String message;
    private final Object data;
    private final HttpStatus httpStatus;

    public GatewayException(String message, Object data, HttpStatus httpStatus) {
        this.message = message;
        this.data = data;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public Object getObject() {
        return data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
