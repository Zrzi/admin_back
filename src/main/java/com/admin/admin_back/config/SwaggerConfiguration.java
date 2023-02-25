package com.admin.admin_back.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 陈群矜
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(prefix = "admin", name = "swagger", havingValue = "true", matchIfMissing = false)
public class SwaggerConfiguration {

    @Bean
    public Docket docket() {
        Contact defaultContact = new Contact("陈群矜", "", "1691876150@qq.com");
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        ApiInfo apiInfo = apiInfoBuilder
                .title("权限管理、excel上传接口文档")
                .contact(defaultContact)
                .version("1.0.0")
                .description("权限管理、excel上传相关接口")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.admin.admin_back.controller"))
                .build();
    }

}
