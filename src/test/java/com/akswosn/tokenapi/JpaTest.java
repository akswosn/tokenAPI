package com.akswosn.tokenapi;

import com.akswosn.tokenapi.repository.master.user.UserMasterRepository;
import com.akswosn.tokenapi.repository.slave.user.UserSlaveRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class JpaTest {
    @Autowired
    private UserMasterRepository masterRepository;

    @Autowired
    private UserSlaveRepository slaveRepository;



}
