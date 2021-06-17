package com.akswosn.tokenapi.entity.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@ApiModel(description = "토큰 발급 정보", value = "Token")
public class ResponseToken {
    @JsonProperty("accessToken")
    @ApiModelProperty(notes = "accessToken", position = 1)
    private String accessToken;
    @JsonProperty("refreshToken")
    @ApiModelProperty(notes = "refreshToken", position = 1)
    private String refreshToken;

}
