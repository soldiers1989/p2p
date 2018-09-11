/*
 * 文 件 名:  HFDonationExecutor.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  lingyuanjie
 * 修改时间:  2016年8月4日
 
package com.dimeng.p2p.escrow.huifu.executor;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import chinapnr.SecureLink;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.data.sql.SQLConnection;
import com.dimeng.framework.resource.AchieveVersion;
import com.dimeng.framework.resource.Resource;
import com.dimeng.framework.resource.ResourceAnnotation;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.S65.entities.T6554;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.order.DonationExecutor;
import com.dimeng.util.StringHelper;

*//**
* 公益标捐款执行器
* <功能详细描述>
* 
* @author  lingyuanjie
* @version  [版本号, 2016年8月4日]
*/
/*
@AchieveVersion(version = 20140715)
@ResourceAnnotation
public class HFDonationExecutor extends DonationExecutor
{
 
 @Override
 public Class<? extends Resource> getIdentifiedType()
 {
     return HFDonationExecutor.class;
 }
 
 *//** <默认构造函数>
*/
/*
public HFDonationExecutor(ResourceProvider resourceProvider)
{
 super(resourceProvider);
}

@Override
protected void doSubmit(SQLConnection connection, int orderId, Map<String, String> ps)
 throws Throwable
{
 T6554 t6554 = selectT6554(connection, orderId);
 if (null == t6554)
 {
     throw new LogicalException("捐款订单不存在!");
 }
 
 ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
 String Version = HuiFuConstants.Version.VERSION_10;
 String CmdId = "UsrAcctPay";
 String OrdId = String.valueOf(t6554.F01);
 String UsrCustId = getUserCustId(connection, t6554.F02);
 if (StringHelper.isEmpty(UsrCustId))
 {
     throw new LogicalException("捐款用户第三方账号不存在!");
 }
 String MerCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
 if (StringHelper.isEmpty(MerCustId))
 {
     throw new LogicalException("平台第三方账号不存在!");
 }
 String TransAmt = String.valueOf(t6554.F04);
 String InAcctId = HuiFuConstants.AcctId.ACCT_ID;
 String InAcctType = "MERDT";
 String RetUrl = configureProvider.format(HuifuVariable.HF_DONATION_TG_RET);
 String BgRetUrl = configureProvider.format(HuifuVariable.HF_DONATION_RET);
 
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
 map.put("UsrCustId", UsrCustId);
 map.put("MerCustId", MerCustId);
 map.put("TransAmt", TransAmt);
 map.put("InAcctId", InAcctId);
 map.put("InAcctType", InAcctType);
 map.put("RetUrl", RetUrl);
 map.put("BgRetUrl", BgRetUrl);
 map.put("ChkValue", ChkValue);
 
 logger.info(String.format("公益标捐款订单：%s 请求参数：%s", t6554.F01, map));
 
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
             logger.info(String.format("公益标捐款订单：%s 返回参数：%s", t6554.F01, rst));
             
             //获取第三方返回结果
             Document doc = Jsoup.parse(rst);
             Element ele = doc.getElementById("RespCode");
             ps.put("result", ele.val());
             
             logger.info(String.format("第三方处理结果码：%s ", ps));
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
*/