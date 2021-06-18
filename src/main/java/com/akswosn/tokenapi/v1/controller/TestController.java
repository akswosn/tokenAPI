package com.akswosn.tokenapi.v1.controller;

import com.akswosn.tokenapi.entity.ResponseEntity;
import com.akswosn.tokenapi.entity.token.ResponseToken;
import com.akswosn.tokenapi.entity.token.TokenInfoEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 간략 :
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
@RequestMapping("/api/v1/test")
@Api(value="/api/v1/test",description="test API", tags="Test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    @GetMapping("")
    @ApiOperation(value="test API ", notes="test API ")
    public ResponseEntity<TokenInfoEntity> test(HttpServletRequest request){
        log.info("Call [GET] test");
        ResponseEntity<TokenInfoEntity> response = null;
        try{
            TokenInfoEntity data = (TokenInfoEntity)request.getAttribute("tokenInfo");
            log.info("token data :::::::: {}",data);
            response = ResponseEntity.selectSuccess(data);
        }
        catch (Exception e){
            log.error("[POST]auth error ::: {} ", e);
            response = ResponseEntity.fail(500, e.getMessage());
        }

        return response;
    }

}
