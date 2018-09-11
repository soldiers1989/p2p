package com.dimeng.p2p.escrow.huifu.achieve;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import chinapnr.Base64;
import chinapnr.SecureLink;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.data.sql.SQLConnectionProvider;
import com.dimeng.framework.resource.ResourceNotFoundException;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.query.ItemParser;
import com.dimeng.p2p.S65.entities.T6501;
import com.dimeng.p2p.S65.entities.T6517;
import com.dimeng.p2p.S65.enums.T6501_F03;
import com.dimeng.p2p.S65.enums.T6501_F07;
import com.dimeng.p2p.escrow.huifu.util.HttpClientHandler;
import com.dimeng.p2p.escrow.huifu.util.MD5;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.service.AbstractP2PService;
import com.dimeng.p2p.variables.P2PConst;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BigDecimalParser;
import com.dimeng.util.parser.BooleanParser;
import com.dimeng.util.parser.DateTimeParser;
import com.dimeng.util.parser.EnumParser;
import com.dimeng.util.parser.IntegerParser;

public class AbstractEscrowService extends AbstractP2PService
{
    
    protected final String idType = "00"; // 证件类型，身份证
    
    protected final String charSet = "UTF-8"; // 参数解码格式
    
    protected ConfigureProvider configureProvider;
    
    protected String merCustId; // 商户客户号
    
    protected String url; // 汇付提交地址
    
    /** MD5签名类型 **/
    protected final String SIGN_TYPE_MD5 = "M";
    
    /** RSA签名类型 **/
    protected final String SIGN_TYPE_RSA = "R";
    
    /** RSA验证签名成功结果 **/
    protected final int RAS_VERIFY_SIGN_SUCCESS = 0;
    
    protected static final Map<String, String> bankMap = new HashMap<>();
    
    static
    {
        bankMap.put("ICBC", "工商银行");
        bankMap.put("ABC", "农业银行");
        bankMap.put("CMB", "招商银行");
        bankMap.put("CCB", "建设银行");
        bankMap.put("BCCB", "北京银行");
        bankMap.put("BJRCB", "北京农村商业银行");
        bankMap.put("BOC", "中国银行");
        bankMap.put("BOCOM", "交通银行");
        bankMap.put("CMBC", "民生银行");
        bankMap.put("BOS", "上海银行");
        bankMap.put("CBHB", "渤海银行");
        bankMap.put("CEB", "光大银行");
        bankMap.put("CIB", "兴业银行");
        bankMap.put("CITIC", "中信银行");
        bankMap.put("CZB", "浙商银行");
        bankMap.put("GDB", "广发银行");
        bankMap.put("HKBEA", "东亚银行");
        bankMap.put("HXB", "华夏银行");
        bankMap.put("HZCB", "杭州银行");
        bankMap.put("NJCB", "南京银行");
        bankMap.put("PINGAN", "平安银行");
        bankMap.put("PSBC", "邮储银行");
        bankMap.put("SDB", "深发银行");
        bankMap.put("SPDB", "浦发银行");
        bankMap.put("SRCB", "上海农村商业银行");
    }
    
    public AbstractEscrowService(ServiceResource serviceResource)
    {
        super(serviceResource);
        configureProvider = serviceResource.getResource(ConfigureProvider.class);
        merCustId = configureProvider.getProperty(HuifuVariable.HF_CUST_ID);
        url = configureProvider.getProperty(HuifuVariable.HF_URL);
    }
    
    @Override
    protected Connection getConnection()
        throws ResourceNotFoundException, SQLException
    {
        return serviceResource.getDataConnectionProvider(SQLConnectionProvider.class, P2PConst.DB_MASTER_PROVIDER)
            .getConnection(P2PConst.DB_CONSOLE);
    }
    
    @Override
    protected Connection getConnection(String db)
        throws ResourceNotFoundException, SQLException
    {
        return serviceResource.getDataConnectionProvider(SQLConnectionProvider.class, P2PConst.DB_MASTER_PROVIDER)
            .getConnection(db);
    }
    
    protected String base64(String str)
    {
        if (StringHelper.isEmpty(str))
        {
            return null;
        }
        return String.valueOf(Base64.encode(str.getBytes()));
    }
    
