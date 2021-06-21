package com.akswosn.tokenapi.service.user;

import com.akswosn.tokenapi.entity.user.UserEntity;

import java.util.List;

/**
 * <pre>
 * 간략 : 사용자 service interface (CRUD)
 * 상세 :
 * </pre>
 *
 * @Author : Keun-su(akswosn@gmail)
 * @Date : 2021-06-17
 * @Version : 1.0
 * -----------------------------------
 * 1.0 : 신규작성
 */
public interface UserService {

    public UserEntity findById(String id) throws Exception;

    public int save(UserEntity userEntity) throws Exception;

    public int update(String id, UserEntity userEntity) throws Exception;

    public UserEntity delete(String id) throws Exception;

    public List<UserEntity> findAll() throws Exception;
}
