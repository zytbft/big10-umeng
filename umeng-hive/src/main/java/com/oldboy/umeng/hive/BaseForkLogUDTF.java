package com.oldboy.umeng.hive;

import com.alibaba.fastjson.JSON;
import com.oldboy.umeng.common.domain.AppBaseLog;
import com.oldboy.umeng.common.domain.AppLogAggEntity;
import com.oldboy.umeng.common.domain.AppStartupLog;
import com.oldboy.umeng.common.util.DateUtil;
import com.oldboy.umeng.common.util.LogUtil;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDTF;
import org.apache.hadoop.hive.serde2.objectinspector.*;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaLongObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 叉分日志UDTF函数
 * startuplog(clienttimems , servertimestr,log) from raw_logs ;
 */
public abstract class BaseForkLogUDTF<T> extends GenericUDTF {

	private Class clazz ;

	public BaseForkLogUDTF(){
		//得到泛型化超类
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class) type.getActualTypeArguments()[0];
	}

	private List<String> fieldNames = null;
	private List<ObjectInspector> fieldOIs = null;

	//转换器
	ObjectInspectorConverters.Converter[] converters = new ObjectInspectorConverters.Converter[4];

	/**
	 * 校验输入类型
	 */
	public StructObjectInspector initialize(ObjectInspector[] args) throws UDFArgumentException {
		if (args.length != 4) {
			throw new UDFArgumentException("forklog() takes 4 arguments");
		}

		//1.clienttimems = long
		if(checkPrimitiveOI(args[0] , PrimitiveObjectInspector.PrimitiveCategory.LONG)){
			converters[0] = ObjectInspectorConverters.getConverter(args[0] , PrimitiveObjectInspectorFactory.javaLongObjectInspector) ;
		}
		else{
			throw new UDFArgumentException("1st argument is not bigint!!");
		}

		//2.servertimestr = string
		if(checkPrimitiveOI(args[1] , PrimitiveObjectInspector.PrimitiveCategory.STRING)){
			converters[1] = ObjectInspectorConverters.getConverter(args[1] , PrimitiveObjectInspectorFactory.javaStringObjectInspector) ;
		}
		else{
			throw new UDFArgumentException("2st argument is not string!!");
		}

		//3.clientip
		if (checkPrimitiveOI(args[2], PrimitiveObjectInspector.PrimitiveCategory.STRING)) {
			converters[2] = ObjectInspectorConverters.getConverter(args[2], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
		} else {
			throw new UDFArgumentException("3st argument is not string!!");
		}

		//4.log = json string
		if (checkPrimitiveOI(args[3], PrimitiveObjectInspector.PrimitiveCategory.STRING)) {
			converters[3] = ObjectInspectorConverters.getConverter(args[3], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
		} else {
			throw new UDFArgumentException("4st argument is not string!!");
		}
		//
		genLogOIs();
		return ObjectInspectorFactory.getStandardStructObjectInspector(fieldNames, fieldOIs);
	}

	/**
	 * 检查指定的OI是否所需要的类型
	 */
	private boolean checkPrimitiveOI(ObjectInspector oi1, PrimitiveObjectInspector.PrimitiveCategory c) {
		if(oi1.getCategory() == ObjectInspector.Category.PRIMITIVE){
			if(((PrimitiveObjectInspector)oi1).getPrimitiveCategory() == c){
				return true ;
			}
		}
		return false ;
	}

	/**
	 * 处理过程 * @param args * @throws HiveException
	 */
	public void process(Object[] args) throws HiveException {
		if (args.length != 4) {
			throw new UDFArgumentException("forklog() takes  4 arguments");
		}

		//1. clienttimems
		Long clienttimems = (Long)converters[0].convert(args[0]) ;

		//2. servertimestr
		Long servertimems  =  DateUtil.formatDateStr((String)converters[1].convert(args[1])) ;

		//3.clientip
		String clientIp = (String) converters[2].convert(args[2]);

		//4. json
		String json = (String) converters[3].convert(args[3]);

		//2. 替换
		json = json.replace("\\\"", "\"");

		//3. 直接将json串解析成日志聚合体
		AppLogAggEntity agg = JSON.parseObject(json, AppLogAggEntity.class);

		//3'.对时间进行对齐,更新日志聚合体中所有日志对象的创建时间，换算成服务器对应的时间。
		//   clientms servertimems
		alignTime(agg , servertimems - clienttimems);

		//3''.对日志进行特殊处理，通常在子类中完成
		extraProcessLogs(agg , clientIp);



		//4. 合并聚合体的公共属性到各个日志的缺失属性上。
		LogUtil.mergeProperties1(agg,getLogs(agg));

		for (AppBaseLog log : getLogs(agg)) {
			Method[] ms = log.getClass().getDeclaredMethods();

			//5. 输出一行记录
			Object[] objs = new Object[fieldNames.size()] ;
			for (int i = 0; i < objs.length; i++) {
				objs[i] = LogUtil.getPropValue(log, fieldNames.get(i));
			}
			forward(objs);
		}
	}

	/**
	 * 对日志进行特殊处理
	 */
	public void extraProcessLogs(AppLogAggEntity agg ,String clientIp) {
	}

	/**
	 *
	 * 对齐时间
	 */
	private void alignTime(AppLogAggEntity agg, long offset) {
		List<AppBaseLog> logs = LogUtil.getAllLogs(agg) ;
		if(logs == null || logs.isEmpty()){
			return ;
		}
		for(AppBaseLog log : logs){
			if(log != null){
				log.setCreatedAtMs((log.getCreatedAtMs()==null?0: log.getCreatedAtMs())+ offset);
			}
		}
	}

	abstract public List<? extends AppBaseLog> getLogs(AppLogAggEntity agg)  ;

	public void close() throws HiveException {

	}

	//生成字段对应的检查器信息
	public void genLogOIs() {
		fieldNames = new ArrayList<String>();
		fieldOIs = new ArrayList<ObjectInspector>();
		doGenLogOIs(clazz);
	}

	private void doGenLogOIs(Class clazz) {
		try {
			BeanInfo bi = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] pps = bi.getPropertyDescriptors();
			for (PropertyDescriptor pp : pps) {
				Method getter = pp.getReadMethod();
				Method setter = pp.getWriteMethod();
				if (getter != null && setter != null) {
					String name = pp.getName();
					Class ptype = pp.getPropertyType();

					if (ptype == String.class) {
						fieldNames.add(name);
						fieldOIs.add(PrimitiveObjectInspectorFactory.javaStringObjectInspector);
					} else if (ptype == int.class || ptype == Integer.class) {
						fieldNames.add(name);
						fieldOIs.add(PrimitiveObjectInspectorFactory.javaIntObjectInspector);
					} else if (ptype == long.class || ptype == Long.class) {
						fieldNames.add(name);
						fieldOIs.add(PrimitiveObjectInspectorFactory.javaLongObjectInspector);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
