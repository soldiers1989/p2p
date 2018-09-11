package com.dimeng.p2p.pay.servlets.huifu.ret;

import com.dimeng.p2p.AbstractHuifuServlet;

public abstract class AbstractHuifuRetServlet extends AbstractHuifuServlet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    protected boolean mustAuthenticated()
    {
        return false;
    }
}
