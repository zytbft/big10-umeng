package com.oldboy.umeng.phone;

import com.alibaba.fastjson.JSON;
import com.oldboy.umeng.common.domain.AppLogAggEntity;
import com.oldboy.umeng.common.util.LogUtil;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;

/**
 * 手机端程序类,收集本地日志，聚合成json，上报给web服务器
 */
public class ClientApp {
	public static void main(String[] args) throws Exception {
		while (true) {
			AppLogAggEntity e = LogUtil.genAppLogAgg();
			String json = JSON.toJSONString(e) ;
			uploadJson(json) ;
			Thread.sleep(1000);
		}
	}

	/**
	 * 上传消息给服务期
	 */
	private static void uploadJson(String json) {
		String url = "http://192.168.13.9:80/" ;
		URL u = null ;
		HttpURLConnection conn = null ;
		//设置提交方式
		try {
			u = new URL(url);
			conn = (HttpURLConnection) u.openConnection();
			conn.setRequestMethod("POST");
			//设置请求的格式
			conn.setRequestProperty("content-type", "application/json");

			//设置客户端时间，为了时间对齐
			conn.setRequestProperty("client_time" , new Date().getTime() + "");

			//设置允许输出
			conn.setDoOutput(true);
			OutputStream out = conn.getOutputStream();
			out.write(json.getBytes());
			out.flush();
			out.close();
			int code = conn.getResponseCode();
			//ok
			if (code == 200) {
				System.out.println("发送成功 : " + json);
			} else {
				System.out.println("发送失败 : " + code);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
