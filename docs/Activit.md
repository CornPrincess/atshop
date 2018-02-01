# Activiti的使用

## springboot + Activiti-rest api
 * 项目搭建
 pom文件中引入：
``` 
            <dependency>
               <groupId>org.activiti</groupId>
               <artifactId>activiti-spring-boot-starter-basic</artifactId>
               <version>${activiti.version}</version>
           </dependency>
           <dependency>
               <groupId>org.postgresql</groupId>
               <artifactId>postgresql</artifactId>
               <scope>runtime</scope>
           </dependency>
           <dependency>
               <groupId>org.activiti</groupId>
               <artifactId>activiti-spring-boot-starter-rest-api</artifactId>
               <version>${activiti.version}</version>
           </dependency>
           <dependency>
               <groupId>org.activiti</groupId>
               <artifactId>activiti-spring-boot-starter-actuator</artifactId>
               <version>${activiti.version}</version>
           </dependency>
           <dependency>
               <groupId>org.activiti</groupId>
               <artifactId>activiti-spring-boot-starter-jpa</artifactId>
               <version>${activiti.version}</version>
           </dependency>
```
 * 运行部署
 ```
 @SpringBootApplication   
 //@EnableEurekaClient
 @EnableAutoConfiguration(exclude = {
         org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
         org.activiti.spring.boot.SecurityAutoConfiguration.class,
         org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
 })    -----activiti具有鉴权，加入排除
 ```
 
 注意需要在resource文件夹中放置bpmn文件，否则报错。 
 
 在activiti-rest中集成了swagger，打开方式：
``` 
 
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.activiti.rest.service"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口列表 v1.1.0") // 任意，请稍微规范点
                .description("接口测试") // 任意，请稍微规范点
                .termsOfServiceUrl("http://url/swagger-ui.html") // 将“url”换成自己的ip:port
                .contact("laowu") // 无所谓（这里是作者的别称）
                .version("1.1.0")
                .build();
    }
}
``` 