    /**
     * 发送请求
     * 
     * @param params
     *            请求参数
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    protected String doPost(Map<String, String> params)
        throws ClientProtocolException, IOException
    {
        String result = null;
        List<NameValuePair> nvps = HttpClientHandler.buildNameValuePair(params);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        EntityBuilder builder = EntityBuilder.create();
        try
        {
            logger.info("请求地址：" + url + "，请求参数：" + params);
            HttpPost httpPost = new HttpPost(url);
            builder.setParameters(nvps);
            httpPost.setEntity(builder.build());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            
            try
            {
                HttpEntity entity = response.getEntity();
                if (response.getStatusLine().getReasonPhrase().equals("OK")
                    && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                {
                    result = EntityUtils.toString(entity, charSet);
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
        logger.info("返回参数：" + result);
        return result;
    }
    
    public String doPostNew(Map<String, String> params)
        throws ClientProtocolException, IOException
    {
        String result = null;
        
        if (params != null)
        {
            logger.info("请求地址：" + url + "，请求参数：" + params);
            UrlEncodedFormEntity formEntiry = HttpClientHandler.buildUrlEncodedFormEntity(params);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(formEntiry);
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try
            {
                CloseableHttpResponse response = httpclient.execute(httpPost);
                try
                {
                    HttpEntity entity = response.getEntity();
                    if (response.getStatusLine().getReasonPhrase().equals("OK")
                        && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
                        result = EntityUtils.toString(entity, charSet);
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
        logger.info("返回参数：" + result);
        return result;
    }
    
    public int pageCount(int totalItems, int pageSize)
    {
        return totalItems % pageSize == 0 ? totalItems / pageSize : (totalItems / pageSize + 1);
    }
    
    /**
     * 拼接url地址
     * 
     * @param params
     * @return
     * @throws Throwable
     */
    protected String concatUrl(Map<String, String> params)
        throws Throwable
    {
        StringBuffer buffer = new StringBuffer(configureProvider.format(HuifuVariable.HF_URL));
        int i = 0;
        for (Entry<String, String> entry : params.entrySet())
        {
            if (StringHelper.isEmpty(entry.getValue()))
            {
                continue;
            }
            i++;
            if (i == 1)
            {
                buffer.append("?");
                buffer.append(entry.getKey());
                buffer.append("=");
                buffer.append(entry.getValue());
                continue;
            }
            buffer.append("&");
            buffer.append(entry.getKey());
            buffer.append("=");
            buffer.append(entry.getValue());
        }
        return buffer.toString();
    }
    
    /**
     * RSA方式加签
     * 
     * @param custId
     * @param forEncryptionStr
     * @param charset
     * @return
     * @throws Exception
     */
    protected String encryptByRSA(String forEncryptionStr)
        throws Exception
    {
        String RECV_MER_ID = configureProvider.getProperty(HuifuVariable.HF_ACCOUNT_ID);
        String MER_PRI_KEY_PATH = configureProvider.getProperty(HuifuVariable.HF_PRI_KEY_PATH);
        SecureLink sl = new SecureLink();
        int result = sl.SignMsg(RECV_MER_ID, MER_PRI_KEY_PATH, forEncryptionStr);
        if (result < 0)
        {
            // 打印日志
            throw new Exception("RSA加密失败");
        }
        return sl.getChkValue();
    }
    
    public boolean verifyByRSA(String forEncryptionStr, String chkValue)
        throws Exception
    {
        try
        {
            String MER_PUB_KEY_PATH = configureProvider.getProperty(HuifuVariable.HF_PUB_KEY_PATH);
            int verifySignResult = new SecureLink().VeriSignMsg(MER_PUB_KEY_PATH, forEncryptionStr, chkValue);
            return verifySignResult == RAS_VERIFY_SIGN_SUCCESS;
        }
        catch (Exception e)
        {
            logger.error(e, e);
            // 打印日志
            throw new Exception();
        }
    }
    
    /**
     * 获取需要签名的字段拼接的字符串
     * 
     * @param params
     *            参数列表
     * @return
     * @throws Exception
     */
    protected String forEncryptionStr(List<String> params)
        throws Exception
    {
        if (params == null)
        {
            return null;
        }
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
        return buffer == null ? null : buffer.toString();
    }
    
    /**
     * 获取签名字符串
     * 
     * @param params
     *            参加签名的参数列表
     * @return
     * @throws Exception
     */
    protected String chkValue(List<String> params)
        throws Exception
    {
        String str = forEncryptionStr(params);
        logger.info("签名前的字符串：" + str);
        return StringHelper.isEmpty(str) ? null : encryptByRSA(str);
    }
    
