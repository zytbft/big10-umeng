package com.oldboy.umeng.hive;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义日期格式化类
 */
public class UmengDateFormatUDF extends GenericUDF {

	private ObjectInspectorConverters.Converter[] converters = null ;
	/**
	 * 判断参数有效性
	 */
	public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
		if(arguments.length != 1 && arguments.length != 2 ){
			throw new UDFArgumentException("参数不对!,dateFormat(ms) , dateformat(ms , 'yyyy')");
		}

		//
		if(arguments.length == 1){
			converters = new ObjectInspectorConverters.Converter[1] ;
			converters[0] = ObjectInspectorConverters.getConverter(arguments[0] , PrimitiveObjectInspectorFactory.javaLongObjectInspector) ;
		}
		else{
			converters = new ObjectInspectorConverters.Converter[2];
			converters[0] = ObjectInspectorConverters.getConverter(arguments[0], PrimitiveObjectInspectorFactory.javaLongObjectInspector);
			converters[1] = ObjectInspectorConverters.getConverter(arguments[1], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
		}

		ObjectInspector outputOI = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
		return outputOI;
	}

	/**
	 * 函数计算体
	 */
	public Object evaluate(GenericUDF.DeferredObject[] arguments) throws HiveException {
		if (arguments.length != 1 && arguments.length != 2) {
			throw new UDFArgumentException("参数不对!,dateFormat(ms) , dateformat(ms , 'yyyy')");
		}
		Long ms = (Long)converters[0].convert(arguments[0].get()) ;
		String fmt = "yyyy/MM/dd" ;
		if(arguments.length == 2){
			fmt = (String) converters[1].convert(arguments[1].get());
		}

		Date d = new Date(ms) ;
		SimpleDateFormat sdf = new SimpleDateFormat(fmt) ;
		return sdf.format(d) ;
	}

	public String getDisplayString(String[] children) {
		return "umeng_dateformat" ;
	}

	protected String getFuncName() {
		return "dateformat";
	}
}
