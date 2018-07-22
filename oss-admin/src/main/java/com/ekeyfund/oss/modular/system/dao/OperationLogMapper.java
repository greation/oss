package com.ekeyfund.oss.modular.system.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ekeyfund.oss.modular.system.model.OperationLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2017-07-11
 */
public interface OperationLogMapper extends BaseMapper<OperationLog> {

    /**
     * 获取操作日志
     */
    List<Map<String, Object>> getOperationLogs(@Param("page") Page<OperationLog> page, @Param("beginTime") String beginTime, @Param("endTime") String endTime, @Param("logName") String logName, @Param("logType") String logType, @Param("orderByField") String orderByField, @Param("isAsc") boolean isAsc);

    /**
     * 根据查询条件获取操作日志
     * @param logType  日志类型
     * @param modelClass  所在类
     * @param keyValue  关联数据ID
     * @return
     */
    List<Map<String, Object>> getOperationLogsByCondition(@Param("logType") String logType, @Param("modelClass") String modelClass, @Param("keyValue") String keyValue);
}