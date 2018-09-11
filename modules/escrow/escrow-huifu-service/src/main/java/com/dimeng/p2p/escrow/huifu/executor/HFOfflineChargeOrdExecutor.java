/*
 * 文 件 名:  OfflineChargeOrdExecutor.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月4日
 */
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
import com.dimeng.framework.resource.Resource;
import com.dimeng.framework.resource.ResourceAnnotation;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.S61.entities.T6101;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S65.entities.T6509;
import com.dimeng.p2p.S71.entities.T7150;
import com.dimeng.p2p.S71.enums.T7150_F03;
import com.dimeng.p2p.S71.enums.T7150_F05;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.order.OfflineChargeOrderExecutor;
import com.dimeng.util.StringHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 线下充值审核执行器
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月4日]
 */
@AchieveVersion(version = 20140715)
@ResourceAnnotation
public class HFOfflineChargeOrdExecutor extends OfflineChargeOrderExecutor
{
    
    @Override
    public Class<? extends Resource> getIdentifiedType()
    {
        return HFOfflineChargeOrdExecutor.class;
    }
    
    /** <默认构造函数>
     */
    public HFOfflineChargeOrdExecutor(ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }
    
    @Override
    protected void doSubmit(SQLConnection connection, int orderId, Map<String, String> ps)
        throws Throwable
    {
        
        T6509 t6509 = selectT6509(connection, orderId);
        if (null == t6509)
        {
            throw new LogicalException("线下充值订单不存在!");
        }
        T7150 t7150 = selectT7150(connection, t6509.F06);
        if (null == t7150)
        {
            throw new LogicalException("线下充值申请不存在!");
        }
        // 平台往来账户信息
        T6101 ptwlzh = selectT6101(connection, getPTID(connection), T6101_F03.WLZH, true);
        if (ptwlzh.F06.compareTo(t6509.F03) == -1)
        {
            updateFailT7150(t6509.F06, T7150_F05.YQX, t7150.F08 + " 取消原因：平台账户余额不足");
            
            throw new LogicalException("平台账户余额不足");
        }
        
        ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "Transfer";
        String OrdId = String.valueOf(t6509.F01);
        String OutCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
        if (StringHelper.isEmpty(OutCustId))
        {
            throw new LogicalException("平台第三方账号不存在!");
        }
        String OutAcctId = HuiFuConstants.AcctId.ACCT_ID;
        String TransAmt = t6509.F03.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String InCustId = getUserCustId(connection, t6509.F02);
        if (StringHelper.isEmpty(InCustId))
        {
            throw new LogicalException("充值用户第三方账号不存在!");
        }
        String InAcctId = HuiFuConstants.AcctId.ACCT_ID;
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_OFFLINE_CHARGE_RET);
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(OrdId);
        params.add(OutCustId);
        params.add(OutAcctId);
        params.add(TransAmt);
        params.add(InCustId);
        params.add(InAcctId);
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
        map.put("OrdId", OrdId);
        map.put("OutCustId", OutCustId);
        map.put("OutAcctId", OutAcctId);
        map.put("TransAmt", TransAmt);
        map.put("InCustId", InCustId);
        map.put("InAcctId", InAcctId);
        map.put("TransAmt", TransAmt);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        
        logger.info(String.format("线下充值审核通过订单：%s 请求参数：%s", t6509.F01, map));
        
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
                    
                    logger.info(String.format("线下充值审核通过订单：%s 返回参数：%s", t6509.F01, rst));
                    
                    Gson gson = new Gson();
                    Map<String, String> mapRst = gson.fromJson(rst, new TypeToken<Map<String, String>>()
                    {
                    }.getType());
                    //取第三方返回结果
                    ps.put("result", HuiFuConstants.Common.SUCCESS_CODE.equals(mapRst.get("RespCode")) ? "success"
                        : "fail");
                    //                    //获取第三方返回结果
                    //                    Document doc = Jsoup.parse(rst);
                    //                    Element ele = doc.getElementById("RespCode");
                    //                    ps.put("result", ele.val());
                    
                    logger.info(String.format("第三方处理结果码：%s ", ps.toString()));
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
    
    @Override
    protected T7150 selectT7150(Connection connection, int F01)
        throws SQLException
    {
        T7150 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11 FROM S71.T7150 WHERE T7150.F01 = ?"))
        {
            pstmt.setInt(1, F01);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T7150();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = T7150_F03.parse(resultSet.getString(3));
                    record.F04 = resultSet.getBigDecimal(4);
                    record.F05 = T7150_F05.parse(resultSet.getString(5));
                    record.F06 = resultSet.getInt(6);
                    record.F07 = resultSet.getTimestamp(7);
                    record.F08 = resultSet.getString(8);
                    record.F09 = resultSet.getInt(9);
                    record.F10 = resultSet.getTimestamp(10);
                    record.F11 = resultSet.getString(11);
                }
            }
        }
        return record;
    }
    
    public void updateFailT7150(int sqId, T7150_F05 status, String checkReason)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("UPDATE S71.T7150 SET F05 = ?, F08 = ? WHERE F01 = ?"))
            {
                pstmt.setString(1, status.name());
                pstmt.setString(2, checkReason);
                pstmt.setInt(3, sqId);
                pstmt.executeUpdate();
            }
        }
    }
}
