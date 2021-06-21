package com.akswosn.tokenapi.v1.service;

import com.akswosn.tokenapi.entity.user.UserEntity;
import com.akswosn.tokenapi.repository.master.user.UserMasterRepository;
import com.akswosn.tokenapi.repository.slave.user.UserSlaveRepository;
import com.akswosn.tokenapi.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 * 간략 : 사용자 서비스
 * 상세 :
 * </pre>
 *
 * @Author : Keun-su(akswosn@gmail)
 * @Date : 2021-06-21
 * @Version : 1.0
 * -----------------------------------
 * 1.0 : 신규작성
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMasterRepository masterRepository;

    @Autowired
    private UserSlaveRepository slaveRepository;


    /**
     * 사용자 정보조회 서비스
     * @param id
     * @return
     */
    @Override
    public UserEntity findById(String id) throws Exception{
        AtomicReference<UserEntity> result = new AtomicReference<>(new UserEntity());

        Optional<UserEntity> optional = slaveRepository.findById(id);
        optional.ifPresent(selectUser ->{
            result.set(selectUser);
        });

        return result.get();
    }

    /**
     * 사용자 등록 서비스
     * @param userEntity
     * @return
     */
    @Override
    public int save(UserEntity userEntity) throws Exception {
        int result = 0;
        Optional<UserEntity> optional = slaveRepository.findById(userEntity.getUserId());

        if(optional.isPresent()){
            return result;
        }

        masterRepository.save(userEntity);
        result++;
        return result;
    }


    /**
     * 사용자 수정 서비스
     * @param id
     * @param userEntity
     */
    @Override
    public int update(String id, UserEntity userEntity) throws Exception{
        Optional<UserEntity> user = slaveRepository.findById(id);
        AtomicInteger count = new AtomicInteger(0);
        user.ifPresent(selectUser ->{
            selectUser.setPassword(userEntity.getPassword());
            masterRepository.save(selectUser);
            count.getAndIncrement();
        });
        return count.get();
    }

    /**
     * 사용자 삭제 서비스
     * @param id
     * @return
     */
    @Override
    public UserEntity delete(String id) throws Exception{
        Optional<UserEntity> user = slaveRepository.findById(id);

        if(!user.isPresent()){  //삭제할 데이터 없음
            return null;
        }

        user.ifPresent(selectUser ->{
            masterRepository.delete(selectUser);
        });

        return user.get();
    }

    /**
     * 사용자 전체조회
     * @return
     */
    @Override
    public List<UserEntity> findAll() throws Exception {
        return slaveRepository.findAll();
    }
}
