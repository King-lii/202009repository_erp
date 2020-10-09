package com.lx.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2  //启用swagger
public class Swagger2AutoConfiguration {

    /**
     * @Api：修饰整个类，描述Controller的作用
     * @ApiOperation：描述一个类的一个方法，或者说一个接口
     * @ApiParam：单个参数描述
     * @ApiModel：用对象来接收参数
     * @ApiProperty：用对象接收参数时，描述对象的一个字段
     * @ApiResponse：HTTP响应其中1个描述
     * @ApiResponses：HTTP响应整体描述
     * @ApiIgnore：使用该注解忽略这个API
     * @ApiError ：发生错误返回的信息
     * @ApiImplicitParam：描述一个请求参数，可以配置参数的中文含义，还可以给参数设置默认值
     * @ApiImplicitParams：描述由多个 @ApiImplicitParam 注解的参数组成的请求参数列表
     *
     * @return
     */
    @Bean
    public Docket swaggerSpringMvcPlugin() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//                .apis(RequestHandlerSelectors.basePackage("com.xwq.controller"))
                .build();
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().description("API工具")
                .contact(new Contact("lx", "", "1774968801@qq.com"))  //名片
                .version("1.0")  //版本
                .license("lx")//所有者
                .build();//构造
    }
}
