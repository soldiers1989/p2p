package com.dimeng.p2p.escrow.huifu.cond;

public interface QueryCond {
	/**
	 * 开始时间
	 */
	public String beginDate();
	/**
	 * 结束时间
	 */
	public String endDate();
	/**
	 * 页号
	 */
	public String pageNum();
	/**
	 * 每页记录条数
	 */
	public String pageSize();
}
