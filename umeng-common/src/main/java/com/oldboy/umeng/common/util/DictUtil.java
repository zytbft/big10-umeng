package com.oldboy.umeng.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 编写字典工具类，提取各种属性值的.
 */
public class DictUtil {

	//存放所有属性和值的集合
	private static Map<String , List<String>> dict = new HashMap<String, List<String>>() ;

	//随机对象
	private static Random r = new Random() ;

	//初始化字典集合
	static{
		//字节流
		try {
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("dict.dat") ;
			//转换流到缓冲流
			BufferedReader br = new BufferedReader(new InputStreamReader(in)) ;
			String line = null ;

			//每个属性的值集合
			List<String> propValues = null ;

			//读行
			while((line = br.readLine()) != null){
				String tmp  = line.trim() ;

				//新属性
				if(!tmp.equals("") && tmp.startsWith("[")){
					String propName = tmp.substring(1 , tmp.length() - 1);
					propValues = new ArrayList<String>() ;
					//转成小写
					dict.put(propName.toLowerCase() , propValues) ;
				}
				else if(!tmp.equals("") && !tmp.startsWith("[")){
					propValues.add(tmp) ;
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到字典本省
	 */
	public static Map<String, List<String>> getDict(){
		return dict ;
	}

	/**
	 * 从dict中的指定属性随机提取一个String值
	 */
	public static String getRandString(String propName){
		List<String> list = dict.get(propName.toLowerCase()) ;
		if(list == null || list.isEmpty()){
			return null ;
		}
		return list.get(r.nextInt(list.size())) ;
	}

	/**
	 * 从dict中的指定属性随机提取一个Integer值
	 */
	public static Integer getRandInt(String propName){
		List<String> list = dict.get(propName.toLowerCase()) ;
		if(list == null || list.isEmpty()){
			return null ;
		}
		return Integer.parseInt(list.get(r.nextInt(list.size()))) ;
	}
}
