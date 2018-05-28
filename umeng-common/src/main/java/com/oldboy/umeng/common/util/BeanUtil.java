package com.oldboy.umeng.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * bean工具类
 */
public class BeanUtil {

	/**
	 * 生成所有日志子表的sql
	 */
	public static List<String> genDDLs(Class[] clazz, String[] tables){
		List<String> sqls = new ArrayList<String>() ;
		for(int i = 0 ; i < clazz.length ; i ++){
			sqls.add(genDDL(clazz[i] , tables[i])) ;
		}
		return sqls ;
	}

	/**
	 * 生成创建日志子表的ddl语句，使用parquet格式分区表
	 * create table if not exists xxxx( ... ) partitioned by (ym, day, hm) stored as parquet ;
	 */
	public static String genDDL(Class clazz , String table) {
		StringBuilder builder = new StringBuilder() ;
		builder.append("create table if not exists ") ;
		builder.append(table) ;
		builder.append("(") ;
		try {
			BeanInfo bi = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] pps = bi.getPropertyDescriptors();
			for (int i = 0 ; i < pps.length ; i ++) {
				PropertyDescriptor pp = pps[i] ;
				Method getter = pp.getReadMethod();
				Method setter = pp.getWriteMethod();
				if (getter != null && setter != null) {
					String name = pp.getName();
					Class ptype = pp.getPropertyType();
					//最后一次
					if(i == pps.length - 1){

						if (ptype == String.class) {
							builder.append(name + " string") ;
						}
						else if (ptype == int.class || ptype == Integer.class) {
							builder.append(name + " int");
						}
						else if (ptype == long.class || ptype == Long.class) {
							builder.append(name + " bigint");
						}
					}
					else{
						if (ptype == String.class) {
							builder.append(name + " string ,");
						} else if (ptype == int.class || ptype == Integer.class) {
							builder.append(name + " int ,");
						} else if (ptype == long.class || ptype == Long.class) {
							builder.append(name + " bigint ,");
						}
					}
				}
			}
			builder.append(") partitioned by (ym int ,day int , hm int) stored as parquet") ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return builder.toString() ;
	}
}
