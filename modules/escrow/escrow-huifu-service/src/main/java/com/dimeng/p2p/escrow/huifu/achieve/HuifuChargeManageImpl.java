package com.dimeng.p2p.escrow.huifu.achieve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.ServiceFactory;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.ChargeCond;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeEntity;
import com.dimeng.p2p.escrow.huifu.service.HuifuChargeManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;

public class HuifuChargeManageImpl extends AbstractEscrowService implements HuifuChargeManage
{
    
    public HuifuChargeManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class ChargeManageFactory implements ServiceFactory<HuifuChargeManage>
    {
        
        @Override
        public HuifuChargeManage newInstance(ServiceResource serviceResource)
        {
            return new HuifuChargeManageImpl(serviceResource);
        }
        
    }
    
    @Override
    public String createChargeUrI(ChargeCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "NetSave";
        String MerCustId = merCustId;
        String UsrCustId = cond.usrCustId();
        String OrdId = cond.ordId();
        String OrdDate = cond.ordDate();
        String GateBusid = cond.GateBusiId();
        String Openbankid = cond.OpenBankId();
        String DcFlag = cond.dcFlag();
        String TransAmt = cond.transAmt().toString();
        String RetUrl = cond.retUrl();
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_CHARGE);
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrCustId);
        params.add(OrdId);
        params.add(OrdDate);
        params.add(GateBusid);
        params.add(Openbankid);
        params.add(DcFlag);
        params.add(TransAmt);
        params.add(RetUrl);
        params.add(BgRetUrl);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        map.put("OrdId", OrdId);
        map.put("OrdDate", OrdDate);
        map.put("GateBusid", GateBusid);
        map.put("Openbankid", Openbankid);
        map.put("DcFlag", DcFlag);
        map.put("TransAmt", TransAmt);
        map.put("RetUrl", RetUrl);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        
        return concatUrl(map);
    }
    
    @Override
    public ChargeEntity chrageRetDecode(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        ChargeEntity entity = new ChargeEntity();
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        entity.cmdId = request.getParameter("CmdId");
        entity.feeAcctId = request.getParameter("FeeAcctId");
        entity.feeAmt = request.getParameter("FeeAmt");
        entity.feeCustId = request.getParameter("FeeCustId");
        entity.gateBankId = request.getParameter("GateBankId");
        entity.gateBusiId = request.getParameter("GateBusiId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.merPriv = urlDecoder(request.getParameter("MerPriv"));
        entity.ordDate = request.getParameter("OrdDate");
        entity.ordId = request.getParameter("OrdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.retUrl = request.getParameter("RetUrl");
        entity.transAmt = request.getParameter("TransAmt");
        entity.trxId = request.getParameter("TrxId");
        entity.usrCustId = request.getParameter("UsrCustId");
        //CardId不为空表示绑定了快捷卡
        String cardId = request.getParameter("CardId");
        if (!"".equals(cardId) && cardId != null)
        {
            entity.cardId = request.getParameter("CardId");
        }
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.usrCustId);
        params.add(entity.ordId);
        params.add(entity.ordDate);
        params.add(entity.transAmt);
        params.add(entity.trxId);
        params.add(entity.retUrl);
        params.add(entity.bgRetUrl);
        params.add(entity.merPriv);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
}
