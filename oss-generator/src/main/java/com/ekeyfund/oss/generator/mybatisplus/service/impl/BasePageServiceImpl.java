package com.ekeyfund.oss.generator.mybatisplus.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ekeyfund.oss.generator.mybatisplus.dao.BasePageMapper;
import com.ekeyfund.oss.generator.mybatisplus.service.IBasePageService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 模板表 服务实现类
 * </p>
 *
 * @author ekeyfund123
 * @since 2018-04-08
 */

public class BasePageServiceImpl<M extends BasePageMapper<T>, T> extends ServiceImpl<M, T> implements IBasePageService<T> {
    @Override
    public List<Map<String, Object>> getLists(Page<T> page, String beginTime, String endTime, String value, String orderByField, boolean asc) {
        return this.baseMapper.getLists(page, beginTime, endTime, value, orderByField, asc);
    }

}
