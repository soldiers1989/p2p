package com.dimeng.p2p.escrow.huifu.service;

import com.dimeng.framework.service.Service;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.escrow.huifu.entity.transfer.TransStatQueryEntity;

public abstract interface TransStatQueryManage extends Service {
	/**
	 * 
	 * @param orderId
	 * @throws Throwable
	 */
	public abstract TransStatQueryEntity query(int orderId) throws Throwable;
	/**
	 * 
	 * @param orderId
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @param paging
	 * @return
	 * @throws Throwable
	 */
	public abstract PagingResult<T6501> orderList(int orderId,
			String startDate, String endDate, int type, Paging paging)
			throws Throwable;
}
