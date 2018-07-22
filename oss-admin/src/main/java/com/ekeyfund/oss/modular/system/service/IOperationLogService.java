package com.ekeyfund.oss.modular.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.ekeyfund.oss.modular.system.model.OperationLog;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志 服务类
 * </p>
 *
 * @author stylefeng123
 * @since 2018-02-22
 */
public interface IOperationLogService extends IService<OperationLog> {

    /**
     * 获取操作日志列表
     */
    List<Map<String, Object>> getOperationLogs(Page<OperationLog> page, String beginTime, String endTime, String logName, String s, String orderByField, boolean asc);

    /**
     * 根据查询条件获取操作日志
     * operationLog 操作日志查询条件对象
     * @return
     */
    List<Map<String, Object>> getOperationLogsByCondition(OperationLog operationLog);
}
