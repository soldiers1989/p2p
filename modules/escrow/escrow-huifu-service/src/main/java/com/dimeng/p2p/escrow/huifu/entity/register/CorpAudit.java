package com.dimeng.p2p.escrow.huifu.entity.register;

import java.io.Serializable;
import java.sql.Timestamp;

import com.dimeng.p2p.S61.enums.T6110_F06;
import com.dimeng.p2p.S61.enums.T6165_F02;

public class CorpAudit implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 登录账号
	 */
	public String account;
	/**
	 * 用户类型
	 */
	public T6110_F06 type;
	/**
	 * 审核状态
	 */
	public T6165_F02 status;
	/**
	 * 审核描述
	 */
	public String desc;
	/**
	 * 最后更新时间
	 */
	public Timestamp time;
}
