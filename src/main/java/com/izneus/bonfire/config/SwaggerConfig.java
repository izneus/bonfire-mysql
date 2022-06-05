package com.izneus.bonfire.config;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * swagger配置文件，配合swagger-ui使用，用不习惯的话，另一个可以选择的ui是knife4j
 * 在线访问地址:
 * 2.x = http://localhost:8080/swagger-ui.html
 * 3.x = http://localhost:8080/swagger-ui/index.html
 *
 * @author Izneus
 * @date 2020/06/28
 */
@Configuration
@EnableSwagger2
@Profile({"dev"})
@RequiredArgsConstructor
public class SwaggerConfig {

    private final BonfireConfig bonfireConfig;

    @Bean
    public Docket createRestApiV1() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("v1")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // .paths(PathSelectors.any())
                .paths(regex("/api/v1/.*"))
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public Docket createRestApiV2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("v2")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(regex("/api/v2/.*"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Bonfire API Doc")
                .description("后端快速开发框架Bonfire的API文档，项目官网Url：http://bonfire.izneus.com/" +
                        "<br>全局Authorize，请求头的Value格式为：Bearer {JWT}，注意中间的1个空格")
                .version(bonfireConfig.getVersion())
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> apiKeys = new ArrayList<>();
        // todo 找不到apiKey写description的地方
        ApiKey apiKey = new ApiKey(
                "Authorization",
                "Authorization",
                "header");
        apiKeys.add(apiKey);
        return apiKeys;
    }

    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                //.forPaths(PathSelectors.regex("^(?!auth).*$"))
                .build());
        return securityContexts;
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
        return securityReferences;
    }

}
