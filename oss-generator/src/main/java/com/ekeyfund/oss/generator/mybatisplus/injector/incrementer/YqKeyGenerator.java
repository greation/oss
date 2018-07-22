package com.ekeyfund.oss.generator.mybatisplus.injector.incrementer;

import com.baomidou.mybatisplus.incrementer.IKeyGenerator;
import com.ekeyfund.oss.generator.mybatisplus.injector.id.ID;

/**
 * Created by zhaolin on 3/29/2018.
 */
public class YqKeyGenerator implements IKeyGenerator{
    private int length = 18;
    private Class returnClass = Long.class;

    public YqKeyGenerator(int length,Class returnClass){
        this.length = length;
        this.returnClass = returnClass;
    }
    @Override
    public String executeSql(String incrementerName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ").append(newKey());
        return sql.toString();
    }

    public Long newKey(){
        return ID.getInstanse().getID(length);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Class getReturnClass() {
        return returnClass;
    }

    public void setReturnClass(Class returnClass) {
        this.returnClass = returnClass;
    }
}
