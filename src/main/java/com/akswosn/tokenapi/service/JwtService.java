package com.akswosn.tokenapi.service;

import com.akswosn.tokenapi.entity.user.RequestUser;
import com.akswosn.tokenapi.entity.user.UserEntity;
import io.jsonwebtoken.*;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 간략 : Json Web Token 관리 인터페이스
 * 상세 :
 * </pre>
 *
 * @Author : Keun-su(akswosn@gmail)
 * @Date : 2021-06-17
 * @Version : 1.0
 * -----------------------------------
 * 1.0 : 신규작성
 */

public interface JwtService {
    private final static long TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 2; //2시간
    private final static long REFRESH_TOKEN_VALIDATION_SECOND = 1000L * 60 * 60 * 24 * 7; //7일

    public final static String ACCESS_TOKEN_NAME = "accessToken";
    public final static String REFRESH_TOKEN_NAME = "refreshToken";
    private final static String SECRET_KEY="token_secret";

    /**
     * 토큰생성 공통
     * @param userId
     * @param tokenName
     * @return
     */
    default String generatorToken(String tokenName, String userId) throws Exception{
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        Date expireTime  = new Date();
        if(ACCESS_TOKEN_NAME.equals(tokenName)){
            expireTime.setTime(expireTime.getTime() + TOKEN_VALIDATION_SECOND);
        }
        else if(REFRESH_TOKEN_NAME.equals(tokenName)){
            expireTime.setTime(expireTime.getTime() + REFRESH_TOKEN_VALIDATION_SECOND);
        }
        else {
            throw new Exception("정의되지 않은 토큰명");
        }
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //token header 정보 설정
        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("type", "JWT");
        headerMap.put("alg", "HS256");
        
        //사용자 ID 토큰에 저장
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        
        // JWT 토큰발급
        JwtBuilder builder = Jwts.builder().setHeader(headerMap)
                .setClaims(map)
                .setExpiration(expireTime)
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    /**
     * JWT Claims 오브젝트 리턴
     * @param token
     * @return
     * @throws ExpiredJwtException
     * @throws JwtException
     */
    default Claims getClaimsMutator(String token) throws ExpiredJwtException, JwtException{
        return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token).getBody();
    }

    /**
     * Access Token 생성
     * @param userEntity
     * @return
     * @throws Exception
     */
    public String createAccessToken(RequestUser userEntity)throws Exception;

    /**
     * Refresh Token 생성
     * @param userEntity
     * @return
     * @throws Exception
     */
    public String createRefreshToken(RequestUser userEntity)throws Exception;

    /**
     * 토큰 사용여부 (만료시간, 토큰검증)
     * @param jwt
     * @return
     */
    public boolean isUsable(String jwt);

    /**
     * 사용지 ID 조회
     * @param key
     * @return
     * @throws Exception
     */
    public String getUserId(String key) throws Exception;

    /**
     * 만료시간 조회
     * @param key
     * @return
     * @throws Exception
     */
    public LocalDateTime getExpiredTime(String key) throws Exception;
}
