package com.akswosn.tokenapi.repository.master.token;

import com.akswosn.tokenapi.entity.token.TokenManagerEntity;
import com.akswosn.tokenapi.entity.token.TokenManagerPKEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
/**
 * <pre>
 * 간략 : 토큰 매니저 레포지토리
 * 상세 :
 * </pre>
 *
 * @Author : Keun-su(akswosn@gmail)
 * @Date : 2021-06-17
 * @Version : 1.0
 * -----------------------------------
 * 1.0 : 신규작성
 */
public interface TokenMasterRepository extends JpaRepository<TokenManagerEntity, TokenManagerPKEntity> {
}
