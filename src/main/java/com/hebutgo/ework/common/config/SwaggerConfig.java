package com.hebutgo.ework.common.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author tianziyi
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean(value = "defaultApi")
    public Docket defaultApi() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("0.1版本")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.hebutgo"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hebutgo"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
//        http://localhost:8081/swagger-ui.html
        return new ApiInfoBuilder()
                .title("hebutgo作业系统")
                .description("作业系统，接口文档")
                .termsOfServiceUrl("hebutgo.com")
                .contact(new Contact("田梓毅", "", "tianziyi2000@yeah.net"))
                .version("1.0")
                .build();
    }

}
