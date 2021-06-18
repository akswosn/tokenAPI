package com.akswosn.tokenapi.entity.user;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <pre>
 * 간략 : 사용자 Entity
 * 상세 :
 * </pre>
 *
 * @Author : Keun-su(akswosn@gmail)
 * @Date : 2021-06-17
 * @Version : 1.0
 * -----------------------------------
 * 1.0 : 신규작성
 */
@Entity(name = "test_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@ToString
public class UserEntity implements Serializable {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

}
