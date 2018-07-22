package com.ekeyfund.oss.core.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.PropertySourcesLoader;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Iterator;

/**
 *  在application.properties之前读取环境变量中oss.yml的值，用于覆盖项目中application.yml的配置
 *  Create by guanglei on 2018/3/28
 */
public class ApplicationEnvironmentPreparedListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationEnvironmentPreparedListener.class);

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        try {
            String path = System.getenv("YQ_OSS_CONFIG_URL_PREFIX");
            Resource resource=new FileSystemResource(path+"/oss.yml");
            PropertySource<?> propertySource = new PropertySourcesLoader().load(resource);
            applicationEnvironmentPreparedEvent.getEnvironment().getPropertySources().addFirst(propertySource);
            Iterator<PropertySource<?>> iter =applicationEnvironmentPreparedEvent.getEnvironment().getPropertySources().iterator();
            while(iter.hasNext()){
                PropertySource<?> propertySourceTmp=iter.next();

                logger.info("property sources "+propertySourceTmp);
            }

        }catch(IOException e){
            throw new IllegalStateException(e);
        }
    }
}
