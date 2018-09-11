package com.dimeng.p2p.escrow.huifu.variables;

import java.io.InputStreamReader;

import com.dimeng.framework.config.VariableTypeAnnotation;
import com.dimeng.framework.config.entity.VariableBean;

@VariableTypeAnnotation(id = "ESCROW", name = "汇付资金托管")
public enum HuifuVariable implements VariableBean
{
    /**
     * 汇付商户号
     */
    HF_ACCOUNT_ID("汇付商户号")
    {
        @Override
        public String getValue()
        {
            return "530694";
        }
    },
    /**
     * 汇付客户号
     */
    HF_CUST_ID("汇付客户号")
    {
        @Override
        public String getValue()
        {
            return "6000060001676093";
        }
    },
    /**
     * 汇付商户银行卡
     */
    HF_MER_CARD("汇付商户银行卡")
    {
        @Override
        public String getValue()
        {
            return "443066340018010079182";
        }
    },
    /**
     * 汇付，提现订单冻结返回地址
     */
    HF_WITHWRAW_FREEZE(" 汇付，提现订单冻结返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/withdrawFreezeRet.htm";
        }
    },
    /**
     * 汇付提现手续费率
     */
    HF_WITHDRAW_RATE("汇付提现手续费率")
    {
        @Override
        public String getValue()
        {
            return "0.00";
        }
    },
    /**
     * 汇付收取提现手续费
     */
    HF_WITHDRAW_AMOUNT("汇付收取提现手续费")
    {
        @Override
        public String getValue()
        {
            return "2.00";
        }
    },
    /**
     * 汇付借款手续费率
     */
    HF_BORROWERRATE("汇付借款手续费率")
    {
        @Override
        public String getValue()
        {
            return "1";
        }
    },
    /**
     * 最大投资手续费率
     */
    HF_MAXTENDERRATE("最大投资手续费率")
    {
        @Override
        public String getValue()
        {
            return "0.08";
        }
    },
    /**
     * 汇付，商户公钥文件地址
     */
    HF_PUB_KEY_PATH("汇付，商户公钥文件地址 ")
    {
        @Override
        public String getValue()
        {
            return "E:\\keys\\PgPubk.key";
        }
    },
    /**
     * 汇付，商户私钥文件地址
     */
    HF_PRI_KEY_PATH("汇付，商户私钥文件地址")
    {
        @Override
        public String getValue()
        {
            return "E:\\keys\\MerPrK530694.key";
        }
    },
    /**
     * 汇付访问地址
     */
    HF_URL("汇付访问地址")
    {
        @Override
        public String getValue()
        {
            return "http://mertest.chinapnr.com/muser/publicRequests";
        }
    },
    /**
     * 汇付，用户开户返回地址
     */
    HF_REGISTER("汇付，用户开户返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/userRegisterRet.htm";
        }
    },
    /**
     * 汇付，网银充值返回地址
     */
    HF_CHARGE("汇付，网银充值返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/chargeRet.htm";
        }
    },
    /**
     * 汇付，用户绑卡返回地址
     */
    HF_BINDCARD("汇付，用户绑卡返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/bindCardRet.htm";
        }
    },
    /**
     * 汇付，担保企业开户返回地址
     */
    HF_CROPREGISTER("汇付，担保企业开户返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/corpRegisterRet.htm";
        }
    },
    /**
     * 汇付，投资返回地址
     */
    HF_BID("汇付，投资返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/bidRet.htm";
        }
    },
    /**
     * 汇付，投资前台返回地址
     */
    HF_TG_BID("汇付，投资前台返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/bidTgRet.htm";
        }
    },
    /**
     * 汇付，债权转让前台返回地址
     */
    HF_TG_EXCHAGE("汇付，债权转让前台返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/bidTgExchangeRet.htm";
        }
    },
    /**
     * 汇付，提现前台返回页面
     */
    HF_TG_WITHDRAW("汇付，提现前台返回页面")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/withdrawTgRet.htm";
        }
    },
    /**
     * 汇付，取消投资返回地址
     */
    HF_BID_CANCLE("汇付，取消投资返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/bidCancleRet.htm";
        }
    },
    /**
     * 汇付，转账返回地址
     */
    HF_TRANSFER("汇付，转账返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/transferRet.htm";
        }
    },
    /**
     * 汇付，用户账户支付返回地址
     */
    HF_ACCTPAY("汇付，用户账户支付返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/acctPayRet.htm";
        }
    },
    /**
     * 汇付，还款返回地址
     */
    HF_REPAYMENT("汇付，还款返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/tenderRepaymentRet.htm";
        }
    },
    /**
     * 汇付，提前还款返回地址
     */
    HF_PREPAYMENT("汇付，提前还款返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/tenderPrepaymentRet.htm";
        }
    },
    /**
     * 汇付，后台还款返回地址
     */
    HF_BG_REPAYMENT("汇付，后台还款返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/bidRepaymentRet.htm";
        }
    },
    /**
     * 汇付，放款返回地址
     */
    HF_LOANS("汇付，放款返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/tenderConfirmRet.htm";
        }
    },
    /**
     * 汇付，提现返回地址
     */
    HF_WITHDRAW("汇付，提现返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/withdrawRet.htm";
        }
    },
    /**
     * 汇付，取现复核返回地址
     */
    HF_CASHAUDIT("汇付，提现返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/cashAuditRet.htm";
        }
    },
    /**
     * 汇付，债权转让返回地址
     */
    HF_EXCHANGE("汇付，债权转让返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/bidExchangeRet.htm";
        }
    },
    /**
     * 汇付，垫付返回地址
     */
    HF_ADVANCE("汇付，垫付返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/advanceRet.htm";
        }
    },
    /**
     * 汇付，订单解冻返回地址
     */
    HF_FREEZE("汇付，订单解冻返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/freezeRet.htm";
        }
    },
    /**
     * 汇付，订单解冻返回地址
     */
    HF_UNFREEZE("汇付，订单解冻返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/unFreezeRet.htm";
        }
    },
    /**
     * 汇付，流标资金解冻返回地址
     */
    HF_BID_UNFREEZE("汇付，流标资金解冻返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/bidUnFreezeRet.htm";
        }
    },
    /**
     * 汇付，撤销自动投资返回地址
     */
    HF_RETURL_CANCLEAUTOBID("汇付，撤销自动投资返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/cancleAutoBidRet.htm";
        }
    },
    /**
     * 汇付，自动投标计划回调地址
     */
    HF_RETURL_AUTOBID("汇付，自动投标计划回调地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/autoBidPlanRet.htm";
        }
    },
    /**
     * 汇付，标的信息录入
     */
    HF_ADD_BID("汇付，标的信息录入返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/addBidInfoRet.htm";
        }
    },
    /**
     * 汇付，线下充值审核通过后台返回地址
     */
    HF_OFFLINE_CHARGE_RET("汇付，线下充值审核通过后台返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/offlineChargeOrdRet.htm";
        }
    },
    /**
     * 汇付，公益标捐款后台回调地址
     */
    HF_DONATION_RET("汇付，公益标捐款后台回调地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/donationRet.htm";
        }
    },
    /**
     * 汇付，公益标捐款失败,资金返回捐款用户回调地址
     */
    HF_DONATION_FAIL_RET("汇付，公益标捐款失败,资金返回捐款用户回调地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/donationFailRet.htm";
        }
    },
    /**
     * 汇付，公益标捐款前台回调地址
     */
    HF_DONATION_TG_RET("汇付，公益标捐款前台回调地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/donationTgRet.htm";
        }
    },
    /**
     * 汇付，不良债权购买回调地址
     */
    HF_BADCLAIM_ADVANCE("汇付，不良债权购买回调地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/badClaimAdvanceRet.htm";
        }
    },
    /**
     * 汇付，公益标捐款前台回调地址
     */
    HF_BUY_TG_RET("汇付，商品购买前台回调地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/buyTgRet.htm";
        }
    },
    /**
     * 汇付，商品购买后台回调地址
     */
    HF_BUY_RET("汇付，商品购买后台回调地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/buyRet.htm";
        }
    },
    /**
     * 汇付，商品购买失败，资金退回后台回调地址
     */
    HF_BUY_FAIL_RET("汇付，商品购买失败，资金退回后台回调地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/buyFailRet.htm";
        }
    },
    /**
     * 汇付，注册是否回调手机号
     */
    HF_RETURN_PHONE("汇付，注册是否回调手机号")
    {
        @Override
        public String getValue()
        {
            return "true";
        }
    },
    /**
     * 汇付，添加银行卡返回地址
     */
    HF_RETURL_BINDCRAD("汇付，添加银行卡返回地址")
    {
        @Override
        public String getValue()
        {
            return "${SYSTEM.SITE_REQUEST_PROTOCOL}${SYSTEM.SITE_DOMAIN}/user/index.htm";
        }
    },
    /**
     * 汇付，体验金还款返回地址
     */
    HF_BID_EXPERIENCE_REPAYMENT("汇付，体验金还款返回地址")
    {
        @Override
        public String getValue()
        {
            return "http://${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/bidExperienceRepaymentRet.htm";
        }
    },
    /**
     * 汇付，加息券还款返回地址
     */
    HF_BID_ACTIVITY_REPAYMENT("汇付，加息券还款返回地址")
    {
        @Override
        public String getValue()
        {
            return "http://${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/bidActivityRepaymentRet.htm";
        }
    },
    /**
     * 汇付，商城退款后台回调地址
     */
    HF_MALL_REFUND_RET("汇付，商城退款后台回调地址")
    {
        @Override
        public String getValue()
        {
            return "http://${SYSTEM.SITE_DOMAIN}/pay/huifu/ret/mallRefundRet.htm";
        }
    },
    /**
     * 汇付，注册是否回调邮箱
     */
    HF_RETURN_EMAIL("汇付，注册是否回调邮箱")
    {
        @Override
        public String getValue()
        {
            return "true";
        }
    },
    /**
     * 用户自动投标:权限设置(P-部分授权，W-完全授权)
     */
    ANTO_BIDING_PLAN_TYPE("用户自动投标:权限设置(P-部分授权，W-完全授权)")
    {
        @Override
        public String getValue()
        {
            return "P";
        }
    },
    /**
     * 用户提现手续费收取对象：M-向商户收取，U-向用户收取
     */
    HF_FEE_OBJ_FLAG("用户提现手续费收取对象：(M-向商户收取，U-向用户收取)")
    {
        @Override
        public String getValue()
        {
            return "U";
        }
    };
    
    protected final String key;
    
    protected final String description;
    
    HuifuVariable(String description)
    {
        StringBuilder builder = new StringBuilder(getType());
        builder.append('.').append(name());
        key = builder.toString();
        this.description = description;
    }
    
    @Override
    public String getType()
    {
        return HuifuVariable.class.getAnnotation(VariableTypeAnnotation.class).id();
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
            new InputStreamReader(HuifuVariable.class.getResourceAsStream(getKey()), "UTF-8"))
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
        return true;
    }
}