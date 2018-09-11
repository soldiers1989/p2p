package com.dimeng.p2p.escrow.huifu.service;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.escrow.huifu.entity.query.MerAcctQueryEntity;

public abstract interface MerAcctQueryManage extends Service {
	/**
	 * 
	 * @throws Throwable
	 */
	public abstract MerAcctQueryEntity query() throws Throwable;
}
