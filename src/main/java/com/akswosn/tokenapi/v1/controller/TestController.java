package com.akswosn.tokenapi.v1.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/v1/test")
@Api(value="/api/v1/test",description="test API", tags="Test", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {
}
