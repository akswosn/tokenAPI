package com.akswosn.tokenapi.v1.controller;

import com.akswosn.tokenapi.entity.ResponseEntity;
import com.akswosn.tokenapi.entity.token.ResponseToken;
import com.akswosn.tokenapi.entity.user.RequestUser;
import com.akswosn.tokenapi.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 간략 : 인증 컨트롤러
 * 상세 :
 * </pre>
 *
 * @Author : Keun-su(akswosn@gmail)
 * @Date : 2021-06-17
 * @Version : 1.0
 * -----------------------------------
 * 1.0 : 신규작성
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@Api(value="/api/v1/auth",description="인증 API", tags="Auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AuthService service;

    @ApiOperation(value="인증 등록 API ", notes="인증 등록 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestUser", value = "사용자 정보" , required = true, dataTypeClass = RequestUser.class , paramType = "body", defaultValue = "")
    })
    @PostMapping("")
    public ResponseEntity<ResponseToken> auth(@RequestBody RequestUser requestUser){
        ResponseEntity<ResponseToken> response = null;
        log.debug("[POST]auth ::: {} ", requestUser);

        try{
            // 필수값 검증
            if(requestUser.getUserId() == null || "".equals(requestUser.getUserId())){
                response = ResponseEntity.inputFail("[필수값 누락] 사용자 id ["+requestUser.getUserId()+"]");
                return response;
            }
            if(requestUser.getPassword() == null || "".equals(requestUser.getPassword())){
                response = ResponseEntity.inputFail("[필수값 누락] 사용자 비밀번호 ["+requestUser.getPassword()+"]");
                return response;
            }

            ResponseToken token = service.auth(requestUser);

            if(token == null){
                response = ResponseEntity.inputFail("토큰발급 실패");
            }
            else {
                response = ResponseEntity.createSuccess(token);
            }
        }
        catch (Exception e){
            log.error("[POST]auth error ::: {} ", e);
            response = ResponseEntity.fail(500, e.getMessage());
        }

        return response;
    }


    @ApiOperation(value="인증 갱신 API (토큰은 header로)", notes="인증 갱신 API (토큰은 header로)")
    @PostMapping("/refreshtoken")
    public ResponseEntity<ResponseToken> refresh(HttpServletRequest request){
        ResponseEntity<ResponseToken> response = null;

        try{
            // 필수값 검증
            String refreshToken = request.getHeader("refreshToken");


            // 갱신 서비스 호출
            ResponseToken token = service.refresh(refreshToken);

            if(token == null){
                response = ResponseEntity.inputFail("토큰발급 실패");
            }
            else {
                response = ResponseEntity.createSuccess(token);
            }
        }
        catch (Exception e){
            log.error("[POST]auth error ::: {} ", e);
            response = ResponseEntity.fail(500, e.getMessage());
        }

        return response;
    }
}
