package com.ekeyfund.oss.generator.mybatisplus.enums;

/**
 * <pre>
 * 数据状态
 * </pre>
 * 
 * @author weiyuan
 * @version 1.0, 2015-6-4
 */
public enum RowStatus {
    /**
     * 正常
     */
    NORMAL("正常"),
    /**
     * 已过期
     */
    OUTDATE("已过期"),
    /**
     * 已删除
     */
    DELED("已删除");

    private String text;

    RowStatus(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
