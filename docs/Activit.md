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