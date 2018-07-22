package com.ekeyfund.oss.generator.mybatisplus.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 模板表 Mapper 接口
 * </p>
 *
 * @author ekeyfund123
 * @since 2018-04-08
 */
public interface BasePageMapper<T> extends BaseMapper<T> {
    /**
     * 获取数据
     */
    List<Map<String, Object>> getLists(@Param("page") Page<T> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("value") String value, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

}
