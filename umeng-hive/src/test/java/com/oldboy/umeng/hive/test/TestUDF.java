package com.oldboy.umeng.hive.test;

import com.alibaba.fastjson.JSON;
import com.oldboy.umeng.common.domain.AppLogAggEntity;
import com.oldboy.umeng.common.util.LogUtil;
import com.oldboy.umeng.hive.BaseForkLogUDTF;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.junit.Test;

/**
 * Created by Administrator on 2018/5/25.
 */
public class TestUDF {

	@Test
	public void test1() throws HiveException {

		String str = "{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appId\\\":\\\"faobengplay\\\",\\\"appPlatform\\\":\\\"ios\\\",\\\"appVersion\\\":\\\"1.2.0\\\",\\\"deviceStyle\\\":\\\"iphone 7\\\",\\\"errorLogs\\\":[{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appId\\\":\\\"faobengplay\\\",\\\"appPlatform\\\":\\\"ios\\\",\\\"appVersion\\\":\\\"1.1.0\\\",\\\"deviceStyle\\\":\\\"iphone 6\\\",\\\"errorBrief\\\":\\\"at cn.lift.dfdf.web.AbstractBaseController.validInbound(AbstractBaseController.java:72)\\\",\\\"errorDetail\\\":\\\"java.lang.NullPointerException at cn.lift.appIn.web.AbstractBaseController.validInbound(AbstractBaseController.java:72) at cn.lift.dfdf.web.AbstractBaseController.validInbound\\\",\\\"osType\\\":\\\"android 4.0\\\",\\\"tenantId\\\":\\\"tnt023\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appId\\\":\\\"sohuvideo\\\",\\\"appPlatform\\\":\\\"android\\\",\\\"appVersion\\\":\\\"1.0.0\\\",\\\"deviceStyle\\\":\\\"红米\\\",\\\"errorBrief\\\":\\\"at cn.lift.appIn.control.CommandUtil.getInfo(CommandUtil.java:67)\\\",\\\"errorDetail\\\":\\\"java.lang.NullPointerException at cn.lift.appIn.web.AbstractBaseController.validInbound(AbstractBaseController.java:72) at cn.lift.dfdf.web.AbstractBaseController.validInbound\\\",\\\"osType\\\":\\\"android 4.0\\\",\\\"tenantId\\\":\\\"tnt009\\\"}],\\\"eventLogs\\\":[{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appId\\\":\\\"sohuvideo\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"iphone 6 plus\\\",\\\"eventId\\\":\\\"bookstore\\\",\\\"osType\\\":\\\"ios11\\\",\\\"tenantId\\\":\\\"tnt501\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appId\\\":\\\"gaodemap\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"iphone 6\\\",\\\"eventId\\\":\\\"popmenu\\\",\\\"osType\\\":\\\"mi 5.5\\\",\\\"tenantId\\\":\\\"tnt501\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appId\\\":\\\"sohuvideo\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"1.2.0\\\",\\\"deviceStyle\\\":\\\"oppo 1\\\",\\\"eventId\\\":\\\"bookstore\\\",\\\"osType\\\":\\\"ios11\\\",\\\"tenantId\\\":\\\"tnt501\\\"},{\\\"appChannel\\\":\\\"umeng\\\",\\\"appId\\\":\\\"sohuvideo\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"1.0.0\\\",\\\"deviceStyle\\\":\\\"iphone 7 plus\\\",\\\"eventId\\\":\\\"popmenu\\\",\\\"osType\\\":\\\"ios11\\\",\\\"tenantId\\\":\\\"tnt009\\\"},{\\\"appChannel\\\":\\\"umeng\\\",\\\"appId\\\":\\\"gaodemap\\\",\\\"appPlatform\\\":\\\"android\\\",\\\"appVersion\\\":\\\"1.0.0\\\",\\\"deviceStyle\\\":\\\"iphone 7\\\",\\\"eventId\\\":\\\"bookstore\\\",\\\"osType\\\":\\\"ios11\\\",\\\"tenantId\\\":\\\"tnt009\\\"}],\\\"osType\\\":\\\"mi 5.5\\\",\\\"pageLogs\\\":[null,null,null],\\\"startupLogs\\\":[{\\\"appChannel\\\":\\\"umeng\\\",\\\"appId\\\":\\\"sohuvideo\\\",\\\"appPlatform\\\":\\\"ios\\\",\\\"appVersion\\\":\\\"1.1.0\\\",\\\"brand\\\":\\\"华为\\\",\\\"carrier\\\":\\\"中国电信\\\",\\\"country\\\":\\\"china\\\",\\\"deviceStyle\\\":\\\"iphone 6 plus\\\",\\\"network\\\":\\\"cell\\\",\\\"osType\\\":\\\"mi 5.5\\\",\\\"province\\\":\\\"guangdong\\\",\\\"screenSize\\\":\\\"960 * 640\\\",\\\"tenantId\\\":\\\"tnt023\\\"},{\\\"appChannel\\\":\\\"appstore\\\",\\\"appId\\\":\\\"tianya\\\",\\\"appPlatform\\\":\\\"blackberry\\\",\\\"appVersion\\\":\\\"1.0.0\\\",\\\"brand\\\":\\\"华为\\\",\\\"carrier\\\":\\\"中国移动\\\",\\\"country\\\":\\\"china\\\",\\\"deviceStyle\\\":\\\"iphone 6 plus\\\",\\\"network\\\":\\\"3g\\\",\\\"osType\\\":\\\"android 4.0\\\",\\\"province\\\":\\\"jiazhou\\\",\\\"screenSize\\\":\\\"960 * 640\\\",\\\"tenantId\\\":\\\"tnt009\\\"},{\\\"appChannel\\\":\\\"umeng\\\",\\\"appId\\\":\\\"gaodemap\\\",\\\"appPlatform\\\":\\\"blackberry\\\",\\\"appVersion\\\":\\\"1.0.0\\\",\\\"brand\\\":\\\"三星\\\",\\\"carrier\\\":\\\"中国移动\\\",\\\"country\\\":\\\"america\\\",\\\"deviceStyle\\\":\\\"vivo 3\\\",\\\"network\\\":\\\"wifi\\\",\\\"osType\\\":\\\"mi 5.5\\\",\\\"province\\\":\\\"guangxi\\\",\\\"screenSize\\\":\\\"960 * 640\\\",\\\"tenantId\\\":\\\"tnt023\\\"}],\\\"tenantId\\\":\\\"tnt501\\\",\\\"usageLogs\\\":[{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appId\\\":\\\"sohuvideo\\\",\\\"appPlatform\\\":\\\"android\\\",\\\"appVersion\\\":\\\"2.0.0\\\",\\\"deviceStyle\\\":\\\"iphone 6 plus\\\",\\\"osType\\\":\\\"mi 5.5\\\",\\\"tenantId\\\":\\\"tnt501\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appId\\\":\\\"sohuvideo\\\",\\\"appPlatform\\\":\\\"winphone\\\",\\\"appVersion\\\":\\\"1.0.0\\\",\\\"deviceStyle\\\":\\\"iphone 7 plus\\\",\\\"osType\\\":\\\"ios11\\\",\\\"tenantId\\\":\\\"tnt023\\\"},{\\\"appChannel\\\":\\\"anroid bus\\\",\\\"appId\\\":\\\"gaodemap\\\",\\\"appPlatform\\\":\\\"ios\\\",\\\"appVersion\\\":\\\"1.1.0\\\",\\\"deviceStyle\\\":\\\"iphone 7\\\",\\\"osType\\\":\\\"mi 5.5\\\",\\\"tenantId\\\":\\\"tnt009\\\"}]}" ;
		str = str.replace("\\\"", "\"") ;
		AppLogAggEntity agg = JSON.parseObject(str , AppLogAggEntity.class) ;
		LogUtil.mergeProperties1(agg,agg.getStartupLogs());

		Object startLog = agg.getStartupLogs().get(0) ;
		String name = (String) LogUtil.getPropValue(startLog , "appVersion");
		System.out.println(name);

	}
}
