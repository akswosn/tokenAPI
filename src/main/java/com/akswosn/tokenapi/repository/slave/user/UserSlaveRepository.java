package com.akswosn.tokenapi.repository.slave.user;

import com.akswosn.tokenapi.entity.user.UserEntity;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSlaveRepository extends JpaRepository<UserEntity, String> {
    //slave 시 insert/update/delete 차단
    @SneakyThrows
    @Override
    default UserEntity save(UserEntity user) {
        throw new Exception("Block");
    }
    @SneakyThrows
    @Override
    default void deleteById(String id){
        throw new Exception("Block");
    }
    @SneakyThrows
    @Override
    default void delete(UserEntity user){
        throw new Exception("Block");
    }

    @SneakyThrows
    @Override
    default void deleteAll(){
        throw new Exception("Block");
    }

}
