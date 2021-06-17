package com.akswosn.tokenapi.v1.service;

import com.akswosn.tokenapi.entity.user.RequestUser;
import com.akswosn.tokenapi.entity.user.UserEntity;
import com.akswosn.tokenapi.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * 간략 : Json Web Token 관리 서비스
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
@Service
public class JwtServiceImpl implements JwtService {
    /**
     * AccessToken 생성
     * @param userEntity
     * @return
     * @throws Exception
     */
    @Override
    public String createAccessToken(RequestUser userEntity) throws Exception {
        return generatorToken(ACCESS_TOKEN_NAME, userEntity.getUserId());
    }

    /**
     * refresh 토큰 생성
     * @param userEntity
     * @return
     * @throws Exception
     */
    @Override
    public String createRefreshToken(RequestUser userEntity) throws Exception {
        return generatorToken(REFRESH_TOKEN_NAME, userEntity.getUserId());
    }
}
