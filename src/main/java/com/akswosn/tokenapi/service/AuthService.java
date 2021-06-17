package com.akswosn.tokenapi.service;

import com.akswosn.tokenapi.entity.token.ResponseToken;
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



}
