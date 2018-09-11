package com.dimeng.p2p.escrow.huifu.achieve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.service.ServiceResource;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.FreezeCond;
import com.dimeng.p2p.escrow.huifu.entity.freeze.FreezeEntity;
import com.dimeng.p2p.escrow.huifu.service.FreezeManage;

public class FreezeManageImpl extends AbstractEscrowService implements FreezeManage
{
    
    public FreezeManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    @Override
    public void sendFreeze(FreezeCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "UsrFreezeBg";
        String MerCustId = merCustId;
        String UsrCustId = cond.usrCustId();
        String SubAcctType = cond.subAcctType();
        String SubAcctId = cond.subAcctId();
        String OrdId = cond.ordId();
        String OrdDate = cond.ordDate();
        String TransAmt = cond.transAmt();
        String RetUrl = cond.retUrl();
        String BgRetUrl = cond.bgRetUrl();
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrCustId);
        params.add(SubAcctType);
        params.add(SubAcctId);
        params.add(OrdId);
        params.add(OrdDate);
        params.add(TransAmt);
        params.add(RetUrl);
        params.add(BgRetUrl);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        map.put("SubAcctType", SubAcctType);
        map.put("SubAcctId", SubAcctId);
        map.put("OrdId", OrdId);
        map.put("OrdDate", OrdDate);
        map.put("TransAmt", TransAmt);
        map.put("RetUrl", RetUrl);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        
        doPost(map);
    }
    
    @Override
    public FreezeEntity freezeRetDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        FreezeEntity entity = new FreezeEntity();
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        entity.cmdId = request.getParameter("CmdId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.ordDate = request.getParameter("OrdDate");
        entity.ordId = request.getParameter("OrdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.retUrl = urlDecoder(request.getParameter("RetUrl"));
        entity.subAcctId = request.getParameter("SubAcctId");
        entity.subAcctType = request.getParameter("SubAcctType");
        entity.transAmt = request.getParameter("TransAmt");
        entity.trxId = request.getParameter("TrxId");
        entity.usrCustId = request.getParameter("UsrCustId");
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.usrCustId);
        params.add(entity.subAcctType);
        params.add(entity.subAcctId);
        params.add(entity.ordId);
        params.add(entity.ordDate);
        params.add(entity.transAmt);
        params.add(entity.retUrl);
        params.add(entity.bgRetUrl);
        params.add(entity.trxId);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public void updateTrxId(String trxId, int orderId)
        throws Throwable
    {
        trxId(trxId, orderId);
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps =
                connection.prepareStatement("UPDATE S65.T6501 SET F03 = ?, F06 = ? WHERE F01 = ?"))
            {
                ps.setString(1, T6501_F03.CG.name());
                ps.setTimestamp(2, getCurrentTimestamp(connection));
                ps.setInt(3, orderId);
                ps.executeUpdate();
            }
        }
    }
    
    @Override
    public int selectT6515(int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F02 FROM S65.T6515 WHERE T6515.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, orderId);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return resultSet.getInt(1);
                    }
                }
            }
        }
        return 0;
    }
    
}
