package com.oldboy.umeng.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * 地址工具类，通过ip获取相应国家和省份
 */
public class GeoUtil {
	//
	private static Reader reader ;

	private static Map<String , String[]> cache = new HashMap<String, String[]>() ;
	
	static{
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream in = loader.getResourceAsStream("GeoLite2-City.mmdb");
			reader = new Reader(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static String getCountry(String ip)  {
		String[] arr = cache.get(ip) ;
		//
		if(arr == null){
			try {
				JsonNode node = reader.get(InetAddress.getByName(ip)) ;
				String country = node.get("country").get("names").get("zh-CN").asText();
				String prov = node.get("subdivisions").get("names").get("zh-CN").asText();
				cache.put(ip , new String[]{country , prov}) ;
				return country ;
			} catch (Exception e) {
				arr = new String[]{"", ""};
				cache.put(ip, arr);
			}
		}
		return arr[0] ;
	}

	public static String getProvince(String ip)  {
		String[] arr = cache.get(ip) ;
		//
		if(arr == null){
			try {
				JsonNode node = reader.get(InetAddress.getByName(ip)) ;
				String country = node.get("country").get("names").get("zh-CN").asText();
				String prov = node.get("subdivisions").get(0).get("names").get("zh-CN").asText();
				cache.put(ip , new String[]{country , prov}) ;
				return prov ;
			} catch (Exception e) {
				arr = new String[]{"", ""} ;
				cache.put(ip, arr);
			}
		}
		return arr[1] ;
	}

}
