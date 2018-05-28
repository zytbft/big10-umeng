package com.oldboy.umeng.hive;

import com.oldboy.umeng.common.domain.AppBaseLog;
import com.oldboy.umeng.common.domain.AppLogAggEntity;
import com.oldboy.umeng.common.domain.AppStartupLog;
import com.oldboy.umeng.common.util.GeoUtil;

import java.util.List;

/**
 *
 */
public class ForkStartupLogUDTF extends BaseForkLogUDTF<AppStartupLog> {

	public List<? extends AppBaseLog> getLogs(AppLogAggEntity agg) {
		return agg.getStartupLogs();
	}

	public void extraProcessLogs(AppLogAggEntity agg , String clientIp) {
		List<AppStartupLog> logs = agg.getStartupLogs();
		for(AppStartupLog log : logs){
			log.setCountry(GeoUtil.getCountry(clientIp));
			log.setProvince(GeoUtil.getProvince(clientIp));
			log.setIpAddress(clientIp);
		}
	}
}
