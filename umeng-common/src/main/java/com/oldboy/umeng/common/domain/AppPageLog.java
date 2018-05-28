package com.oldboy.umeng.common.domain;

/**
 * 页面日志
 */
public class AppPageLog extends AppBaseLog {
	private int pageViewCntInSession = 0;
	private String pageId;      				//页面id
	private int visitIndex = 0;    				//访问顺序号，0为第一个页面
	private String nextPage;    				//下一个访问页面，如为空则表示为退出应用的页面
	private Long stayDurationSecs = (long) 0;  	//当前页面停留时长

	public int getPageViewCntInSession() {
		return pageViewCntInSession;
	}

	public void setPageViewCntInSession(int pageViewCntInSession) {
		this.pageViewCntInSession = pageViewCntInSession;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public int getVisitIndex() {
		return visitIndex;
	}

	public void setVisitIndex(int visitIndex) {
		this.visitIndex = visitIndex;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public Long getStayDurationSecs() {
		return stayDurationSecs;
	}

	public void setStayDurationSecs(Long stayDurationSecs) {
		this.stayDurationSecs = stayDurationSecs;
	}
}