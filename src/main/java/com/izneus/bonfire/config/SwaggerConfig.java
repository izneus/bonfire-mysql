package com.izneus.bonfire.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static java.util.Collections.singletonList;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * swagger配置文件，配合swagger-ui使用，用不习惯的话，另一个可以选择的ui是knife4j
 * 在线访问地址: http://localhost:8080/swagger-ui.html
 *
 * @author Izneus
 * @date 2020/06/28
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
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
                .globalOperationParameters(
                        singletonList(new ParameterBuilder()
                                .name("Authorization")
                                .description("Bearer <jwt>")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(false)
                                .defaultValue("Bearer ")
                                .build()));
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
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Bonfire API Doc")
                .description("后端快速开发框架Bonfire的API文档，项目官网Url：[todo]")
                .version("0.0.1")
                .build();
    }
}
