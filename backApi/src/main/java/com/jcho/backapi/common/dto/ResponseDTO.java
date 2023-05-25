package com.jcho.backapi.common.dto;

import com.jcho.backapi.common.ResultCode;
import lombok.Data;

@Data
public class ResponseDTO<T> {

    /**
     * 결과 DTO
     */
    private T responseDto;

    /**
     * 결과 상태
     */
    private ResultCode resultCode;

    /**
     * 메세지
     */
    private String msg;
}
