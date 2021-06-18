package com.akswosn.tokenapi.interceptor;

import com.akswosn.tokenapi.constants.TokenApiConst;
import com.akswosn.tokenapi.entity.token.TokenInfoEntity;
import com.akswosn.tokenapi.service.AuthService;
import com.akswosn.tokenapi.v1.service.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * 간략 : 토큰 검증 Interceptor
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
@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthService service;
    /**
     * 토큰 검증 interceptor
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String accessToken = request.getHeader(TokenApiConst.HEADER_ACCESS_TOKEN_KEY);
        boolean result = false;

        try{
            //토큰 검증 후 현재 토큰상태 조회
            TokenInfoEntity tokenInfo = service.checkAccess(accessToken);

            if(tokenInfo == null){
                return false;
            }
            // request attribute에 담아 필요시 추출하도록
            log.info("[interceptor] tokenInfo ::: {}", tokenInfo);
            request.setAttribute("tokenInfo",tokenInfo);
            result = true;
            log.info("[interceptor]result ::: {}", result);

        }
        catch (Exception e){
            log.error("Err ::: {}", e);
            return false;
        }

        return result;
    }
}
