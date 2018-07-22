package com.ekeyfund.oss.modular.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.ekeyfund.oss.config.properties.YQProperties;
import com.ekeyfund.oss.core.util.ChuangLanSmsUtil;
import com.ekeyfund.oss.modular.system.service.SmsService;
import com.ekeyfund.oss.modular.system.transfer.SmsSendRequest;
import com.ekeyfund.oss.modular.system.transfer.SmsSendResponse;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;


@Service
public class SmsServiceImpl  implements SmsService {


	@Autowired
	private YQProperties yqProperties;

	@Override
	public  String batchSend( String mobile, String msg,
								   boolean needstatus, String product, String extno) throws Exception{
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			URI base = new URI(yqProperties.getUrl(), false);
			method.setURI(new URI(base, "HttpBatchSendSM", false));
			method.setQueryString(new NameValuePair[]{
					new NameValuePair("account", yqProperties.getUserName()),
					new NameValuePair("pswd", yqProperties.getPassword()),
					new NameValuePair("mobile", mobile),
					new NameValuePair("needstatus", String.valueOf(needstatus)),
					new NameValuePair("msg", msg),
					new NameValuePair("product", product),
					new NameValuePair("extno", extno),
			});
			int result = client.executeMethod(method);
			if (result == HttpStatus.SC_OK) {
				InputStream in = method.getResponseBodyAsStream();
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {
					baos.write(buffer, 0, len);
				}
				return URLDecoder.decode(baos.toString(), "UTF-8");
			} else {
				throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
			}
		} finally {
			method.releaseConnection();
		}
	}

	@Override
	public SmsSendResponse sendSms(String message, String phone) {
		SmsSendRequest smsSendRequest=new SmsSendRequest(yqProperties.getUserName(),yqProperties.getPassword(),message,phone);
		String requestJson = JSON.toJSONString(smsSendRequest);
		String response = ChuangLanSmsUtil.sendSmsByPost(yqProperties.getUrl(), requestJson);
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		return smsSingleResponse;
	}



	}

