package com.compete.config;

import com.compete.util.IdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class IdWorkerConfig {

    private Logger logger = LoggerFactory.getLogger(IdWorkerConfig.class);

    @Autowired
    private Environment environment;

    @Bean
    public IdWorker idWorker() {
        long workerid = 0L;
        long datacenterid = 0;
        if (environment.getProperty("myConfig.idWorker.workerid") != null
                && !"".equals(environment.getProperty("myConfig.idWorker.workerid"))) {
            workerid = Long.valueOf(environment.getProperty("myConfig.idWorker.workerid"));
        } else {
            logger.info("未配置workerid，默认0");
        }
        if (environment.getProperty("myConfig.idWorker.datacenterid") != null
                && !"".equals(environment.getProperty("myConfig.idWorker.datacenterid"))) {
            datacenterid = Long.valueOf(environment.getProperty("myConfig.idWorker.datacenterid"));
        } else {
            logger.info("未配置datacenterid，默认0");
        }
        return new IdWorker(workerid, datacenterid);
    }

}