package com.oldboy.umeng.hive;

import com.oldboy.umeng.common.domain.AppBaseLog;
import com.oldboy.umeng.common.domain.AppLogAggEntity;
import com.oldboy.umeng.common.domain.AppStartupLog;
import com.oldboy.umeng.common.domain.AppUsageLog;

import java.util.List;

/**
 *叉分使用日志
 */
public class ForkUsageLogUDTF extends BaseForkLogUDTF<AppUsageLog> {

	public List<? extends AppBaseLog> getLogs(AppLogAggEntity agg) {
		return agg.getUsageLogs();
	}
}
