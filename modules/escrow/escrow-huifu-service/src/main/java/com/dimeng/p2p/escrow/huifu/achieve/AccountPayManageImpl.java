package com.dimeng.p2p.escrow.huifu.achieve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.ServiceFactory;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.AccountPayCond;
import com.dimeng.p2p.escrow.huifu.service.AccountPayManage;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 用户支付管理
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月21日]
 */
public class AccountPayManageImpl extends AbstractEscrowService implements AccountPayManage
{
    
    public AccountPayManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class AccountPayManageFactory implements ServiceFactory<AccountPayManage>
    {
        
        @Override
        public AccountPayManage newInstance(ServiceResource serviceResource)
        {
            return new AccountPayManageImpl(serviceResource);
        }
        
    }
    
    @Override
    public String createUrl(AccountPayCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "UsrAcctPay";
        String OrdId = cond.ordId();
        String UsrCustId = cond.usrCustId();
        String MerCustId = merCustId;
        String TransAmt = cond.transAmt();
        String InAcctId = cond.inAcctId();
        String InAcctType = cond.inAcctType();
        String RetUrl = cond.retUrl();
        String BgRetUrl = cond.bgRetUrl();
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(OrdId);
        params.add(UsrCustId);
        params.add(MerCustId);
        params.add(TransAmt);
        params.add(InAcctId);
        params.add(InAcctType);
        params.add(RetUrl);
        params.add(BgRetUrl);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("OrdId", OrdId);
        map.put("UsrCustId", UsrCustId);
        map.put("MerCustId", MerCustId);
        map.put("TransAmt", TransAmt);
        map.put("InAcctId", InAcctId);
        map.put("InAcctType", InAcctType);
        map.put("RetUrl", RetUrl);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        
        return concatUrl(map);
    }
    
    @Override
    public int retDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        
        return IntegerParser.parse(request.getParameter("OrdId"));
    }
    
}
