package com.akswosn.tokenapi.entity.token;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDateTime;

/**
 * <pre>
 * 간략 :
 * 상세 :
 * </pre>
 *
 * @Author : Keun-su(akswosn@gmail)
 * @Date : 2021-06-18
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
@ApiModel(description = "토큰정보", value = "TokenInfo")
public class TokenInfoEntity {
    private String token;
    private LocalDateTime expiredTime;
    private long accessCount;

}
