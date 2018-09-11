/*
 * 文 件 名:  UpdateQyJszl.java
 * 版    权:  深圳市迪蒙网络科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  huqinfu
 * 修改时间:  2016年8月23日
 */
package com.dimeng.p2p.user.servlets.account;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.S61.entities.T6162;
import com.dimeng.p2p.account.user.service.QyBaseManage;

/**
 * 修改企业介绍资料
 * 
 * @author  huqinfu
 * @version  [版本号, 2016年8月23日]
 */
public class UpdateQyJszl extends AbstractAccountServlet
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -9129892050612241316L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        QyBaseManage manage = serviceSession.getService(QyBaseManage.class);
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        T6162 entity = new T6162();
        entity.parse(request);
        manage.updateJscl(entity);
        PrintWriter out = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        jsonMap.put("status", "success");
        out.print(objectMapper.writeValueAsString(jsonMap));
        out.close();
    }
    
    @Override
    protected void onThrowable(HttpServletRequest request, HttpServletResponse response, Throwable throwable)
        throws ServletException, IOException
    {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        if (throwable instanceof ParameterException)
        {
            PrintWriter out = response.getWriter();
            ObjectMapper objectMapper = new ObjectMapper();
            jsonMap.put("status", "error");
            jsonMap.put("msg", throwable.getMessage());
            out.print(objectMapper.writeValueAsString(jsonMap));
            out.close();
        }
        else
        {
            super.onThrowable(request, response, throwable);
        }
    }
}
