package com.ekeyfund.oss.multidatasource;

import com.ekeyfund.oss.OssApplication;
import com.ekeyfund.oss.core.listener.ApplicationEnvironmentPreparedListener;
import org.springframework.boot.SpringApplication;

/**
 * @author guanglei
 * @create 2018-05-14
 */
public class MultiDataSourceTest {


    public static void main(String[] args) {
        SpringApplication springApplication=new SpringApplication(OssApplication.class);
        springApplication.addListeners(new ApplicationEnvironmentPreparedListener());
        springApplication.run(args);


//        SpringContextHolder.getBean(ITestService.class).testGYP2P();
//        SpringContextHolder.getBean(ITestService.class).testYQOSS();

    }


}
