package com.potato.auth.exceptions;

import lombok.Getter;

@Getter
public class RestException extends RuntimeException {

    private Integer status;

    public RestException(String message, Integer status) {
        super(message);
        this.status = status;
    }

}
