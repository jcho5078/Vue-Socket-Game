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

    @Autowired
    private static JwtUtil jwtUtil;

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
     * 요청결과 응답(Check Token)
     * @param responseDto
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<?> responseResult(HttpServletRequest request, T responseDto) throws Exception{
        String token = request.getHeader(CommonCode.JWT_HEADER);

        try {
            /**
             * 토큰 검증
             */
            if(StringUtils.isEmpty(token) && !jwtUtil.validateToken(token)){
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                        .build();
            }

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
        ResponseDTO<UserDto> result = new ResponseDTO();
        result.setResultCode(ResultCode.REQUEST_FAIL);

        /**
         *  인증 처리 실행시, JWT 생성 로직
         */
        if(responseDto.getClass().equals(UserDto.class)
                && !StringUtils.isEmpty(((UserDto)responseDto).getJwtToken())){
            result.setJwtToken(((UserDto)responseDto).getJwtToken());
        }

        /**
         * Exception 발생
         * 요청 미처리, 메세지 반환(202)
         */
        if(responseDto.getClass().equals(Exception.class)
                && !StringUtils.isEmpty(((Exception)responseDto).getMessage())){
            result.setMsg(((Exception)responseDto).getMessage());
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(result);
        }

        /**
         * 결과 처리
         */
        if(responseDto == null){
            return ResponseEntity.noContent()
                    .build();
        }else{
            result.setResultCode(ResultCode.REQUEST_OK);
        }

        // HATEOAS EntityModel
        //EntityModel entityModel = EntityModel.of(responseDto, linkTo(methodOn(UserController.class).login(responseDto)).withSelfRel());

        return ResponseEntity.ok()
                .body(result);
    }
}
