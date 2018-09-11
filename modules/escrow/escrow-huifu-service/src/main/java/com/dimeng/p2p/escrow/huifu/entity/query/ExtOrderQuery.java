package com.dimeng.p2p.escrow.huifu.entity.query;

import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.modules.account.console.service.query.OrderQuery;

/**
 * 订单查询参数扩展类
 * @author raoyujun
 *
 */
public interface ExtOrderQuery extends OrderQuery {
	
    T6501_F03 getOrderStatus();
    
}
