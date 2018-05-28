package com.oldboy.umeng.common.util;

import com.oldboy.umeng.common.domain.AppBaseLog;
import com.oldboy.umeng.common.domain.AppLogAggEntity;
import com.oldboy.umeng.common.domain.AppStartupLog;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 日志工具类
 */
public class LogUtil {
	/**
	 * 按照类生成对应的日志对象 , 通过内省方式复赋值属性
	 */
	public static <T> T genInstance(Class<T> clazz){
		try {
			//实例化对象
			T  t = clazz.newInstance();
			//得到bean信息
			BeanInfo bi = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] pps = bi.getPropertyDescriptors();
			for(PropertyDescriptor pp : pps){
				//属性名
				String propName = pp.getName();
				//setXxxx
				Method setter = pp.getWriteMethod();
				//属性类型
				Class ptype = pp.getPropertyType() ;

				if(setter != null){
					//String
					if(ptype == String.class){
						String value = DictUtil.getRandString(propName);
						setter.invoke(t , value) ;
					}
					else if(ptype == int.class || ptype == Integer.class){
						Integer value = DictUtil.getRandInt(propName);
						setter.invoke(t,value) ;
					}
				}

				//模拟半年时间的日志
				if(propName.equals("createdAtMs")){
					long currtime = System.currentTimeMillis() ;
					long dur = 6 * 30 * 24 * 60 * 60 * 1000 ;
					Random r = new Random() ;
					long offset = (long)r.nextFloat() * dur ;
					((AppBaseLog)t).setCreatedAtMs(currtime - offset);
				}
			}
			return t ;
		} catch (Exception e) {
		}
		return null ;
	}

	/**
	 * 生成实例列表，随机生成最多max个日志，最少一个.
	 */
	public static <T> List<T> genIntances(Class<T> clazz , int max){
		List<T> list = new ArrayList<T>() ;
		Random r = new Random() ;
		int n = r.nextInt(max) + 1 ;
		for(int i = 0 ; i < n ; i ++){
			list.add(genInstance(clazz)) ;
		}
		return list ;
	}

	/**
	 * 生成日志聚合体
	 */
	public static AppLogAggEntity genAppLogAgg() throws IllegalAccessException {
		AppLogAggEntity e = genInstance(AppLogAggEntity.class) ;
		Random r = new Random() ;
		int devid = r.nextInt(80000) ;

		DecimalFormat df = new DecimalFormat("0000000") ;
		String devidStr = df.format(devid) ;

		//设置设备id
		e.setDeviceId("dev" + devidStr);

		Field[] fs = e.getClass().getDeclaredFields();
		for(Field f : fs){
			//得到字段类型 , List<>
			Class ftype = f.getType() ;
			if(ftype == List.class){
				// 得到泛型化类型
				ParameterizedType ptype = (ParameterizedType) f.getGenericType();
				Class clazz0 = (Class) ptype.getActualTypeArguments()[0];
				List list = genIntances(clazz0,5);
				f.setAccessible(true);
				f.set(e , list);
			}
		}
		return e ;
	}

	/**
	 * 合并src对象属性到目标集合中的每个元素中
	 */
	public static void mergeProperties1(Object src, List<? extends Object> list) {
		for(Object dest : list){
			mergeProperties0(src,dest);
		}
	}

	/**
	 * 合并src对象属性到目标中的每个元素中
	 */
	public static void mergeProperties0(Object src, Object dest) {
		try {
			//
			BeanInfo bi_src = Introspector.getBeanInfo(src.getClass()) ;

			//目标类的属性
			BeanInfo bi_dest = Introspector.getBeanInfo(dest.getClass()) ;
			PropertyDescriptor[] pps_dst = bi_dest.getPropertyDescriptors() ;
			Map<String, Method> map = new HashMap<String,Method>() ;
			for(PropertyDescriptor pp : pps_dst){
				String pname = pp.getName() ;
				Method m = pp.getWriteMethod() ;
				if(m != null){
					map.put(pname , m) ;
				}
			}

			//
			PropertyDescriptor[] pps = bi_src.getPropertyDescriptors();
			for(PropertyDescriptor pp : pps){
				Method getter = pp.getReadMethod();
				Method setter = pp.getWriteMethod();
				//
				if(getter != null && setter != null){
					String pname = pp.getName() ;
					Class ptype = pp.getPropertyType() ;
					Method dest_setter = map.get(pname) ;
					if(dest_setter != null){
						Object ret = getter.invoke(src) ;
						dest_setter.invoke(dest,ret) ;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 得到属性值
	 */
	public static Object getPropValue(Object obj , String prop){
		try {
			BeanInfo bi = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] pps  = bi.getPropertyDescriptors();
			for(PropertyDescriptor pp : pps){
				String name = pp.getName() ;
				if(name.equals(prop)){
					Method m = pp.getReadMethod();
					return m.invoke(obj) ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}

	/**
	 * 获取所有日志集合
	 */
	public static List<AppBaseLog> getAllLogs(AppLogAggEntity agg) {
		List<AppBaseLog> list = new ArrayList<AppBaseLog>();
		list.addAll(agg.getStartupLogs());
		list.addAll(agg.getUsageLogs());
		list.addAll(agg.getPageLogs());
		list.addAll(agg.getEventLogs());
		list.addAll(agg.getErrorLogs());
		return list;
	}
}
