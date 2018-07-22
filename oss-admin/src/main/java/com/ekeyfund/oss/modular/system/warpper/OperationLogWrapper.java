package com.ekeyfund.oss.modular.system.warpper;

import com.ekeyfund.oss.core.base.warpper.BaseControllerWarpper;

import java.util.List;
import java.util.Map;

/**
 * 操作日志列表的包装类
 *
 * @author chenjie
 * @date 2017年4月17日11:25:32
 */
public class OperationLogWrapper extends BaseControllerWarpper {

    public OperationLogWrapper(List<Map<String, Object>> list) {
        super(list);
    }

    @Override
    protected void warpTheMap(Map<String, Object> map) {

    }
}
