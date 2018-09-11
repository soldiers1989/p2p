package com.dimeng.p2p.escrow.huifu.service;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.Service;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.entities.T6119;
import com.dimeng.p2p.S61.entities.T6161;
import com.dimeng.p2p.escrow.huifu.cond.BgUserRegisterCond;
import com.dimeng.p2p.escrow.huifu.cond.CorpRegisterCond;
import com.dimeng.p2p.escrow.huifu.cond.UserRegisterCond;
import com.dimeng.p2p.escrow.huifu.entity.query.CorpQueryEntity;
import com.dimeng.p2p.escrow.huifu.entity.register.CorpAudit;
import com.dimeng.p2p.escrow.huifu.entity.register.CorpRegisterEntity;
import com.dimeng.p2p.escrow.huifu.entity.register.OpenEscrowGuideEntity;
import com.dimeng.p2p.escrow.huifu.entity.register.UserRegisterEntity;

public abstract interface UserManage extends Service {
	/**
	 * 生成用户开户跳转地址
	 * 
	 * @param cond
	 *            传入的参数
	 * @return 返回跳转地址
	 * @throws Throwable
	 */
	public abstract String createRegisterUri(UserRegisterCond cond)
			throws Throwable;

	/**
	 * 更新用户信息
	 * 
	 * @param realName
	 *            真实姓名
	 * @param idNo
	 *            身份证号
	 * @param phone
	 *            手机号
	 * @param email
	 *            邮箱
	 * @throws Throwable
	 */
	public abstract void updateUserInfo(UserRegisterEntity entity)
			throws Throwable;

	/**
	 * 查询用户基本信息
	 * 
	 * @param connection
	 * @param F01
	 * @return
	 * @throws SQLException
	 */
	public T6110 selectT6110() throws Throwable;

	/**
	 * 查询用户第三方托管信息
	 * 
	 * @param F01
	 * @return
	 * @throws Throwable
	 */
	public T6119 selectT6119() throws Throwable;

	/**
	 * 获取企业信息
	 * 
	 * @return
	 * @throws Throwable
	 */
	public T6161 selectT6161() throws Throwable;

	/**
	 * 解析用户开户返回数据, 成功解析的必要条件：数据正确返回，验签成功
	 * 
	 * @param request
	 * @return 若未成功解析返回null
	 * @throws Throwable
	 */
	public abstract UserRegisterEntity registerReturnDecoder(
			HttpServletRequest request) throws Throwable;

	/**
	 * 后台用户开户
	 * 
	 * @param cond
	 * @return 结果状态码
	 * @throws Throwable
	 */
	public abstract String sendBgUserRegister(BgUserRegisterCond cond)
			throws Throwable;

	/**
	 * 生成用户登录地址
	 * 
	 * @param usrCustId
	 *            用户客户号
	 * @return 用户登录地址
	 * @throws Throwable
	 */
	public abstract String createLoginUri(String usrCustId) throws Throwable;

	/**
	 * 生成用户信息修改地址
	 * 
	 * @param usrCustId
	 * @return 用户信息修改地址
	 * @throws Throwable
	 */
	public abstract String createEditUri(String usrCustId) throws Throwable;

	/**
	 * 生成企业用户注册地址
	 * 
	 * @param cond
	 * @return 企业用户注册地址
	 * @throws Throwable
	 */
	public abstract String createCorpRegisterUri(CorpRegisterCond cond)
			throws Throwable;

	/**
	 * 企业用户注册返回参数解析
	 * 
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	public abstract CorpRegisterEntity corpRegisterDecoder(
			HttpServletRequest request) throws Throwable;

	/**
	 * 更新企业用户信息
	 * 
	 * @param entity
	 * @throws Throwable
	 */
	public abstract void updateCropInfo(CorpRegisterEntity entity)
			throws Throwable;

	/**
	 * 查询机构审核状态
	 * 
	 * @param busiCode
	 * @throws Throwable
	 */
	public abstract CorpQueryEntity cropInfo(String busiCode) throws Throwable;

	/**
	 * 获取企业审核状态列表
	 * 
	 * @param accout
	 * @param paging
	 * @return
	 * @throws Throwable
	 */
	public abstract PagingResult<CorpAudit> auditStat(String account,
			Paging paging) throws Throwable;
	
	/**
	 * 托管引导页数据查询
	 * <功能详细描述>
	 * @return
	 * @throws Throwable
	 */
    public OpenEscrowGuideEntity getOpenEscrowGuideEntity()
        throws Throwable;
}
