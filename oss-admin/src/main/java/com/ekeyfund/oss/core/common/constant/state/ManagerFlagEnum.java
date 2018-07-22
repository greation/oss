package com.ekeyfund.oss.core.common.constant.state;

/**
 * Create by guanglei on 2018/4/10
 */
public enum ManagerFlagEnum {

    YES(1, "是"), NO(0, "否");

    int code;

    String desc;

    ManagerFlagEnum(int code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static String valueOf(Integer code) {
        if (code == null) {
            return "";
        } else {
            for (ManagerFlagEnum  mf : ManagerFlagEnum.values()) {
                if (mf.getCode() == code) {
                    return mf.getDesc();
                }
            }
            return "";
        }
    }

}
