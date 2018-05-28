package com.oldboy.umeng.common.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;
import com.oldboy.umeng.common.domain.*;
import com.oldboy.umeng.common.util.BeanUtil;
import com.oldboy.umeng.common.util.GeoUtil;
import com.oldboy.umeng.common.util.LogUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.List;

/**
 * Created by Administrator on 2018/5/25.
 */
public class TestBeanUtil {

	@Test
	public void test1() throws IOException {
		String ddl = BeanUtil.genDDL(AppStartupLog.class , "startuplogs") ;
		System.out.println(ddl);
	}

	@Test
	public void test2() throws IOException {
		Class[] classes = {
				AppStartupLog.class ,
				AppEventLog.class ,
				AppErrorLog.class ,
				AppUsageLog.class ,
				AppPageLog.class ,
		} ;

		String[] tables = {
				"startuplogs" ,
				"eventlogs" ,
				"errorlogs" ,
				"usgaelogs" ,
				"pagelogs"
		} ;

		List<String> ddls = BeanUtil.genDDLs(classes , tables) ;
		for(String sql : ddls){
			System.out.println(sql + " ; ");
		}
	}
}
