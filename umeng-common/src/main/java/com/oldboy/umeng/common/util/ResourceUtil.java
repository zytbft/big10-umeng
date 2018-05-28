package com.oldboy.umeng.common.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源工具类
 */
public class ResourceUtil {

	/**
	 * 加载资源串
	 */
	public static String loadResoureString(String file){
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
			byte[] buf = new byte[1024];
			int len = -1 ;
			while((len = in.read(buf)) != -1){
				baos.write(buf , 0 , len);
			}
			baos.close();
			in.close();
			return new String(baos.toByteArray()) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "" ;
	}

	/**
	 * 加载资源串
	 */
	public static List<String> loadResoureLines(String file){
		List<String> lines = new ArrayList<String>() ;
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(in)) ;
			String line = null ;
			while((line = br.readLine()) != null){
				if(!line.trim().equals("")){
					lines.add(line) ;
				}
			}
			br.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines ;
	}
}
