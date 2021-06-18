package com.akswosn.tokenapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>
 * 간략 : SwaggerConfig
 * 상세 :
 * </pre>
 *
 * @Author : Keun-su(akswosn@gmail)
 * @Date : 2021-06-17
 * @Version : 1.0
 * -----------------------------------
 * 1.0 : 신규작성
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.akswosn.tokenapi.v1.controller"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .securitySchemes(apiKeys())
                .securityContexts(Collections.singletonList(securityContext()));

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("TokenAPI")
                .description("TEST !!!!")
                .version("1.0.0")
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/api/v1(/auth/refreshtoken|/test).*"))
                .build();
    }

    /**
     * controller request 별설정
     * @return
     */
    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        List<SecurityReference> result = new ArrayList<>();
        result.add(new SecurityReference("accessToken", authorizationScopes));
        result.add(new SecurityReference("refreshToken", authorizationScopes));
        return result;
    }

    /**
     * Authrorize 버튼 설정
     * @return
     */
    private List<SecurityScheme> apiKeys() {
        List<SecurityScheme> list = new ArrayList<>();
        list.add(new ApiKey("accessToken", "accessToken", "header"));
        list.add(new ApiKey("refreshToken", "refreshToken", "header"));
        return list;
    }
}
