package com.akswosn.tokenapi.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * <pre>
 * 간략 : 요청 사용자 정보
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
@ApiModel(description = "로그인 정보", value = "RequestUser")
public class RequestUser implements Serializable {
    @ApiModelProperty(notes = "사용자 ID",  position = 1, example="")
    private String userId;

    @ApiModelProperty(notes = "비밀번호",  position = 2, example="")
    private String password;
}
