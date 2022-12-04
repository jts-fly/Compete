package com.compete.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SystemCache implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SystemCache.class);

    /**登录用户<userid,token>*/
    private static Map<String, String> loginUserMap;

    /**登录信息<token,<>>*/
    private static Map<String, Map<String, String>> loginInfoMap;


    @Override
    public void run(String... args) {
        logger.info("加载系统缓存开始");
        loginUserMap = new HashMap<>();
        loginInfoMap = new HashMap<>();
        logger.info("加载系统缓存成功");
    }

    public static Map<String, String> getLoginUserMap() {
        return loginUserMap;
    }

    public static void setLoginUserMap(Map<String, String> loginUserMap) {
        SystemCache.loginUserMap = loginUserMap;
    }

    public static Map<String, Map<String, String>> getLoginInfoMap() {
        return loginInfoMap;
    }

    public static void setLoginInfoMap(Map<String, Map<String, String>> loginInfoMap) {
        SystemCache.loginInfoMap = loginInfoMap;
    }
}
