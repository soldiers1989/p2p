/**
 * 
 */
package com.dimeng.p2p.escrow.huifu.entity.bid;

/**
 * 
 * 投标撤销返回
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年12月7日]
 */
public class BidCancleReturn {
    /**
     * 投资返回订单ID
     */
    public int bidOrderIds;

    /**
     * 体验金投资返回ID
     */
    public String experOrderIds;

    /**
     * 加息券放款订单ID
     */
    public String couponOrderIds;
}