    protected String chkValueNew(List<String> params)
        throws Exception
    {
        String str = forEncryptionStr(params);
        logger.info("签名前的字符串：" + str);
        str = MD5.encryption(str);
        logger.info("MD5加密后的字符串：" + str);
        return StringHelper.isEmpty(str) ? null : encryptByRSA(str);
    }
    
    protected String urlDecoder(String str)
        throws Exception
    {
        if (!StringHelper.isEmpty(str))
        {
            return URLDecoder.decode(str, charSet);
        }
        return null;
    }
    
    protected String urlEncode(String str)
        throws Exception
    {
        if (!StringHelper.isEmpty(str))
        {
            return URLEncoder.encode(str, charSet);
        }
        return null;
    }
    
    /**
     * 简单的json解析，只支持键值对型字符串
     * 
     * @param json
     * @return
     */
    public static Map<String, String> jsonParse(String json)
    {
        String[] items = json.trim().substring(1, json.length() - 1).split(",");
        Map<String, String> params = new HashMap<>();
        if (items != null && items.length > 0)
        {
            for (String item : items)
            {
                if (item == null)
                {
                    continue;
                }
                String[] em = item.split(":");
                if (em != null && em.length == 2)
                {
                    String em0 = em[0].trim();
                    String em1 = em[1].trim();
                    params.put(em0.substring(1, em0.length() - 1), em1.substring(1, em1.length() - 1));
                }
            }
        }
        return params;
    }
    
    protected int getAccountId(String userCustId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01 FROM S61.T6119 WHERE T6119.F03 = ? LIMIT 1"))
            {
                pstmt.setString(1, userCustId);
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
    
    protected void parameters(HttpServletRequest request)
        throws Throwable
    {
        Set<Entry<String, String[]>> params = request.getParameterMap().entrySet();
        StringBuilder builder =
            new StringBuilder(String.format("%s%s返回参数：",
                request.getParameter("CmdId"),
                DateTimeParser.format(new Date())));
        for (Entry<String, String[]> entry : params)
        {
            builder.append("&");
            builder.append(entry.getKey());
            builder.append("=");
            String value = entry.getValue()[0];
            if ("BgRetUrl".equals(entry.getKey()) || "RespDesc".equals(entry.getKey())
                || "RetUrl".equals(entry.getKey()) || "UsrName".equals(entry.getKey())
                || "AuditDesc".equals(entry.getKey()))
            {
                value = urlDecoder(value);
            }
            builder.append(value);
        }
        logger.info(builder.toString());
    }
    
    protected void trxId(String trxId, int orderId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("UPDATE S65.T6501 SET F10 = ? WHERE F01 = ?"))
            {
                ps.setString(1, trxId);
                ps.setInt(2, orderId);
                ps.executeUpdate();
            }
        }
    }
    
    protected String userCustId(int accountId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S61.T6119 WHERE F01 = ?"))
            {
                ps.setInt(1, accountId);
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
    
    protected T6501 selectT6501(Connection connection, int orderId)
        throws SQLException
    {
        T6501 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10 FROM S65.T6501 WHERE T6501.F01 = ? FOR UPDATE"))
        {
            pstmt.setInt(1, orderId);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6501();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = T6501_F03.parse(resultSet.getString(3));
                    record.F04 = resultSet.getTimestamp(4);
                    record.F05 = resultSet.getTimestamp(5);
                    record.F06 = resultSet.getTimestamp(6);
                    record.F07 = EnumParser.parse(T6501_F07.class, resultSet.getString(7));
                    record.F08 = resultSet.getInt(8);
                    record.F09 = resultSet.getInt(9);
                    record.F10 = resultSet.getString(10);
                }
            }
        }
        return record;
    }
    
