package com.oldboy.umeng.hive;

import com.oldboy.umeng.common.domain.AppBaseLog;
import com.oldboy.umeng.common.domain.AppErrorLog;
import com.oldboy.umeng.common.domain.AppEventLog;
import com.oldboy.umeng.common.domain.AppLogAggEntity;

import java.util.List;

/**
 *叉分使用日志
 */
public class ForkErrorLogUDTF extends BaseForkLogUDTF<AppErrorLog> {

	public List<? extends AppBaseLog> getLogs(AppLogAggEntity agg) {
		return agg.getErrorLogs();
	}
}
