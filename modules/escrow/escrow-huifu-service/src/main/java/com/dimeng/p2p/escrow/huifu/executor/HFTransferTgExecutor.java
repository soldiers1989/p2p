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
import com.dimeng.p2p.S65.entities.T6517;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.order.TransferExecutor;
import com.dimeng.util.StringHelper;

/**
 * 
 * 转账执行器
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月21日]
 */
@AchieveVersion(version = 20140922)
@ResourceAnnotation
public class HFTransferTgExecutor extends TransferExecutor
{
    
    public HFTransferTgExecutor(ResourceProvider resourceProvider)
    {
        super(resourceProvider);
    }
    
    @Override
    protected void doSubmit(SQLConnection connection, int orderId, Map<String, String> ps)
        throws Throwable
    {
        T6517 t6517 = selectT6517(connection, orderId);
        if (t6517 == null)
        {
            throw new LogicalException(String.format("转账订单%s不存在", orderId));
        }
        ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "Transfer";
        String OrdId = String.valueOf(orderId);
        String OutCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
        if (StringHelper.isEmpty(OutCustId))
        {
            return;
        }
        String OutAcctId = HuiFuConstants.AcctId.ACCT_ID;
        String TransAmt = t6517.F02.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        String InCustId = getUserCustId(connection, t6517.F04);
        if (StringHelper.isEmpty(InCustId))
        {
            return;
        }
        String InAcctId = HuiFuConstants.AcctId.ACCT_ID;
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_TRANSFER);
        
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
        
        logger.info(String.format("转账代扣订单%s请求参数：%s", orderId, map));
        
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
                    logger.info(String.format("转账代扣订单%s返回参数：%s", orderId, rst));
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
