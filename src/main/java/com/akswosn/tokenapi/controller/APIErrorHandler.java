package com.akswosn.tokenapi.controller;

import com.akswosn.tokenapi.entity.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 간략 : Common Error Handler
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
@Component
public class APIErrorHandler extends DefaultErrorAttributes {

    private Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> result = super.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        log.error("APIErrorHandler Result ::: {} ", result);

        return makeBaseResponse(result);
//        return null;
    }

    /**
     * Map => BaseResponse => Map Convertor
     * @param err
     * @return
     */
    private Map<String, Object> makeBaseResponse(Map<String, Object> err){

        String msg =err.get("message").toString();
        String code =err.get("status").toString();
        int intbvalCode = 0;
        try {
            intbvalCode = Integer.parseInt(code);
        }
        catch (Exception e){
            intbvalCode = 500;  //server interval err
        }

        ResponseEntity<Object> baseResponse = ResponseEntity.builder()
                .message(msg).code(intbvalCode)
                .build();

        return  getCommonErrMap(baseResponse);
    }

    /**
     * ResponseEntity To Map
     * @param baseResponse
     * @return
     */
    public Map<String, Object> getCommonErrMap(ResponseEntity baseResponse){
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> header = new HashMap<>();
        result.put("data",null);
        result.put("message", baseResponse.getMessage());
        result.put("code", baseResponse.getCode());

        return result;
    }
}
