package com.oldboy.umeng.common.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;
import com.oldboy.umeng.common.domain.AppLogAggEntity;
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
public class TestGeo {

	@Test
	public void test1() throws IOException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream in = loader.getResourceAsStream("GeoLite2-City.mmdb") ;
		Reader reader = new Reader(in) ;
		JsonNode node = reader.get(InetAddress.getByName("192.168.231.11")) ;
		System.out.println(node);

		System.out.println(GeoUtil.getProvince("192.168.231.11"));
		System.out.println(GeoUtil.getCountry("192.168.231.11"));
	}

	@Test
	public void test2() throws IllegalAccessException {
		AppLogAggEntity agg = LogUtil.genAppLogAgg() ;
		System.out.println(agg.getDeviceId());
		
	}
	@Test
	public void test3() throws IllegalAccessException {
		long time = 1527247937997L ;
		Date date = new Date(time) ;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println(sdf.format(date)) ;
	}
}
