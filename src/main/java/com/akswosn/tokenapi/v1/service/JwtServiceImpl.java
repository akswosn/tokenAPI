package com.akswosn.tokenapi.v1.service;

import com.akswosn.tokenapi.entity.user.RequestUser;
import com.akswosn.tokenapi.entity.user.UserEntity;
import com.akswosn.tokenapi.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.xml.bind.DatatypeConverter;
import java.util.LinkedHashMap;
import java.util.Map;

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

    /**
     * 토큰 검증
     * @param jwt
     * @return
     */
    public boolean isUsable(String jwt){
        try {
            Claims claims = getClaimsMutator(jwt);

            log.debug("토큰 정상");
            log.debug("expireTime :" + claims.getExpiration());

            return true;
        } catch (ExpiredJwtException exception) { // 토큰 기간 만료
            log.error("token chack ::: {}", exception);
            return false;
        } catch (JwtException exception) { // 토큰 변조
            log.error("token chack ::: {}", exception);
            return false;
        }
    }

    /**
     * token에 저장된 userId 추출
     * @param key
     * @return
     * @throws Exception
     */
    public String getUserId(String key) throws Exception {
        try {
            Claims claims = getClaimsMutator(key);

            Map<String, Object> value = (LinkedHashMap<String, Object>)claims.get("userId");
            log.debug("value ::: {}",value);

        } catch (Exception e) {
            log.error("getUserId error::: {} ", e);
            throw new Exception("사용자 죄회 실패");
        }
        return null;
    }
}
