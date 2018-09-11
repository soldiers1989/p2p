package com.dimeng.p2p.escrow.huifu.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import chinapnr.Base64;

public class HttpClientHandler
{
    
    protected static final Logger logger = Logger.getLogger(HttpClientHandler.class);
    
    /** 汇付HOST **/
    public static final String HTTP_HOST = "http://mertest.chinapnr.com/muser/publicRequests";
    
    /**
     * MAP类型数组转换成NameValuePair类型
     * 
     */
    public static List<NameValuePair> buildNameValuePair(Map<String, String> params)
    {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null)
        {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        
        return nvps;
    }
    
    public static UrlEncodedFormEntity buildUrlEncodedFormEntity(Map<String, String> params)
        throws UnsupportedEncodingException
    {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if (params != null)
        {
            for (Map.Entry<String, String> entry : params.entrySet())
            {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        UrlEncodedFormEntity formEntity = null;
        formEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
        return formEntity;
    }
    
    public static String getBase64Encode(String str)
    {
        if (str == null || "".equals(str))
        {
            return "";
        }
        try
        {
            byte[] bt = str.getBytes("UTF-8");
            str = String.valueOf(Base64.encode(bt));
        }
        catch (UnsupportedEncodingException e)
        {
            logger.error(e, e);
        }
        return str;
    }
    
    public static String getBase64Decode(String str)
    {
        if (str == null || "".equals(str))
        {
            return "";
        }
        char[] ch = str.toCharArray();
        byte[] bt = Base64.decode(ch);
        try
        {
            str = new String(bt, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            logger.error(e, e);
        }
        return str;
    }
    
}
