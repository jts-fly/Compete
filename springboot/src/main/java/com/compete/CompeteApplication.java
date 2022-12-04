package com.compete;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.compete.dao")
@SpringBootApplication
public class CompeteApplication {

    private static Logger logger = LoggerFactory.getLogger(CompeteApplication.class);

    public static void main(String[] args) {
        logger.info("大学生竞赛管理系统开始启动");
        SpringApplication.run(CompeteApplication.class, args);
        logger.info("大学生竞赛管理系统启动成功");
    }

}
