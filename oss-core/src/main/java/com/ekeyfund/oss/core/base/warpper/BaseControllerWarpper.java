package com.ekeyfund.oss.core.base.warpper;

import com.ekeyfund.oss.core.util.DateUtil;
import com.ekeyfund.oss.core.util.ToolUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 控制器查询结果的包装类基类
 *
 * @author fengshuonan
 * @date 2017年2月13日 下午10:49:36
 */
public abstract class BaseControllerWarpper {

    public Object obj = null;

    public BaseControllerWarpper(Object obj) {
        this.obj = obj;
    }

    @SuppressWarnings("unchecked")
    public Object warp() {
        if (this.obj instanceof List) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) this.obj;
            for (Map<String, Object> map : list) {
                defaultWrap(map);
                warpTheMap(map);
            }
            return list;
        } else if (this.obj instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) this.obj;
            defaultWrap(map);
            warpTheMap(map);
            return map;
        } else {
            return this.obj;
        }
    }

    /**
     * 默认对创建时间和更新时间进行格式编码
     * @param map
     */
    public void defaultWrap(Map map){
        Iterator keyIter = map.entrySet().iterator();
        while(keyIter.hasNext()){
            Map.Entry entry = ( Map.Entry)keyIter.next();
            if(entry.getValue() instanceof Date){
                if (ToolUtil.isEmpty((Date)entry.getValue())) {
                    //map.put(entry.getKey(), "--");
                    entry.setValue("--");
                } else {
                    //map.put(entry.getKey(), DateUtil.formatDate((Date)entry.getValue(), "yyyy/MM/dd HH:mm:ss"));
                    entry.setValue(DateUtil.formatDate((Date)entry.getValue(), "yyyy/MM/dd HH:mm:ss"));
                }
            }
        }
    }

    protected abstract void warpTheMap(Map<String, Object> map);
}
