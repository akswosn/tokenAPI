package com.akswosn.tokenapi.v1.service;

import com.akswosn.tokenapi.entity.token.ResponseToken;
import com.akswosn.tokenapi.entity.token.TokenManagerEntity;
import com.akswosn.tokenapi.entity.token.TokenManagerPKEntity;
import com.akswosn.tokenapi.entity.user.RequestUser;
import com.akswosn.tokenapi.entity.user.UserEntity;
import com.akswosn.tokenapi.repository.master.token.TokenMasterRepository;
import com.akswosn.tokenapi.repository.master.user.UserMasterRepository;
import com.akswosn.tokenapi.repository.slave.token.TokenSlaveRespository;
import com.akswosn.tokenapi.service.AuthService;
import com.akswosn.tokenapi.util.TransactionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLTransientException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserMasterRepository userMasterRepository;

    @Autowired
    private TokenMasterRepository masterRepository;

    @Autowired
    private TokenSlaveRespository slaveRespository;

    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private TransactionUtil transactionUtil;

    /**
     * 사용자 정보로 인증 token 발급
     *
     * @param userEntity
     * @return
     */
    @Override
    public ResponseToken auth(RequestUser userEntity) throws Exception {
        AtomicReference<ResponseToken> token = new AtomicReference<>();


        //1. DB 조회
        Optional<UserEntity> option = userMasterRepository.findById(userEntity.getUserId());
        option.ifPresent(select -> {
            try {
                //trans 시작 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                transactionUtil.beginTransaction();

                if (select.getPassword().equals(userEntity.getPassword())) {
                    //2. token 발급
                    String accessToken = jwtService.createAccessToken(userEntity);
                    String refreshToken = jwtService.createRefreshToken(userEntity);
                    log.debug("accessToken :: {}", accessToken);
                    log.debug("refreshToken :: {}", refreshToken);

                    //3. token DB 저장
                    //access 토큰 저장
                    TokenManagerEntity tokenManager = TokenManagerEntity.builder()
                            .accessCount(200).tokenName(JwtServiceImpl.ACCESS_TOKEN_NAME)
                            .token(accessToken).userId(userEntity.getUserId())
                            .build();
                    log.debug("tokenManager access insert/update:: {}", tokenManager);

                    masterRepository.save(tokenManager);

                    //refresh 토큰 저장
                    tokenManager.setToken(refreshToken);
                    tokenManager.setTokenName(JwtServiceImpl.REFRESH_TOKEN_NAME);
                    log.debug("tokenManager refresh insert/update:: {}", tokenManager);
                    masterRepository.save(tokenManager);

                    //4. return 값 세팅
                    token.set(ResponseToken.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build());

                    transactionUtil.commit();
                    //>> trans 종료 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                } else {
                    throw new Exception("비밀번호 불일치");
                }

            } catch (Exception e) {
                try {
                    transactionUtil.rollback();
                } catch (SQLTransientException throwables) {
                    log.error("Service AuthServiceImpl auth transaction err ::: {}", throwables);
                }
                log.error("Service AuthServiceImpl auth ::: {}", e);
                throw new RuntimeException(e);
            }
        });


        log.debug("AuthServiceImpl jwt ::: {} ", token.get());
        return token.get();
    }

    /**
     * refresh 토큰으로 토큰 갱신
     * @param refreshToken
     * @return
     * @throws Exception
     */
    @Override
    public ResponseToken refresh(String refreshToken) throws Exception {
        //1. 토큰검증 호출
        boolean isAvailableToken = check(refreshToken, JwtServiceImpl.REFRESH_TOKEN_NAME);
        log.debug("isAvailableToken ::: {} ", isAvailableToken);
        String userId = jwtService.getUserId(refreshToken);
        ResponseToken result = null;

        if(isAvailableToken){
            //1. accessToken 발급
            String accessToken = jwtService.createAccessToken(RequestUser.builder().userId(userId).build());

            //2. accessToken update
            TokenManagerEntity tokenManager = TokenManagerEntity.builder()
                    .accessCount(200).tokenName(JwtServiceImpl.ACCESS_TOKEN_NAME)
                    .token(accessToken).userId(userId)
                    .build();
            log.debug("tokenManager access update:: {}", tokenManager);

            masterRepository.save(tokenManager);

            //3. token 세팅
            result = ResponseToken.builder()
                    .refreshToken(refreshToken).accessToken(accessToken)
                    .build();
        }

        return result;
    }

    /**
     * 토큰 검증
     *
     * @param token
     * @return
     */
    @Override
    public boolean check(String token, String tokenName) throws Exception {
        //1. 토큰 변조/만료 시간 검증
        boolean isUsable = jwtService.isUsable(token);

        //2. 토큰 user_id 와 token_manager 유효성 체크(토큰,회원 userId, tokenName(access../refresh..) 조회
        String userId = jwtService.getUserId(token);
        Optional<TokenManagerEntity> dbData = slaveRespository.findByIdAndToken(TokenManagerPKEntity.builder()
                .userId(userId).tokenName(tokenName)
                .build(), token);

        if(dbData.isPresent()){
            return true;
        }

        return false;
    }

}