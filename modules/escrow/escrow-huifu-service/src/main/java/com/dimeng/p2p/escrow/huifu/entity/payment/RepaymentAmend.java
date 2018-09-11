package com.dimeng.p2p.escrow.huifu.entity.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 还款异常订单处理记录
 */
public class RepaymentAmend implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 订单id
	 */
	public int ordId;
	/**
	 * 投资用户名
	 */
	public String name;
	/**
	 * 标名
	 */
	public String bidName;
	/**
	 * 期号
	 */
	public int issue;
	/**
	 * 还款金额
	 */
	public BigDecimal amount;
	/**
	 * 费用科目
	 */
	public String subject;
	/**
	 * 订单时间
	 */
	public Timestamp ordTime;
}
