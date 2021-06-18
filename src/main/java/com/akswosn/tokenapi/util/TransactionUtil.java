package com.akswosn.tokenapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLTransientException;

/**
 * <pre>
 * 간략 : 트랜잭션 util
 * 상세 :
 * </pre>
 *
 * @Author : Keun-su(akswosn@gmail)
 * @Date : 2021-06-17
 * @Version : 1.0
 * -----------------------------------
 * 1.0 : 신규작성
 */
@Component
public class TransactionUtil {
    @Autowired
    private PlatformTransactionManager txManager;


    private TransactionStatus tranStatus;

    /**
     * 트랜잭션 시작 시점 및 commit/rollback 작업을 위한 준비 단계 선언
     * 해당 메서드가 호출되지 않으면 트랜잭션이 동작하지 않음
     */
    public void beginTransaction(){
        this.tranStatus = txManager.getTransaction(getDefaultTransactionDefinition());
    }

    public TransactionStatus getTransactionStatus(){
        return this.tranStatus;
    }


    public DefaultTransactionDefinition getDefaultTransactionDefinition(){
        DefaultTransactionDefinition td =
                new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
        td.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        td.setTimeout(10);
        return td;
    }

    /**
     * commit
     * @throws SQLTransientException
     */
    public void commit() throws SQLTransientException {
        if(tranStatus == null){
            throw new SQLTransientException("Commit 실행 불가 상태입니다.(PlatformTransactionManager is null)");
        }
        txManager.commit(tranStatus);

    }

    /**
     * Rollback
     * @throws SQLTransientException
     */
    public void rollback() throws SQLTransientException{
        if(tranStatus == null){
            throw new SQLTransientException("rollback 실행 불가 상태입니다.(PlatformTransactionManager is null)");
        }
        txManager.rollback(tranStatus);
    }
}
