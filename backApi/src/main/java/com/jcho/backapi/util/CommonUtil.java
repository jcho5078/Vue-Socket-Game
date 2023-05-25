package com.jcho.backapi.util;

import com.jcho.backapi.common.CommonCode;
import com.jcho.backapi.common.ResultCode;
import com.jcho.backapi.common.dto.ResponseDTO;
import com.jcho.backapi.web.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {

    /**
     * 요청결과 응답(No Check Token)
     * @param responseDto
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> ResponseEntity<?> responseResult(T responseDto) throws Exception{
        try {
            /**
             * 요청결과 처리
             */
            return execResult(responseDto);
        }catch (Exception e){
            return ResponseEntity.badRequest()
                    .body(responseDto);
        }
    }

    /**
     * 결과 처리
     * @param responseDto
     * @param <T>
     * @return
     */
    private static <T> ResponseEntity<?> execResult(T responseDto){
        ResponseDTO result = new ResponseDTO();
        result.setResultCode(ResultCode.REQUEST_FAIL);

        /**
         * 결과 처리
         */
        if(responseDto == null){
            result.setResultCode(ResultCode.NO_CONTENT);
        }else{
            result.setResponseDto(responseDto);
            result.setResultCode(ResultCode.REQUEST_OK);
        }

        // HATEOAS EntityModel
        //EntityModel entityModel = EntityModel.of(responseDto, linkTo(methodOn(UserController.class).login(responseDto)).withSelfRel());

        return ResponseEntity.ok()
                .body(result);
    }
}
