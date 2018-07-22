package com.ekeyfund.oss.core.common.exception;

/**
 * 验证码错误异常
 *
 * @author fengshuonan
 * @date 2017-05-05 23:52
 */
public class InvalidKaptchaException extends RuntimeException {
    /**
     * 验证码错误
     */
    public static final int INVALID = 0;
    /**
     * 验证码失效
     */
    public static final int EXPIRED = 1;

    private int reason = INVALID;

    public InvalidKaptchaException(){
        this(INVALID);
    }

    public InvalidKaptchaException(int reason){
        this.reason = reason;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }
}
