/**
 * 
 */
package com.dimeng.p2p.app.servlets.pay.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zhoulantao
 *
 */
@XmlRootElement(name = "result")
public class BaofuResultParam implements Serializable
{
    private static final long serialVersionUID = 112736806743227189L;
    
    private String version;
    
    private String req_reserved;
    
    private String additional_info;
    
    private String resp_code;
    
    private String resp_msg;
    
    private String member_id;
    
    private String terminal_id;
    
    private String data_type;
    
    private String txn_type;
    
    private String txn_sub_type;
    
    private String biz_type;
    
    private String trade_date;
    
    private String trans_id;
    
    private String trans_no;
    
    private String succ_amt;
    
    public String getVersion()
    {
        return version;
    }
    
    public void setVersion(String version)
    {
        this.version = version;
    }
    
    public String getReq_reserved()
    {
        return req_reserved;
    }
    
    public void setReq_reserved(String req_reserved)
    {
        this.req_reserved = req_reserved;
    }
    
    public String getAdditional_info()
    {
        return additional_info;
    }
    
    public void setAdditional_info(String additional_info)
    {
        this.additional_info = additional_info;
    }
    
    public String getResp_code()
    {
        return resp_code;
    }
    
    public void setResp_code(String resp_code)
    {
        this.resp_code = resp_code;
    }
    
    public String getResp_msg()
    {
        return resp_msg;
    }
    
    public void setResp_msg(String resp_msg)
    {
        this.resp_msg = resp_msg;
    }
    
    public String getMember_id()
    {
        return member_id;
    }
    
    public void setMember_id(String member_id)
    {
        this.member_id = member_id;
    }
    
    public String getTerminal_id()
    {
        return terminal_id;
    }
    
    public void setTerminal_id(String terminal_id)
    {
        this.terminal_id = terminal_id;
    }
    
    public String getData_type()
    {
        return data_type;
    }
    
    public void setData_type(String data_type)
    {
        this.data_type = data_type;
    }
    
    public String getTxn_type()
    {
        return txn_type;
    }
    
    public void setTxn_type(String txn_type)
    {
        this.txn_type = txn_type;
    }
    
    public String getTxn_sub_type()
    {
        return txn_sub_type;
    }
    
    public void setTxn_sub_type(String txn_sub_type)
    {
        this.txn_sub_type = txn_sub_type;
    }
    
    public String getBiz_type()
    {
        return biz_type;
    }
    
    public void setBiz_type(String biz_type)
    {
        this.biz_type = biz_type;
    }
    
    public String getTrade_date()
    {
        return trade_date;
    }
    
    public void setTrade_date(String trade_date)
    {
        this.trade_date = trade_date;
    }
    
    public String getTrans_id()
    {
        return trans_id;
    }
    
    public void setTrans_id(String trans_id)
    {
        this.trans_id = trans_id;
    }
    
    public String getTrans_no()
    {
        return trans_no;
    }
    
    public void setTrans_no(String trans_no)
    {
        this.trans_no = trans_no;
    }
    
    public String getSucc_amt()
    {
        return succ_amt;
    }
    
    public void setSucc_amt(String succ_amt)
    {
        this.succ_amt = succ_amt;
    }

    @Override
    public String toString()
    {
        return "BaofuResultParam [version=" + version + ", req_reserved=" + req_reserved + ", additional_info="
            + additional_info + ", resp_code=" + resp_code + ", resp_msg=" + resp_msg + ", member_id=" + member_id
            + ", terminal_id=" + terminal_id + ", data_type=" + data_type + ", txn_type=" + txn_type
            + ", txn_sub_type=" + txn_sub_type + ", biz_type=" + biz_type + ", trade_date=" + trade_date
            + ", trans_id=" + trans_id + ", trans_no=" + trans_no + ", succ_amt=" + succ_amt + "]";
    }
    
}
