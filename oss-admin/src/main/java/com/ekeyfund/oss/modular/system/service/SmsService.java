package com.ekeyfund.oss.modular.system.service;

import com.ekeyfund.oss.modular.system.transfer.SmsSendResponse;

/**
 * Create by guanglei on 2018/4/3
 */
public interface SmsService {


    /**
     *  创兰短信2016版短信接口
     * @param mobile 手机号码，多个号码使用","分割
     * @param msg 短信内容
     * @param needstatus 是否需要状态报告，需要true，不需要false
     * @return 返回值定义参见HTTP协议文档
     * @throws Exception
     */
    String batchSend(String mobile, String msg,
                     boolean needstatus, String product, String extno)throws Exception;


    /**
     * 创蓝短信2017年12月4日版短信接口
     * @param msg
     * @param phone
     * @return
     */
    SmsSendResponse sendSms(String msg, String phone);


}
