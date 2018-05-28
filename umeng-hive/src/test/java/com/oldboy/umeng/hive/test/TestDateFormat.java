package com.oldboy.umeng.hive.test;

import com.alibaba.fastjson.JSON;
import com.oldboy.umeng.common.domain.AppLogAggEntity;
import com.oldboy.umeng.common.util.LogUtil;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2018/5/25.
 */
public class TestDateFormat {

	@Test
	public void test1() throws Exception {
		String str = "24/May/2018:18:10:59 -0700" ;

		//日期格式化对象
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z" , Locale.US) ;
		Date d = new Date() ;
		String dateStr =  sdf.format(d) ;
		System.out.println(dateStr);

		Date dd = sdf.parse(str) ;
		long ms = dd.getTime() ;
		System.out.println(ms);		//1527210659000

		long mmm = 1527210659000L ;

		Date ddd = new Date(mmm) ;
		System.out.println(sdf.format(ddd));
	}
}