    protected T6517 selectT6517(Connection connection, int orderId)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT t1.* from S65.T6517 t1,S65.T6501 t2 where t1.F01 = t2.F01 and t1.F01 = ?"))
        {
            pstmt.setInt(1, orderId);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    T6517 record = new T6517();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getBigDecimal(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getString(5);
                    record.F06 = resultSet.getInt(6);
                    return record;
                }
            }
        }
        return null;
    }
    
    private String getParamValue(String json, String name)
    {
        String regex = "\"" + name + "\":\"[^\"]*\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find())
        {
            String value = matcher.group();
            String[] em = value.split(":");
            if (em != null && em.length == 2)
            {
                String em1 = em[1].trim();
                return em1.substring(1, em1.length() - 1);
            }
        }
        return matcher.find() ? matcher.group() : null;
    }
    
    private <T> List<T> getListParamValue(String json, String name, Class<T> valueType)
        throws Throwable
    {
        String regex = "\"" + name + "\":\\[.*\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);
        if (matcher.find())
        {
            String mJson = matcher.group();
            Pattern itemPattern = Pattern.compile("\\{[^\\{]+\\}");
            Matcher itemMatcher = itemPattern.matcher(mJson);
            List<T> list = null;
            while (itemMatcher.find())
            {
                if (list == null)
                {
                    list = new ArrayList<>();
                }
                String itemJson = itemMatcher.group();
                list.add(jsonReflectParser(itemJson, valueType));
            }
            return list;
        }
        return null;
    }
    
    protected <T> T jsonReflectParser(String json, Class<T> valueType)
        throws Throwable
    {
        T obj = valueType.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            String type = field.getGenericType().toString();
            if (type.equals("int"))
            {
                String name = field.getName();
                String value = getParamValue(json, name);
                if (!StringHelper.isEmpty(value))
                {
                    Method method = valueType.getMethod("set" + name, int.class);
                    method.invoke(obj, IntegerParser.parse(value));
                }
            }
            else if (type.equals("long"))
            {
                String name = field.getName();
                String value = getParamValue(json, name);
                if (!StringHelper.isEmpty(value))
                {
                    Method method = valueType.getMethod("set" + name, long.class);
                    method.invoke(obj, IntegerParser.parse(value));
                }
            }
            else if (type.equals("class java.lang.String"))
            {
                String name = field.getName();
                String value = getParamValue(json, name);
                if (!StringHelper.isEmpty(value))
                {
                    Method method = valueType.getMethod("set" + name, String.class);
                    method.invoke(obj, value);
                }
            }
            else if (type.equals("class java.lang.Integer"))
            {
                String name = field.getName();
                String value = getParamValue(json, name);
                if (!StringHelper.isEmpty(value))
                {
                    Method method = valueType.getMethod("set" + name, Integer.class);
                    method.invoke(obj, IntegerParser.parse(value));
                }
            }
            else if (type.equals("class java.lang.Boolean"))
            {
                String name = field.getName();
                String value = getParamValue(json, name);
                if (!StringHelper.isEmpty(value))
                {
                    Method method = valueType.getMethod("set" + name, Boolean.class);
                    method.invoke(obj, BooleanParser.parse(value));
                }
            }
            else if (type.equals("class java.util.Date"))
            {
                String name = field.getName();
                String value = getParamValue(json, name);
                if (!StringHelper.isEmpty(value))
                {
                    Method method = valueType.getMethod("set" + name, Date.class);
                    method.invoke(obj, DateTimeParser.parse(value));
                }
            }
            else if (type.equals("class java.math.BigDecimal"))
            {
                String name = field.getName();
                String value = getParamValue(json, name);
                if (!StringHelper.isEmpty(value))
                {
                    Method method = valueType.getMethod("set" + name, BigDecimal.class);
                    method.invoke(obj, BigDecimalParser.parse(value));
                }
            }
            else if (type.startsWith("java.util.List"))
            {
                String name = field.getName();
                Method method = valueType.getMethod("set" + name, field.getType());
                String icn = type.replaceAll("java.util.List<", "").replaceAll(">", "").trim();
                method.invoke(obj, getListParamValue(json, name, Class.forName(icn)));
            }
        }
        return obj;
    }
    
    @Override
    protected Timestamp getCurrentTimestamp(Connection connection)
        throws Throwable
    {
        try (PreparedStatement pstmt = connection.prepareStatement("SELECT CURRENT_TIMESTAMP()"))
        {
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    return resultSet.getTimestamp(1);
                }
            }
        }
        return null;
    }
    
    protected BigDecimal selectBigDecimal(Connection connection, String sql, Object... paramters)
        throws SQLException
    {
        final BigDecimal decimal = new BigDecimal(0);
        return select(connection, new ItemParser<BigDecimal>()
        {
            @Override
            public BigDecimal parse(ResultSet resultSet)
                throws SQLException
            {
                if (resultSet.next())
                {
                    return resultSet.getBigDecimal(1);
                }
                return decimal;
            }
        }, sql, paramters);
    }
    
}
