package com.akswosn.tokenapi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <pre>
 * 간략 : 공통 Response엔티티
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
@Getter @ToString
@ApiModel(value="Response", description = "공통 Response")
public class ResponseEntity<T> {

    @Builder.Default
    @ApiModelProperty(notes = "결과 상태 코드", example="", position = 1)
    private int code = 200;
    @ApiModelProperty(notes = "결과 상태 메세지", example="", position = 2)
    private String message;
    @ApiModelProperty(notes = "결과 데이터", example="", position = 1)
    private T data;

    /**
     * 200 : 정상
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> selectSuccess(T data){
        return ResponseEntity.<T>builder()
                .code(200).message("SUCCESS").data(data)
                .build();
    }

    /**
     * 클라이언트 오류 : 400
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> inputFail(String msg){
        return ResponseEntity.<T>builder()
                .code(400).message(msg)
                .build();
    }

    /**
     * 401 : 인증, 권한 오류
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> authFail(String msg){
        return ResponseEntity.<T>builder()
                .code(401).message(msg)
                .build();
    }
    /**
     * 404 : not Found(페이지 요청 실패)
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> notFoundFail(String msg){
        return ResponseEntity.<T>builder()
                .code(404).message(msg)
                .build();
    }

    /**
     * 204 : 데이터 존재하지 않음
     * @return
     */
    public static <T> ResponseEntity<T> emptyFail(){
        return ResponseEntity.<T>builder()
                .code(204).message("정보가 존재하지 않습니다.")
                .build();
    }

    /**
     * custom fail
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> fail(int code, String msg){
        return ResponseEntity.<T>builder()
                .code(code).message(msg)
                .build();
    }

    /**
     * 201 : 등록성공
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> createSuccess(T data){
        return ResponseEntity.<T>builder()
                .code(201).message("SUCCESS").data(data)
                .build();
    }

    /**
     * 205 : 변경성공
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseEntity<T> updateSuccess(T data){
        return ResponseEntity.<T>builder()
                .code(205).message("SUCCESS").data(data)
                .build();
    }
}
