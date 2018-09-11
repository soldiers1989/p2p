package com.dimeng.p2p.escrow.huifu.entity.loans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 放款异常订单处理记录
 */
public class LoansAmend implements Serializable {

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
	 * 金额
	 */
	public BigDecimal amount;
	/**
	 * 订单时间
	 */
	public Timestamp ordTime;
	
}
