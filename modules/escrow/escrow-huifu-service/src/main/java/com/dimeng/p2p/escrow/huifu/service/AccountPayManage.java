package com.dimeng.p2p.escrow.huifu.service;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.Service;
import com.dimeng.p2p.escrow.huifu.cond.AccountPayCond;

public abstract interface AccountPayManage extends Service {
	
	
	public abstract String createUrl(AccountPayCond cond) throws Throwable; 
	
	public abstract int retDecoder(HttpServletRequest request) throws Throwable;
}
