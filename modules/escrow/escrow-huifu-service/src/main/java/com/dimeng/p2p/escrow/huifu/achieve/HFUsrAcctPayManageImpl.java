/*
 * 文 件 名:  HFDonationServiceImpl.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月4日
 */
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
import com.dimeng.p2p.S65.entities.T6554;
import com.dimeng.p2p.S65.entities.T6555;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.UsrAcctPayCond;
import com.dimeng.p2p.escrow.huifu.entity.usracctpay.UsrAcctPayEntity;
import com.dimeng.p2p.escrow.huifu.service.HFUsrAcctPayManage;

/**
 * 公益标捐款管理实现类
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月4日]
 */
public class HFUsrAcctPayManageImpl extends AbstractEscrowService implements HFUsrAcctPayManage
{
    
    public HFUsrAcctPayManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    @Override
    public UsrAcctPayEntity usrAcctPayDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        UsrAcctPayEntity entity = new UsrAcctPayEntity();
        entity.cmdId = request.getParameter("CmdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.ordId = request.getParameter("OrdId");
        entity.usrCustId = request.getParameter("UsrCustId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.transAmt = request.getParameter("TransAmt");
        entity.inAcctId = request.getParameter("InAcctId");
        entity.inAcctType = request.getParameter("InAcctType");
        entity.retUrl = urlDecoder(request.getParameter("RetUrl"));
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.ordId);
        params.add(entity.usrCustId);
        params.add(entity.merCustId);
        params.add(entity.transAmt);
        params.add(entity.inAcctId);
        params.add(entity.inAcctType);
        params.add(entity.retUrl);
        params.add(entity.bgRetUrl);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
        
    }
    
    @Override
    public T6554 selectT6554(int orderId)
        throws Throwable
    {
        T6554 record = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement statement =
                connection.prepareStatement("SELECT F01,F02,F03,F04,F05 FROM S65.T6554 WHERE F01=?"))
            {
                statement.setInt(1, orderId);
                try (ResultSet resultSet = statement.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6554();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getInt(3);
                        record.F04 = resultSet.getBigDecimal(4);
                        record.F05 = resultSet.getInt(5);
                    }
                }
            }
        }
        return record;
    }
    
    /** {@inheritDoc} */
    
    @Override
    public String createUsrAcctPayUrI(UsrAcctPayCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "UsrAcctPay";
        String OrdId = cond.ordId();
        String MerCustId = merCustId;
        String UsrCustId = cond.usrCustId();
        String TransAmt = cond.transAmt();
        String InAcctId = HuiFuConstants.AcctId.ACCT_ID;
        String InAcctType = "MERDT";
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
    public String getUserCustId(int userId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S61.T6119 WHERE T6119.F01 = ?"))
            {
                ps.setInt(1, userId);
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return resultSet.getString(1);
                    }
                }
            }
        }
        return null;
    }
    
    /** {@inheritDoc} */
    
    @Override
    public T6555 selectT6555(int orderId)
        throws Throwable
    {
        T6555 record = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement statement =
                connection.prepareStatement("SELECT F01,F02,F03,F04,F05 FROM S65.T6555 WHERE F01=?"))
            {
                statement.setInt(1, orderId);
                try (ResultSet resultSet = statement.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6555();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getInt(3);
                        record.F04 = resultSet.getBigDecimal(4);
                        record.F05 = resultSet.getInt(5);
                    }
                }
            }
        }
        return record;
    }
    
}
