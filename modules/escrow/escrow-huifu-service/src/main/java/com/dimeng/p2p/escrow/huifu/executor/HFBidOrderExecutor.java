/*package com.dimeng.p2p.escrow.huifu.executor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Map;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.config.Envionment;
import com.dimeng.framework.data.sql.SQLConnection;
import com.dimeng.framework.resource.AchieveVersion;
import com.dimeng.framework.resource.Resource;
import com.dimeng.framework.resource.ResourceAnnotation;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.p2p.FeeCode;
import com.dimeng.p2p.S61.entities.T6101;
import com.dimeng.p2p.S61.entities.T6102;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.enums.T6101_F03;
import com.dimeng.p2p.S61.enums.T6106_F05;
import com.dimeng.p2p.S62.entities.T6230;
import com.dimeng.p2p.S62.entities.T6231;
import com.dimeng.p2p.S62.entities.T6238;
import com.dimeng.p2p.S62.entities.T6250;
import com.dimeng.p2p.S62.entities.T6251;
import com.dimeng.p2p.S62.enums.T6230_F15;
import com.dimeng.p2p.S62.enums.T6230_F20;
import com.dimeng.p2p.S62.enums.T6230_F28;
import com.dimeng.p2p.S62.enums.T6250_F07;
import com.dimeng.p2p.S62.enums.T6250_F09;
import com.dimeng.p2p.S62.enums.T6250_F11;
import com.dimeng.p2p.S62.enums.T6251_F08;
import com.dimeng.p2p.S63.entities.T6342;
import com.dimeng.p2p.S63.entities.T6344;
import com.dimeng.p2p.S63.enums.T6342_F04;
import com.dimeng.p2p.S65.entities.T6504;
import com.dimeng.p2p.S65.entities.T6527;
import com.dimeng.p2p.common.SMSUtils;
import com.dimeng.p2p.order.TenderOrderExecutor;
import com.dimeng.p2p.service.ActivityCommon;
import com.dimeng.p2p.variables.defines.LetterVariable;
import com.dimeng.p2p.variables.defines.MallVariavle;
import com.dimeng.p2p.variables.defines.MsgVariavle;
import com.dimeng.p2p.variables.defines.SystemVariable;
import com.dimeng.p2p.variables.defines.smses.SmsVaribles;
import com.dimeng.util.DateHelper;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BooleanParser;
import com.dimeng.util.parser.DateTimeParser;
import com.dimeng.util.parser.IntegerParser;

*//**
* 
* 投标执行器
* <功能详细描述>
* 
* @author  lingyuanjie
* @version  [版本号, 2016年8月8日]
*/
/*
@AchieveVersion(version = 201401022)
@ResourceAnnotation
public class HFBidOrderExecutor extends TenderOrderExecutor
{
 
 public HFBidOrderExecutor(ResourceProvider resourceProvider)
 {
     super(resourceProvider);
 }
 
 @Override
 public Class<? extends Resource> getIdentifiedType()
 {
     return HFBidOrderExecutor.class;
 }
 
 @Override
 protected void doConfirm(SQLConnection connection, int orderId, Map<String, String> params)
     throws Throwable
 {
     try
     {
         // 订单详情
         T6504 t6504 = selectT6504(connection, orderId);
         if (t6504 == null)
         {
             throw new LogicalException("订单明细记录不存在");
         }
         // 标的详情,锁定标
         T6230 t6230 = selectT6230(connection, t6504.F03);
         T6231 t6231 = selectT6231(connection, t6504.F03);
         if (t6230 == null || t6231 == null)
         {
             throw new LogicalException("标记录不存在");
         }
         if (T6230_F28.S.equals(t6230.xsb) && (getXsbCount(t6504.F02) > 0 || getZqzrCount(t6504.F02) > 0))
         {
             throw new LogicalException("感谢您的支持！<br/>此标为新手标，只有未成功投资过并且没有购买过债权的新用户才可以投资。");
         }
         ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
         boolean ajkt = BooleanParser.parse(configureProvider.getProperty(SystemVariable.BID_SFZJKT));
         if (!ajkt && t6504.F02 == t6230.F02)
         {
             throw new LogicalException("不可投本账号发的标");
         }
         //获取标的担保方ID（购买人不能购买自己担保的标）
         int assureId = selectT6236(connection, t6504.F03);
         if (t6504.F02 == assureId)
         {
             throw new LogicalException("不可投资自己担保的标");
         }
         if (t6230.F20 != T6230_F20.TBZ)
         {
             throw new LogicalException("不是投资中状态,不能投资");
         }
         if (t6504.F04.compareTo(t6230.F07) > 0)
         {
             if (params != null)
             {
                 resourceProvider.log(String.format("%s %s投资异常：投资金额大于可投金额，投资订单%s，冻结订单%s，冻结流水%s",
                     DateTimeParser.format(new java.util.Date()),
                     t6230.F01,
                     orderId,
                     params.get("freezeOrdId"),
                     params.get("freezeTrxId")));
                 
             }
             throw new LogicalException("投资金额大于可投金额");
         }
         // 校验最低起投金额
         // BigDecimal min =
         // BigDecimalParser.parse(configureProvider.getProperty(SystemVariable.MIN_BIDING_AMOUNT));
         BigDecimal min = t6231.F25;
         if (t6504.F04.compareTo(min) < 0)
         {
             throw new LogicalException("投资金额不能低于最低起投金额");
         }
         BigDecimal max = t6231.F26;
         if (t6504.F04.compareTo(max) > 0)
         {
             throw new LogicalException("投资金额不能大于最大投资金额");
         }
         t6230.F07 = t6230.F07.subtract(t6504.F04);
         if (t6230.F07.compareTo(BigDecimal.ZERO) > 0 && t6230.F07.compareTo(min) < 0)
         {
             throw new LogicalException("剩余可投金额不能低于最低起投金额");
         }
         int pid = getPTID(connection); // 平台用户id
         if (pid <= 0)
         {
             throw new LogicalException("平台账号不存在");
         }
         // 平台往来账户信息
         T6101 ptwlzh = selectT6101(connection, pid, T6101_F03.WLZH, true);
         if (ptwlzh == null)
         {
             throw new LogicalException("平台往来账户不存在");
         }
         if (params != null)
         {
             String trxId = params.get("trxId");
             int freezeOrdId = IntegerParser.parse(params.get("freezeOrdId"));
             String freezeTrxId = params.get("freezeTrxId");
             // 更新流水号
             updateT6501(connection, trxId, t6504.F01);
             if (freezeOrdId > 0)
             {
                 updateT6501(connection, freezeTrxId, freezeOrdId);
             }
         }
         // 扣减可投金额
         try (PreparedStatement pstmt = connection.prepareStatement("UPDATE S62.T6230 SET F07 = ? WHERE F01 = ?"))
         {
             pstmt.setBigDecimal(1, t6230.F07);
             pstmt.setInt(2, t6504.F03);
             pstmt.execute();
         }
         // 锁定投资人资金账户
         T6101 czzh = selectT6101(connection, t6504.F02, T6101_F03.WLZH, true);
         if (czzh == null)
         {
             throw new LogicalException("投资人往来账户不存在");
         }
         // 锁定入账账户
         T6101 rzzh = null;
         String msg = null;
         int feeCode = 0;
         if (t6230.F15 == T6230_F15.S)
         {
             rzzh = selectT6101(connection, t6230.F02, T6101_F03.WLZH, true);
             msg = "借款人账户不存在";
             feeCode = FeeCode.JK;
         }
         else
         {
             rzzh = selectT6101(connection, t6504.F02, T6101_F03.SDZH, true);
             msg = "投资人锁定账户不存在";
             feeCode = FeeCode.TZ;
         }
         if (rzzh == null)
         {
             throw new LogicalException(msg);
         }
         BigDecimal sjAmount = t6504.F04;
         String userReward = "";
         String myRewardType = "";
         T6342 t6342 = null;
         T6344 t6344 = null;
         T6527 t6527 = null;
         if (params != null)
         {
             // 用户红包
             userReward = params.get("userReward");
             myRewardType = params.get("myRewardType");
             
             if (!StringHelper.isEmpty(userReward) && "hb".equalsIgnoreCase(myRewardType))
             {
                 // 查询红包订单详情
                 t6527 = selectT6527(connection, orderId);
                 if (t6527 != null && t6527.F01 > 0)
                 {
                     String hbRule = params.get("hbRule");
                     t6342 = selectT6342(connection, IntegerParser.parse(hbRule), T6342_F04.WSY.name());
                     if (t6342 != null)
                     {
                         t6344 = selectT6344(connection, t6342.F03);
                     }
                     if (t6342 == null || t6344 == null)
                     {
                         throw new LogicalException("用户红包不存在");
                     }
                     
//                        sjAmount = sjAmount.subtract(t6344.F05);
                     sjAmount = sjAmount.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO : sjAmount;
                 }
             }
         }
         {
             // 扣减出账账户金额
             czzh.F06 = czzh.F06.subtract(sjAmount);
             if (czzh.F06.compareTo(BigDecimal.ZERO) < 0)
             {
                 throw new LogicalException("账户金额不足");
             }
             updateT6101(connection, czzh.F06, czzh.F01);
             // 资金流水
             T6102 t6102 = new T6102();
             t6102.F02 = czzh.F01;
             t6102.F03 = FeeCode.TZ;
             t6102.F04 = rzzh.F01;
             t6102.F07 = sjAmount;
             t6102.F08 = czzh.F06;
             t6102.F09 = String.format("散标投资:%s，标题：%s", t6230.F25, t6230.F03);
             insertT6102(connection, t6102);
         }
         {
             // 增加入账账户金额
             rzzh.F06 = rzzh.F06.add(sjAmount);
             updateT6101(connection, rzzh.F06, rzzh.F01);
             T6102 t6102 = new T6102();
             t6102.F02 = rzzh.F01;
             t6102.F03 = feeCode;
             t6102.F04 = czzh.F01;
             t6102.F06 = sjAmount;
             t6102.F08 = rzzh.F06;
             t6102.F09 = String.format("散标投资:%s，标题：%s", t6230.F25, t6230.F03);
             insertT6102(connection, t6102);
         }
         
         // 插入投资记录
         T6250 t6250 = new T6250();
         t6250.F02 = t6230.F01;
         t6250.F03 = t6504.F02;
         t6250.F04 = t6504.F04;
         t6250.F01 = t6504.F05;
         t6250.F09 = t6504.F06 == null ? T6250_F09.F : T6250_F09.parse(t6504.F06.name());
         t6250.F11 = T6250_F11.parse(t6504.F07.name());
         // 判断计息金额与标总金额是否一致
         if (t6230.F05.compareTo(t6230.F26) == 0)
         {
             t6250.F05 = t6504.F04;
         }
         else
         {
             t6250.F05 = t6230.F26.multiply(t6504.F04).divide(t6230.F05, DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
         }
         t6250.F07 = T6250_F07.F;
         int rid = insertT6250(connection, t6250);
         try (PreparedStatement ps = connection.prepareStatement("UPDATE S65.T6504 SET F05 = ? WHERE F01 = ?"))
         {
             ps.setInt(1, rid);
             ps.setInt(2, t6504.F01);
             ps.executeUpdate();
         }
         Timestamp now = getCurrentTimestamp(connection);
         if (t6230.F15 == T6230_F15.S)
         {
             // 自动放款
             Date currentDate = getCurrentDate(connection);
             T6251 t6251 = new T6251();
             {
                 // 插入债权
                 t6251.F02 = zqCode(rid);
                 t6251.F03 = t6230.F01;
                 t6251.F04 = t6504.F02;
                 t6251.F05 = t6250.F04;
                 t6251.F06 = t6250.F04;
                 t6251.F07 = t6250.F04;
                 t6251.F08 = T6251_F08.F;
                 t6251.F09 = currentDate;
                 t6251.F10 = new Date(currentDate.getTime() + t6230.F19 * DateHelper.DAY_IN_MILLISECONDS);
                 t6251.F11 = rid;
                 t6251.F01 = insertT6251(connection, t6251);
             }
             // 生成还款计划
             hkjh(connection, t6230, t6251);
             // 收成交服务费
             T6238 t6238 = selectT6238(connection, t6230.F01);
             if (t6238 != null && t6238.F02.compareTo(BigDecimal.ZERO) > 0)
             {
                 BigDecimal fwf = t6238.F02.multiply(t6504.F04);
                 // updateT6101(connection, wlzh.F06, wlzh.F01);
                 {
                     // 平台资金增加
                     ptwlzh.F06 = ptwlzh.F06.add(fwf);
                     updateT6101(connection, ptwlzh.F06, ptwlzh.F01);
                     T6102 t6102 = new T6102();
                     t6102.F02 = ptwlzh.F01;
                     t6102.F03 = FeeCode.CJFWF;
                     t6102.F04 = rzzh.F01;
                     t6102.F06 = fwf;
                     t6102.F08 = ptwlzh.F06;
                     t6102.F09 = String.format("散标成交服务费:%s", t6230.F25);
                     insertT6102(connection, t6102);
                 }
                 {
                     // 借款人账户减少
                     rzzh.F06 = rzzh.F06.subtract(fwf);
                     updateT6101(connection, rzzh.F06, rzzh.F01);
                     T6102 t6102 = new T6102();
                     t6102.F02 = rzzh.F01;
                     t6102.F03 = FeeCode.CJFWF;
                     t6102.F04 = ptwlzh.F01;
                     t6102.F07 = fwf;
                     t6102.F08 = rzzh.F06;
                     t6102.F09 = String.format("散标成交服务费:%s", t6230.F25);
                     insertT6102(connection, t6102);
                 }
             }
             try (PreparedStatement pstmt =
                 connection.prepareStatement("UPDATE S62.T6231 SET F12 = ? WHERE F01 = ?"))
             {
                 pstmt.setTimestamp(1, now);
                 pstmt.setInt(2, t6230.F01);
                 pstmt.execute();
             }
             if (t6230.F07.compareTo(BigDecimal.ZERO) <= 0)
             {
                 // 满标
                 try (PreparedStatement pstmt =
                     connection.prepareStatement("UPDATE S62.T6231 SET F11 = ?,F29 = 'F',F30=null WHERE F01 = ?"))
                 {
                     pstmt.setTimestamp(1, now);
                     pstmt.setInt(2, t6230.F01);
                     pstmt.execute();
                 }
                 try (PreparedStatement pstmt =
                     connection.prepareStatement("UPDATE S62.T6230 SET F20 = ? WHERE F01 = ?"))
                 {
                     pstmt.setString(1, T6230_F20.HKZ.name());
                     pstmt.setInt(2, t6230.F01);
                     pstmt.execute();
                 }
                 //筹款成功(满标)，发站内信
                 sendLetterForLoanFilled(configureProvider, connection, t6230);
             }
         }
         else
         {
             if (t6230.F07.compareTo(BigDecimal.ZERO) <= 0)
             {
                 // 满标
                 try (PreparedStatement pstmt =
                     connection.prepareStatement("UPDATE S62.T6231 SET F11 = ?,F29 = 'F',F30=null WHERE F01 = ?"))
                 {
                     pstmt.setTimestamp(1, now);
                     pstmt.setInt(2, t6230.F01);
                     pstmt.execute();
                 }
                 try (PreparedStatement pstmt =
                     connection.prepareStatement("UPDATE S62.T6230 SET F20 = ? WHERE F01 = ?"))
                 {
                     pstmt.setString(1, T6230_F20.DFK.name());
                     pstmt.setInt(2, t6230.F01);
                     pstmt.execute();
                 }
                 //筹款成功(满标)，发站内信
                 sendLetterForLoanFilled(configureProvider, connection, t6230);
             }
         }
         // 发站内信
         T6110 t6110 = selectT6110(connection, t6504.F02);
         Envionment envionment = configureProvider.createEnvionment();
         envionment.set("title", t6230.F03);
         envionment.set("money", t6504.F04.toString());
         String content = configureProvider.format(LetterVariable.TZR_TBCG, envionment);
         sendLetter(connection, t6504.F02, "投资成功", content);
         
         String isUseYtx = configureProvider.getProperty(SmsVaribles.SMS_IS_USE_YTX);
         if ("1".equals(isUseYtx))
         {
             SMSUtils smsUtils = new SMSUtils(configureProvider);
             int type = smsUtils.getTempleId(MsgVariavle.TZR_TBCG.getDescription());
             sendMsg(connection, t6110.F04, t6230.F03, type);
         }
         else
         {
             String msgContent = configureProvider.format(MsgVariavle.TZR_TBCG, envionment);
             sendMsg(connection, t6110.F04, msgContent);
         }
         
         // 活动处理
         if (!StringHelper.isEmpty(userReward) && !StringHelper.isEmpty(myRewardType))
         {
             String result = existActInvest(connection, t6230.F01, t6504.F02);
             if (!StringHelper.isEmpty(result))
             {
                 throw new LogicalException("该标已使用过"
                     + ("experience".equals(result) ? "体验金" : "hb".equals(result) ? "红包" : "加息券") + "投资");
             }
             switch (myRewardType)
             {
             // 插入体验金订单
                 case "experience":
                     tyjDoConfirm(connection, configureProvider, orderId, rid, t6230);
                     break;
                 // 插入红包订单
                 case "hb":
                     hbDoConfirm(connection, t6230, t6342, t6344, t6527, rid);
                     break;
                 // 插入加息券订单
                 case "jxq":
                     jxqDoConfirm(connection, configureProvider, orderId, rid, t6230, params, t6504);
                     break;
                 default:
                     break;
             }
             
         }
         
         try (ServiceSession serviceSession =
             resourceProvider.getResource(ServiceProvider.class).createServiceSession())
         {
             // 送红包和加息券
             ActivityCommon activityCommon = serviceSession.getService(ActivityCommon.class);
             activityCommon.sendRedAndRest(sjAmount, t6504.F02, connection);
         }
         boolean is_mall = Boolean.parseBoolean(configureProvider.getProperty(MallVariavle.IS_MALL));
         if (is_mall)
         {
             //投资赠送积分
             giveScore(connection, t6504.F02, t6504.F04, T6106_F05.invest, now, null);
             //邀请有效投资用户赠送积分
             giveScore(connection, t6504.F02, t6504.F04, T6106_F05.invite, now, configureProvider);
         }
         
         // 托管接口调用
         callFace(connection, orderId, params);
     }
     catch (RuntimeException e)
     {
         // 托管接口调用
         rollbackFace(connection, orderId, params);
         logger.error(e, e);
         throw e;
     }
     catch (Exception e)
     {
         // 托管接口调用
         rollbackFace(connection, orderId, params);
         logger.error(e, e);
         throw e;
     }
 }
 
}*/