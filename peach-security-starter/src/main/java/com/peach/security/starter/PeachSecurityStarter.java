package com.peach.security.starter;


import com.peach.common.anno.MyBatisDao;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Indexed;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Author Mr Shu
 * @Version 1.0.0
 * @Description //TODO
 * @CreateTime 06 3月 2025 22:23
 */
@Slf4j
@Indexed
@Configuration
@ComponentScan(basePackages = {"com.peach.security",}, lazyInit = true)
@MapperScan(lazyInitialization = "true", basePackages = "com.peach.security.dao",
        annotationClass = MyBatisDao.class,sqlSessionFactoryRef = "mybatis-session")
public class PeachSecurityStarter {

    /**
     * 注册权限校验模块接口文档
     * @return
     */
    @Lazy
    @Bean
    public Docket securityApi() {
        Contact contact = new Contact("Ryan","https://github.com/Ryan-Guizhou","huanhuanshu48@gmail.com");
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("PEACH-API文档")
                        .description("PEACH-API文档")
                        .contact(contact)
                        .version("PEACH-1.0.0")
                        .build())
                //分组名称
                .groupName("AUTH_API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.peach.security"))
                .build();
        log.info("knife4j AUTH_API  has been configured");
        return docket;
    }


}
