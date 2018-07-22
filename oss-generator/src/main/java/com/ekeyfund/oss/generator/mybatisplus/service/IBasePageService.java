package com.ekeyfund.oss.generator.mybatisplus.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 模板表 服务类
 * </p>
 *
 * @author ekeyfund123
 * @since 2018-04-08
 */
public interface IBasePageService<T> extends IService<T> {
    /**
     * 获取数据列表
     */
    List<Map<String, Object>> getLists(Page<T> page, String beginTime, String endTime, String value, String orderByField, boolean asc);

}
