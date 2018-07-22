package com.ekeyfund.oss;

import com.ekeyfund.oss.core.listener.ApplicationEnvironmentPreparedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot方式启动类
 *
 * @author stylefeng
 * @Date 2017/5/21 12:06
 */
@SpringBootApplication
public class OssApplication {

    private final static Logger logger = LoggerFactory.getLogger(OssApplication.class);

    public static void main(String[] args) {
      SpringApplication springApplication=new SpringApplication(OssApplication.class);
      springApplication.addListeners(new ApplicationEnvironmentPreparedListener());
      springApplication.run(args);
        logger.info("GunsApplication is success!");

    }
}
