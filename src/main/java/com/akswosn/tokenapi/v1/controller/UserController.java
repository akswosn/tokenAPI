package com.akswosn.tokenapi.v1.controller;

import com.akswosn.tokenapi.entity.ResponseEntity;
import com.akswosn.tokenapi.entity.user.UserEntity;
import com.akswosn.tokenapi.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@Api(value="/api/v1/users",description="사용자 API", tags="Users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private UserService service;

    /**
     * 사용자 전체조회 API Controller
     * @return
     */
    @GetMapping
    @ApiOperation(value="사용자 전체조회 API ", notes="사용자 전체조회 API")
    public ResponseEntity<List<UserEntity>> getUsers(){
        ResponseEntity<List<UserEntity>> response = null;
        try {
            List<UserEntity> users = service.findAll();
            if(users == null){
                response = ResponseEntity.emptyFail();
                return response;
            }
            if(users.size() == 0){
                response = ResponseEntity.emptyFail();
                return response;
            }
            response = ResponseEntity.<List<UserEntity>>selectSuccess(users);
            log.debug("find users ::: {} ", response);

        }
        catch (Exception e){
            log.error("Error getUser ::: {}", e);
            response = ResponseEntity.fail(500, e.getMessage());
        }

        return response;
    }

    @GetMapping("/{id}")
    @ApiOperation(value="사용자 조회 API ", notes="사용자 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "사용자 ID" , required = true, dataType = "String", paramType = "path", defaultValue = "")
    })
    public ResponseEntity<UserEntity> getUser(@PathVariable("id") String id){
        ResponseEntity<UserEntity> response = null;
        try {
            log.debug("id ::: {} ", id);

            if(id == null || "".equals(id)){
                response = ResponseEntity.inputFail("[필수값 누락] 사용자 id ["+id+"]");
                return response;
            }

            UserEntity user = service.findById(id);
            if(user == null){
                response = ResponseEntity.emptyFail();
                return response;
            }
            if(user.getUserId() == null || "".equals(user.getUserId())){
                response = ResponseEntity.emptyFail();
                return response;
            }
            response = ResponseEntity.<UserEntity>selectSuccess(user);
            log.debug("find user ::: {} ", response);

        }
        catch (Exception e){
            log.error("Error getUser ::: {}", e);
            response = ResponseEntity.fail(500, e.getMessage());
        }

        return response;
    }

    @ApiOperation(value="사용자 등록 API ", notes="사용자 등록 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userEntity", value = "사용자 정보" , required = true, dataTypeClass = UserEntity.class, paramType = "body", defaultValue = "")
    })
    @PostMapping("")
    public ResponseEntity<UserEntity> postUser(@RequestBody UserEntity userEntity){
        ResponseEntity<UserEntity> response = null;
        try {
            log.debug("body ::: {} ", userEntity);

            if(userEntity.getUserId() == null || "".equals(userEntity.getUserId())){
                response = ResponseEntity.inputFail("[필수값 누락] 사용자 id ["+userEntity.getUserId()+"]");
                return response;
            }
            if(userEntity.getPassword() == null || "".equals(userEntity.getPassword())){
                response = ResponseEntity.inputFail("[필수값 누락] 사용자 비밀번호 ["+userEntity.getPassword()+"]");
                return response;
            }

            int insertCnt = service.save(userEntity);
            if(insertCnt == 0){
                response = ResponseEntity.inputFail("이미 등록된 사용자가 존재합니다.");
                return response;
            }
            response = ResponseEntity.<UserEntity>createSuccess(userEntity);
            log.debug("save user ::: {} ", response);

        }
        catch (Exception e){
            log.error("Error getUser ::: {}", e);
            response = ResponseEntity.fail(500, e.getMessage());
        }

        return response;
    }

    @ApiOperation(value="사용자 수정 API ", notes="사용자 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "사용자 ID" , required = true, dataType = "String", paramType = "path", defaultValue = "")
            , @ApiImplicitParam(name = "userEntity", value = "사용자 정보" , required = true, dataType = "UserEntity", paramType = "body", defaultValue = "")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserEntity> putUser(@PathVariable("id") String id, @RequestBody UserEntity userEntity){
        ResponseEntity<UserEntity> response = null;
        try {
            log.debug("body ::: {} ", userEntity);

            if(id == null || "".equals(id)){
                response = ResponseEntity.inputFail("[필수값 누락] 사용자 id ["+id+"]");
                return response;
            }
            if(userEntity.getPassword() == null || "".equals(userEntity.getPassword())){
                response = ResponseEntity.inputFail("[필수값 누락] 사용자 비밀번호 ["+userEntity.getPassword()+"]");
                return response;
            }

            service.update(id, userEntity);
            userEntity.setUserId(id);
            response = ResponseEntity.<UserEntity>updateSuccess(userEntity);
            log.debug("update user ::: {} ", response);

        }
        catch (Exception e){
            log.error("Error getUser ::: {}", e);
            response = ResponseEntity.fail(500, e.getMessage());
        }

        return response;
    }
    @ApiOperation(value="사용자 삭제 API ", notes="사용자 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "사용자 ID" , required = true, dataType = "String", paramType = "path", defaultValue = "")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<UserEntity> deleteUser(@PathVariable("id") String id){
        ResponseEntity<UserEntity> response = null;
        try {
            log.debug("id ::: {} ", id);

            if(id == null || "".equals(id)){
                response = ResponseEntity.inputFail("[필수값 누락] 사용자 id ["+id+"]");
                return response;
            }

            UserEntity entity = service.delete(id);
            if(entity == null){
                response = ResponseEntity.emptyFail();
                return response;
            }
            response = ResponseEntity.<UserEntity>updateSuccess(entity);
            log.debug("delete user ::: {} ", response);

        }
        catch (Exception e){
            log.error("Error getUser ::: {}", e);
            response = ResponseEntity.fail(500, e.getMessage());
        }

        return response;
    }

}
