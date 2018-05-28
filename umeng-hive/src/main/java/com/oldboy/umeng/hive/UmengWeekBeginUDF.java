package com.oldboy.umeng.hive;

import com.oldboy.umeng.common.util.DateUtil;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获得指定的周起始时刻(毫秒数)
 */
public class UmengWeekBeginUDF extends GenericUDF {

	private ObjectInspectorConverters.Converter[] converters = null ;
	/**
	 * 判断参数有效性
	 */
	public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
		if(arguments.length != 1 && arguments.length != 2 && arguments.length !=3 ){
			throw new UDFArgumentException("参数不对!,函数调用形式为weekbegin(ms) , weekbegin(ms,-1) , weekbegin('2018-12' , 'yyyy-MM' , -1)");
		}

		//
		if(arguments.length == 1){
			converters = new ObjectInspectorConverters.Converter[1] ;
			converters[0] = ObjectInspectorConverters.getConverter(arguments[0] , PrimitiveObjectInspectorFactory.javaLongObjectInspector) ;
		}
		else if (arguments.length == 2){
			converters = new ObjectInspectorConverters.Converter[2];
			converters[0] = ObjectInspectorConverters.getConverter(arguments[0], PrimitiveObjectInspectorFactory.javaLongObjectInspector);
			converters[1] = ObjectInspectorConverters.getConverter(arguments[1], PrimitiveObjectInspectorFactory.javaIntObjectInspector);
		}
		else{
			converters = new ObjectInspectorConverters.Converter[3];
			converters[0] = ObjectInspectorConverters.getConverter(arguments[0], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
			converters[1] = ObjectInspectorConverters.getConverter(arguments[1], PrimitiveObjectInspectorFactory.javaStringObjectInspector);
			converters[2] = ObjectInspectorConverters.getConverter(arguments[2], PrimitiveObjectInspectorFactory.javaIntObjectInspector);
		}

		ObjectInspector outputOI = PrimitiveObjectInspectorFactory.javaLongObjectInspector;
		return outputOI;
	}

	/**
	 * 函数计算体
	 */
	public Object evaluate(DeferredObject[] arguments) throws HiveException {

		if (arguments.length != 1 && arguments.length != 2 && arguments.length != 3) {
			throw new UDFArgumentException("参数不对!,函数调用形式为weekbegin(ms) , weekbegin(ms,-1) , weekbegin('2018-12' , 'yyyy-MM' , -1)");
		}

		if(arguments.length == 1 || arguments.length == 2){
			Long ms = (Long)converters[0].convert(arguments[0].get()) ;
			int offset = 0 ;
			if(arguments.length == 2){
				offset = (Integer) converters[1].convert(arguments[1].get());
			}
			return DateUtil.weekBegin(new Date(ms) , offset) ;

		}
		else{
			String dateStr = (String)converters[0].convert(arguments[0].get());
			String fmt = (String)converters[1].convert(arguments[1].get());
			int offset = (Integer)converters[2].convert(arguments[2].get());
			SimpleDateFormat sdf = new SimpleDateFormat(fmt) ;
			Date newDate = null;
			try {
				newDate = sdf.parse(dateStr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return DateUtil.weekBegin(newDate , offset) ;
		}
	}

	public String getDisplayString(String[] children) {
		return "umeng_weekbegin" ;
	}

	protected String getFuncName() {
		return "weekbegin";
	}
}
