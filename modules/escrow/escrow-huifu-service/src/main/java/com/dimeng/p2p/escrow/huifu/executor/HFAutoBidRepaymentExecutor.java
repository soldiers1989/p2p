package com.dimeng.p2p.escrow.huifu.executor;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import chinapnr.SecureLink;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.data.sql.SQLConnection;
import com.dimeng.framework.resource.AchieveVersion;
import com.dimeng.framework.resource.ResourceAnnotation;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.p2p.FeeCode;
import com.dimeng.p2p.S62.entities.T6230;
import com.dimeng.p2p.S62.entities.T6238;
import com.dimeng.p2p.S62.entities.T6251;
import com.dimeng.p2p.S62.entities.T6253;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6504;
import com.dimeng.p2p.S65.entities.T6506;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.order.AutoTenderRepaymentExecutor;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.DateParser;

/**
 * 
 * 自动还款执行器
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月31日]
 */
@AchieveVersion(version = 20140715)
@ResourceAnnotation
public class HFAutoBidRepaymentExecutor extends AutoTenderRepaymentExecutor
{
    
    public HFAutoBidRepaymentExecutor(ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }
    
    @Override
    protected void doSubmit(SQLConnection connection, int orderId, Map<String, String> ps)
        throws Throwable
    {
        ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        T6501 t6501 = selectT6501(connection, orderId); //订单表
        T6506 t6506 = selectT6506(connection, orderId); //还款订单
        T6230 t6230 = selectT6230(connection, t6506.F03); //标信息
        T6251 t6251 = selectT6251(connection, t6506.F04); //标债权记录
        T6253 t6253 = selectT6253(connection, t6230.F01); //垫付记录
        T6501 yT6501 = null;
        if (t6251.F12 > 0)
        {
            // 有债权转让
            yT6501 = selectT6501(connection, t6251.F12);
        }
        else
        {
            // 没有债权转让
            T6504 t6504 = selectT6504(connection, t6251.F11);
            yT6501 = selectT6501(connection, t6504.F01);
        }
        
        BigDecimal fee = new BigDecimal(0);
        if ((t6506.F07 == FeeCode.TZ_LX || t6506.F07 == FeeCode.TZ_FX) && t6253 == null)
        {
            // 计算投资管理费
            T6238 t6238 = selectT6238(connection, t6230.F01);
            fee = t6506.F06.multiply(t6238.F03).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        String Version = HuiFuConstants.Version.VERSION_20;
        String CmdId = "Repayment";
        String MerCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
        String OrdId = String.valueOf(orderId);
        String OrdDate = DateParser.format(t6501.F04, "yyyyMMdd");
        String OutCustId = usrCustId(connection, t6230.F02);
        String SubOrdId = String.valueOf(yT6501.F01);
        String SubOrdDate = DateParser.format(yT6501.F04, "yyyyMMdd");
        String TransAmt = t6506.F06.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String Fee = fee.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String InCustId = usrCustId(connection, t6506.F02);
        String InAcctId = HuiFuConstants.AcctId.ACCT_ID;
        String DivDetails = null;
        if (fee.compareTo(new BigDecimal(0)) > 0)
        {
            StringBuilder builder = new StringBuilder();
            builder.append("[{\"DivCustId\":\"");
            builder.append(configureProvider.getProperty(HuifuVariable.HF_CUST_ID));
            builder.append("\",\"DivAcctId\":\"" + HuiFuConstants.AcctId.ACCT_ID + "\",\"DivAmt\":\"");
            builder.append(Fee);
            builder.append("\"}]");
            DivDetails = builder.toString();
        }
        String FeeObjFlag = "I";
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_REPAYMENT);
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(OrdId);
        params.add(OrdDate);
        params.add(OutCustId);
        params.add(SubOrdId);
        params.add(SubOrdDate);
        params.add(TransAmt);
        params.add(Fee);
        params.add(InCustId);
        params.add(InAcctId);
        params.add(DivDetails);
        params.add(FeeObjFlag);
        params.add(BgRetUrl);
        
        StringBuffer buffer = null;
        for (String param : params)
        {
            if (StringHelper.isEmpty(param))
            {
                continue;
            }
            if (buffer == null)
            {
                buffer = new StringBuffer();
            }
            buffer.append(StringUtils.trimToEmpty(param));
        }
        String RECV_MER_ID = configureProvider.getProperty(HuifuVariable.HF_ACCOUNT_ID);
        String MER_PRI_KEY_PATH = configureProvider.getProperty(HuifuVariable.HF_PRI_KEY_PATH);
        SecureLink sl = new SecureLink();
        int result = sl.SignMsg(RECV_MER_ID, MER_PRI_KEY_PATH, buffer.toString());
        if (result < 0)
        {
            // 打印日志
            throw new Exception();
        }
        String ChkValue = sl.getChkValue();
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("OrdId", OrdId);
        map.put("OrdDate", OrdDate);
        map.put("OutCustId", OutCustId);
        map.put("SubOrdId", SubOrdId);
        map.put("SubOrdDate", SubOrdDate);
        map.put("TransAmt", TransAmt);
        map.put("Fee", Fee);
        map.put("InCustId", InCustId);
        map.put("InAcctId", InAcctId);
        map.put("DivDetails", DivDetails);
        map.put("FeeObjFlag", FeeObjFlag);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        
        logger.info(String.format("自动还款订单%s请求参数：%s", orderId, map));
        
        List<NameValuePair> nvps = HttpClientHandler.buildNameValuePair(map);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        EntityBuilder builder = EntityBuilder.create();
        try
        {
            HttpPost httpPost = new HttpPost(configureProvider.getProperty(HuifuVariable.HF_URL));
            builder.setParameters(nvps);
            httpPost.setEntity(builder.build());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            
            try
            {
                HttpEntity entity = response.getEntity();
                if (response.getStatusLine().getReasonPhrase().equals("OK")
                    && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    String rst = EntityUtils.toString(entity, "UTF-8");
                    logger.info(String.format("自动还款订单%s返回参数：%s", orderId, rst));
                }
                EntityUtils.consume(entity);
            }
            finally
            {
                response.close();
            }
        }
        finally
        {
            httpclient.close();
        }
    }
    
    private String usrCustId(Connection connection, int accoutId)
        throws SQLException
    {
        try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S61.T6119 WHERE T6119.F01 = ?"))
        {
            ps.setInt(1, accoutId);
            try (ResultSet resultSet = ps.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getString(1);
                }
            }
        }
        return null;
    }
    
    private T6504 selectT6504(Connection connection, int F05)
        throws SQLException
    {
        T6504 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05 FROM S65.T6504 WHERE T6504.F05 = ? LIMIT 1"))
        {
            pstmt.setInt(1, F05);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6504();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = resultSet.getInt(5);
                }
            }
        }
        return record;
    }
    
}
