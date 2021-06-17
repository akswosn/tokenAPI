package com.akswosn.tokenapi.repository.master.user;

import com.akswosn.tokenapi.entity.user.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMasterRepository extends CrudRepository<UserEntity, Long> {
}
