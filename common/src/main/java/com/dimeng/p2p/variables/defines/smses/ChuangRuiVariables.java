package com.dimeng.p2p.variables.defines.smses;

import java.io.InputStreamReader;

import com.dimeng.framework.config.VariableTypeAnnotation;
import com.dimeng.framework.config.entity.VariableBean;

//@VariableTypeAnnotation(id = "MESSAGECR", name = "创瑞短信发送信息")
public enum ChuangRuiVariables implements VariableBean
{
    /**
     * 短信发送用户id
     */
    SMS_USER_ID("短信发送用户名")
    {
        @Override
        public String getValue()
        {
            return "358128363";
        }
    },
    /**
     * 短信发送用户密码
     */
    SMS_USER_PASSWORD("短信发送用户密码")
    {
        @Override
        public String getValue()
        {
            return "38722D486205C4F09BB662EEBD3F";
        }
    },
    /**
     * 签名
     */
    SMS_SIGN("签名")
    {
        @Override
        public String getValue()
        {
            return "【迪蒙网贷】";
        }
    },
    
    /**
     * 短信发送地址
     */
    SMS_URI("短信发送地址")
    {
        @Override
        public String getValue()
        {
            return "http://web.cr6868.com/asmx/smsservice.aspx";
        }
    };
    
    protected final String key;
    
    protected final String description;
    
    ChuangRuiVariables(String description)
    {
        StringBuilder builder = new StringBuilder(getType());
        builder.append('.').append(name());
        key = builder.toString();
        this.description = description;
    }
    
    @Override
    public String getType()
    {
        return ChuangRuiVariables.class.getAnnotation(VariableTypeAnnotation.class).id();
    }
    
    @Override
    public String getKey()
    {
        return key;
    }
    
    @Override
    public String getDescription()
    {
        return description;
    }
    
    @Override
    public String getValue()
    {
        try (InputStreamReader reader =
            new InputStreamReader(ChuangRuiVariables.class.getResourceAsStream(getKey()), "UTF-8"))
        {
            StringBuilder builder = new StringBuilder();
            char[] cbuf = new char[1024];
            int len = reader.read(cbuf);
            while (len > 0)
            {
                builder.append(cbuf, 0, len);
                len = reader.read(cbuf);
            }
            return builder.toString();
        }
        catch (Throwable t)
        {
        }
        return null;
    }
    
    @Override
    public boolean isInit()
    {
        return false;
    }
}
