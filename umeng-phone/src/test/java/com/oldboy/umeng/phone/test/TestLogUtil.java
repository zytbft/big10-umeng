package com.oldboy.umeng.phone.test;

import com.oldboy.umeng.common.domain.AppLogAggEntity;
import com.oldboy.umeng.common.domain.AppStartupLog;
import com.oldboy.umeng.common.util.LogUtil;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/24.
 */
public class TestLogUtil {
	@Test
	public void test1() throws IllegalAccessException {
		AppLogAggEntity e = LogUtil.genAppLogAgg();
		System.out.println(e.getDeviceId());
	}
}
