package com.oldboy.umeng.common.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;
import com.oldboy.umeng.common.domain.AppLogAggEntity;
import com.oldboy.umeng.common.util.DateUtil;
import com.oldboy.umeng.common.util.GeoUtil;
import com.oldboy.umeng.common.util.LogUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/25.
 */
public class TestDateUtil {

	@Test
	public void test1() throws IOException {
		Date d = new Date();
		System.out.println(DateUtil.dateFormat(DateUtil.dayBegin(d , -1) ));
		System.out.println(DateUtil.dateFormat(DateUtil.dayBegin(d , -2) ));
		System.out.println(DateUtil.dateFormat(DateUtil.dayBegin(d , -3) ));
		System.out.println(DateUtil.dateFormat(DateUtil.dayBegin(d , 0) ));
		System.out.println(DateUtil.dateFormat(DateUtil.dayBegin(d , 1) ));
		System.out.println("==============");
		System.out.println(DateUtil.dateFormat(DateUtil.weekBegin(d , 0) ));
		System.out.println(DateUtil.dateFormat(DateUtil.weekBegin(d , -1) ));
		System.out.println(DateUtil.dateFormat(DateUtil.weekBegin(d , -2) ));
		System.out.println(DateUtil.dateFormat(DateUtil.weekBegin(d , 1) ));
		System.out.println("==============");
		System.out.println(DateUtil.dateFormat(DateUtil.monthBegin(d , 0) ));
		System.out.println(DateUtil.dateFormat(DateUtil.monthBegin(d , -1) ));
		System.out.println(DateUtil.dateFormat(DateUtil.monthBegin(d , -2) ));
		System.out.println(DateUtil.dateFormat(DateUtil.monthBegin(d , -6) ));

		
	}
}
