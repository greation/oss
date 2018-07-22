package com.ekeyfund.oss.core.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 创蓝短信工具类
 * Create by guanglei on 2018/4/17
 */
public class SMSUtils {


    static Map<String,String> message=new HashMap<>();

    static{
        message.put("0","提交成功");
        message.put("101","无此用户");
        message.put("102","密码错");
        message.put("103","提交过快（提交速度超过流速限制）");
        message.put("104","系统忙（因平台侧原因，暂时无法处理提交的短信）");
        message.put("105","敏感短信（短信内容包含敏感词）");
        message.put("106","消息长度错（>536或<=0）");
        message.put("107","包含错误的手机号码");
        message.put("108","手机号码个数错（群发>50000或<=0;单发>200或<=0）");
        message.put("1090","无发送额度（该用户可用短信数已使用完）");
        message.put("110","不在发送时间内");

        message.put("111","超出该账户当月发送额度限制");
        message.put("112","无此产品，用户没有订购该产品");
        message.put("113","extno格式错（非数字或者长度不对）");
        message.put("115","自动审核驳回");
        message.put("116","签名不合法，未带签名（用户必须带签名的前提下）");
        message.put("117","IP地址认证错,请求调用的IP地址不是系统登记的IP地址");
        message.put("118","用户没有相应的发送权限");
        message.put("119","用户已过期");
        message.put("120","测试内容不是白名单");


    }

    /**
     * 获取创蓝短信返回结果消息
     * @param code
     * @return
     */
    public static String getSMSMessage(String code){

        if(StringUtils.isNotEmpty(code)){
            return message.get(code);
        }
        return null;
    }

    /**
     * 根据创蓝短信返回的内容截取到返回码
     * @param responseContent
     * @return
     */
    public static String getSMSCode(String responseContent){

        if(StringUtils.isNotEmpty(responseContent)){

            String[] responseContentArray=responseContent.split("\n");
            String oneLine=responseContentArray[0];
            String code =oneLine.split(",")[1];
            return code;
        }

        return null;
    }

    public static void main(String[] args) {

        String responseContent="20110725160412,117\n" +
                "1234567890100\n";
        System.out.println(getSMSCode(responseContent));
    }
}
