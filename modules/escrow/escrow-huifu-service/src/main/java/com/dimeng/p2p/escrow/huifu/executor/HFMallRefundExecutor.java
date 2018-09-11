/*
 * 文 件 名:  HFMallRefundExecutor.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月6日
 */
package com.dimeng.p2p.escrow.huifu.executor;

import java.math.BigDecimal;
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
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.S65.entities.T6528;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.order.MallRefundExecutor;
import com.dimeng.util.StringHelper;

/**
 * 商品退款执行器
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年8月6日]
 */
@AchieveVersion(version = 20140715)
@ResourceAnnotation
public class HFMallRefundExecutor extends MallRefundExecutor
{
    
    /** <默认构造函数>
     */
    public HFMallRefundExecutor(ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }
    
    @Override
    protected void doSubmit(SQLConnection connection, int orderId, Map<String, String> ps)
        throws Throwable
    {
        T6528 t6528 = selectT6528(connection, orderId);
        if (null == t6528)
        {
            throw new LogicalException("商城退款订单不存在!");
        }
        
        ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "Transfer";
        String OrdId = String.valueOf(t6528.F01);
        String OutCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
        if (StringHelper.isEmpty(OutCustId))
        {
            throw new LogicalException("平台第三方账号不存在!");
        }
        String OutAcctId = HuiFuConstants.AcctId.ACCT_ID;
        String TransAmt = t6528.F03.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String InCustId = getUserCustId(connection, t6528.F02);
        if (StringHelper.isEmpty(InCustId))
        {
            throw new LogicalException("退款用户第三方账号不存在!");
        }
        String InAcctId = HuiFuConstants.AcctId.ACCT_ID;
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_MALL_REFUND_RET);
        String MerPriv = "";
        if (ps != null)
        {
            MerPriv = ps.get("reason") != null ? HttpClientHandler.getBase64Encode(ps.get("reason")) : "";
        }
        
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
        params.add(MerPriv);
        
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
        map.put("MerPriv", MerPriv);
        map.put("ChkValue", ChkValue);
        
        logger.info(String.format("商城退款订单：%s 请求参数：%s", t6528.F01, map));
        
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
                    
                    logger.info(String.format("商城退款订单：%s 返回参数：%s", t6528.F01, rst));
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
}
