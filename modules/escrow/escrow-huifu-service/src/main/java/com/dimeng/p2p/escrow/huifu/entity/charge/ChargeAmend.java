package com.dimeng.p2p.escrow.huifu.entity.charge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class ChargeAmend implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 订单id
	 */
	public int ordId;
	/**
	 * 用户名
	 */
	public String userName;
	/**
	 * 订单金额
	 */
	public BigDecimal amount;
	/**
	 * 订单时间
	 */
	public Timestamp time;
}
