package com.oldboy.umeng.hive;

import com.oldboy.umeng.common.domain.AppBaseLog;
import com.oldboy.umeng.common.domain.AppEventLog;
import com.oldboy.umeng.common.domain.AppLogAggEntity;
import com.oldboy.umeng.common.domain.AppUsageLog;

import java.util.List;

/**
 *叉分使用日志
 */
public class ForkEventLogUDTF extends BaseForkLogUDTF<AppEventLog> {

	public List<? extends AppBaseLog> getLogs(AppLogAggEntity agg) {
		return agg.getEventLogs();
	}
}
