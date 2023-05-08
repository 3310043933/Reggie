package com.liwenjie.reggie1;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan("com.liwenjie.reggie1.filter")        // 开启原生javaWeb
@EnableTransactionManagement        // 开启事务支持
public class Reggie1Application {

    public static void main(String[] args) {
        SpringApplication.run(Reggie1Application.class, args);
        log.info("项目启动成功");
    }
}