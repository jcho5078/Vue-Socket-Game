package com.jcho.backapi.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCode {
    REQUEST_OK(0, HttpStatus.OK, "REQUEST_OK"),
    REQUEST_FAIL(1, HttpStatus.BAD_REQUEST, "REQUEST_FAIL"),
    NOT_FOUND(2, HttpStatus.NOT_FOUND, "NOT_FOUND"),
    NOT_AUTH(3, HttpStatus.FORBIDDEN, "NOT_AUTH"),
    NO_CONTENT(4, HttpStatus.NO_CONTENT, "NO_CONTENT");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String msg;
}
