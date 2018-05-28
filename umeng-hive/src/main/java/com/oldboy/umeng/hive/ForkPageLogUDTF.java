package com.oldboy.umeng.hive;

import com.oldboy.umeng.common.domain.AppBaseLog;
import com.oldboy.umeng.common.domain.AppErrorLog;
import com.oldboy.umeng.common.domain.AppLogAggEntity;
import com.oldboy.umeng.common.domain.AppPageLog;

import java.util.List;

/**
 *叉分使用日志
 */
public class ForkPageLogUDTF extends BaseForkLogUDTF<AppPageLog> {

	public List<? extends AppBaseLog> getLogs(AppLogAggEntity agg) {
		return agg.getPageLogs();
	}
}
