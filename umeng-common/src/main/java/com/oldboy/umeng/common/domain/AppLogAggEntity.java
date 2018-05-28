package com.oldboy.umeng.common.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志聚合体
 */
public class AppLogAggEntity {
	private String appId;               //应用唯一标识
	private String tenantId;            //租户唯一标识,企业用户
	private String deviceId;            //设备唯一标识
	private String appVersion;          //版本
	private String appChannel;          //渠道,安装时就在清单中制定了，appStore等。
	private String appPlatform;         //平台
	private String osType;              //操作系统
	private String deviceStyle;         //机型

	//集合信息
	private List<AppStartupLog> startupLogs = new ArrayList<AppStartupLog>();
	private List<AppErrorLog> errorLogs = new ArrayList<AppErrorLog>();
	private List<AppEventLog> eventLogs = new ArrayList<AppEventLog>();
	private List<AppUsageLog> usageLogs = new ArrayList<AppUsageLog>();
	private List<AppPageLog> pageLogs = new ArrayList<AppPageLog>();

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppChannel() {
		return appChannel;
	}

	public void setAppChannel(String appChannel) {
		this.appChannel = appChannel;
	}

	public String getAppPlatform() {
		return appPlatform;
	}

	public void setAppPlatform(String appPlatform) {
		this.appPlatform = appPlatform;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getDeviceStyle() {
		return deviceStyle;
	}

	public void setDeviceStyle(String deviceStyle) {
		this.deviceStyle = deviceStyle;
	}

	public List<AppStartupLog> getStartupLogs() {
		return startupLogs;
	}

	public void setStartupLogs(List<AppStartupLog> startupLogs) {
		this.startupLogs = startupLogs;
	}

	public List<AppErrorLog> getErrorLogs() {
		return errorLogs;
	}

	public void setErrorLogs(List<AppErrorLog> errorLogs) {
		this.errorLogs = errorLogs;
	}

	public List<AppEventLog> getEventLogs() {
		return eventLogs;
	}

	public void setEventLogs(List<AppEventLog> eventLogs) {
		this.eventLogs = eventLogs;
	}

	public List<AppUsageLog> getUsageLogs() {
		return usageLogs;
	}

	public void setUsageLogs(List<AppUsageLog> usageLogs) {
		this.usageLogs = usageLogs;
	}

	public List<AppPageLog> getPageLogs() {
		return pageLogs;
	}

	public void setPageLogs(List<AppPageLog> pageLogs) {
		this.pageLogs = pageLogs;
	}

}
