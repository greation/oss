package com.ekeyfund.oss.modular.demo.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ekeyfund.oss.generator.mybatisplus.service.impl.BasePageServiceImpl;
import com.ekeyfund.oss.core.constant.DatasourceEnum;
import com.ekeyfund.oss.core.mutidatasource.annotion.DataSource;
import com.ekeyfund.oss.modular.demo.model.Test;
import com.ekeyfund.oss.modular.demo.dao.TestMapper;
import com.ekeyfund.oss.modular.demo.service.ITestService;

/**
 * 测试生成服务实现类
 *
 * @author fengshuonan
 * @Date 2018-05-31 17:31:09
 */
@Service
@DataSource(DatasourceEnum.DATA_SOURCE_YQ_OSS)
public class TestServiceImpl extends BasePageServiceImpl<TestMapper, Test> implements ITestService {

}
