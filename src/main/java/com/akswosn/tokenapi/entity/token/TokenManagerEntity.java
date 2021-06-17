package com.akswosn.tokenapi.entity.token;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;

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
@Entity(name = "token_manager")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@IdClass(TokenManagerPKEntity.class)
public class TokenManagerEntity implements Serializable {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "token_name")
    private String tokenName;

    @Column(name="token")
    private String token;
    
    @Column(name="access_count")
    private int accessCount;

}
