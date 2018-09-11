package com.dimeng.p2p.escrow.huifu.service;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.S50.entities.T5020;
import com.dimeng.p2p.S61.entities.T6114;
import com.dimeng.p2p.escrow.huifu.cond.BgBindCardCond;
import com.dimeng.p2p.escrow.huifu.entity.bankcard.BgBindCardEntity;
import com.dimeng.p2p.escrow.huifu.entity.bankcard.BindCardEntity;
import com.dimeng.p2p.escrow.huifu.entity.bankcard.UnbindCardEntity;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeEntity;

public abstract interface BankCardManage extends Service {
	/**
	 * 生成绑定银行卡访问地址
	 * 
	 * @param usrCustId
	 *            用户客户号
	 * @return
	 * @throws Throwable
	 */
	public abstract String createBindCardUrI(String usrCustId) throws Throwable;

	/**
	 * 插入银行卡
	 * 
	 * @param t6114
	 * @throws Throwable
	 */
	public abstract void insertT6114(BindCardEntity entity) throws Throwable;

	/**
	 * 前台绑定银行卡返回参数
	 * 
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	public abstract BindCardEntity bindCardReturnDecoder(
			HttpServletRequest request) throws Throwable;

	/**
	 * 发送后台绑定银行卡信息
	 * 
	 * @param cond
	 * @return
	 * @throws Throwable
	 */
	public abstract BgBindCardEntity sendBgBindCard(BgBindCardCond cond)
			throws Throwable;

	/**
	 * 用户解绑卡
	 * 
	 * @param usrCustId
	 *            用户客户号
	 * @param cardId
	 *            银行卡号
	 * @return
	 * @throws Throwable
	 */
	public abstract UnbindCardEntity sendUnbindCard(String usrCustId,
			String cardId) throws Throwable;

	/**
	 * 获取用户商户号
	 * 
	 * @throws Throwable
	 */
	public abstract String getUserCustId() throws Throwable;

	/**
	 * 获取银行卡号
	 * 
	 * @param id
	 * @return
	 * @throws Throwable
	 */
	public abstract String getBankCard(int id) throws Throwable;

	/**
	 * 删除银行卡
	 * 
	 * @param entity
	 * @throws Throwable
	 */
	public abstract void unbindCard(UnbindCardEntity entity) throws Throwable;

	/**
	 * 获取登录用户银行卡数量
	 * 
	 * @return
	 * @throws Throwable
	 */
	public abstract int getBankCardCount() throws Throwable;

	/**
	 * 获取平台银行卡
	 * 
	 * @return
	 * @throws Throwable
	 */
	public abstract T6114 getPtBankCard() throws Throwable;

	/**
	 * 获取银行列表
	 * 
	 * @return
	 * @throws Throwable
	 */
	public abstract T5020[] getBankList() throws Throwable;

	/**
	 * 添加平台银行卡
	 * 
	 * @param bankId
	 * @param bankCard
	 * @throws Throwable
	 */
	public abstract void addPtCard(int bankId, String bankCard)
			throws Throwable;

	/**
	 * 更新平台银行卡
	 * 
	 * @param bankId
	 * @param bankCard
	 * @param cardId
	 * @throws Throwable
	 */
	public abstract void updatePtCard(int bankId, String bankCard, int cardId)
			throws Throwable;

	/**
	 * 添加用户银行卡
	 * 
	 * @param name
	 * @param bankId
	 * @param bankCard
	 * @throws Throwable
	 */
	public abstract void addCard(String name, int bankId, String bankCard)
			throws Throwable;
	
	/**
     * 添加快捷支付返回卡号处理
     * 
     * @param name
     * @param bankId
     * @param bankCard
     * @throws Throwable
     */
    public abstract void addQuickPayCardId(ChargeEntity chargeEntity)
            throws Throwable;
    /**
     * 用户解绑快捷卡返回参数解析
     * 
     * @param usrCustId
     *            用户客户号
     * @param cardId
     *            银行卡号
     * @return
     * @throws Throwable
     */
    public abstract UnbindCardEntity unbindQuickCardRetDecoder(
        HttpServletRequest request) throws Throwable;
    
    /**
     *  更新用户快捷卡
     * 
     * @param usrCustId
     *            用户客户号
     * @param cardId
     *            银行卡号
     * @return
     * @throws Throwable
     */
    public abstract void updateQuickCard(UnbindCardEntity entity) throws Throwable;
}
