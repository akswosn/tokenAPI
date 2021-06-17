package com.akswosn.tokenapi.entity.token;

import lombok.*;

import javax.persistence.Id;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TokenManagerPKEntity implements Serializable {
    private String userId;
    private String tokenName;
}
