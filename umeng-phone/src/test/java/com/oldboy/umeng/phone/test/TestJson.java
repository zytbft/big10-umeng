package com.oldboy.umeng.phone.test;

import com.alibaba.fastjson.JSON;
import com.oldboy.umeng.common.domain.AppLogAggEntity;
import com.oldboy.umeng.common.util.LogUtil;
import org.junit.Test;

/**
 * Created by Administrator on 2018/5/24.
 */
public class TestJson {
	@Test
	public void test1() throws IllegalAccessException {
		AppLogAggEntity e = LogUtil.genAppLogAgg();
		String json = JSON.toJSONString(e) ;
		System.out.println(json);
	}
}
