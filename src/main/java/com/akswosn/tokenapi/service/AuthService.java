package com.akswosn.tokenapi.service;

import com.akswosn.tokenapi.entity.token.ResponseToken;
import com.akswosn.tokenapi.entity.token.TokenInfoEntity;
import com.akswosn.tokenapi.entity.user.RequestUser;
import com.akswosn.tokenapi.entity.user.UserEntity;

/**
 * <pre>
 * 간략 : 인증 service interface
 * 상세 :
 * </pre>
 *
 * @Author : Keun-su(akswosn@gmail)
 * @Date : 2021-06-17
 * @Version : 1.0
 * -----------------------------------
 * 1.0 : 신규작성
 */
public interface AuthService {

    /**
     * id/password 검증 후 인증 토큰 발급
     * @param userEntity
     * @return
     * @throws Exception
     */
    public ResponseToken auth(RequestUser userEntity) throws Exception;

    /**
     * 토큰 재발급(refreshToken)
     * @param refreshToken
     * @return
     * @throws Exception
     */
    public ResponseToken refresh(String refreshToken) throws Exception;

    /**
     * 토큰 검증(유요한 토큰인지 검증)
     * @param token
     * @return
     */
    public boolean check(String token, String tokenName) throws Exception;

    /**
     * 토큰 엑세스 검증(count 차감)
     * @param accessToken
     * @return
     * @throws Exception
     */
    public TokenInfoEntity checkAccess(String accessToken) throws Exception;
}
