package com.dimeng.p2p.escrow.huifu.achieve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;

import com.dimeng.framework.service.ServiceFactory;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.exception.LogicalException;
import com.dimeng.framework.service.exception.ParameterException;
import com.dimeng.p2p.S50.entities.T5020;
import com.dimeng.p2p.S50.enums.T5020_F03;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.entities.T6114;
import com.dimeng.p2p.S61.entities.T6141;
import com.dimeng.p2p.S61.enums.T6110_F06;
import com.dimeng.p2p.S61.enums.T6114_F08;
import com.dimeng.p2p.S61.enums.T6114_F10;
import com.dimeng.p2p.S61.enums.T6141_F03;
import com.dimeng.p2p.S61.enums.T6141_F04;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.BgBindCardCond;
import com.dimeng.p2p.escrow.huifu.entity.bankcard.BgBindCardEntity;
import com.dimeng.p2p.escrow.huifu.entity.bankcard.BindCardEntity;
import com.dimeng.p2p.escrow.huifu.entity.bankcard.UnbindCardEntity;
import com.dimeng.p2p.escrow.huifu.entity.charge.ChargeEntity;
import com.dimeng.p2p.escrow.huifu.service.BankCardManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.util.StringHelper;

public class BankCardManageImpl extends AbstractEscrowService implements BankCardManage
{
    
    public BankCardManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    public static class BankCardManageFactory implements ServiceFactory<BankCardManage>
    {
        
        @Override
        public BankCardManage newInstance(ServiceResource serviceResource)
        {
            return new BankCardManageImpl(serviceResource);
        }
        
    }
    
    @Override
    public String createBindCardUrI(String usrCustId)
        throws Throwable
    {
        if (StringHelper.isEmpty(usrCustId))
        {
            return null;
        }
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "UserBindCard";
        String MerCustId = merCustId;
        String UsrCustId = usrCustId;
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_BINDCARD);
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrCustId);
        params.add(BgRetUrl);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        
        return concatUrl(map);
    }
    
    @Override
    public BindCardEntity bindCardReturnDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        BindCardEntity entity = new BindCardEntity();
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        entity.cmdId = request.getParameter("CmdId");
        entity.merCustId = request.getParameter("MerCustId");
        entity.merPriv = urlDecoder(request.getParameter("MerPriv"));
        entity.openAcctId = request.getParameter("OpenAcctId");
        entity.openBankId = request.getParameter("OpenBankId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.trxId = request.getParameter("TrxId");
        entity.usrCustId = request.getParameter("UsrCustId");
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.openAcctId);
        params.add(entity.openBankId);
        params.add(entity.usrCustId);
        params.add(entity.trxId);
        params.add(entity.bgRetUrl);
        params.add(entity.merPriv);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public BgBindCardEntity sendBgBindCard(BgBindCardCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "BgBindCard";
        String MerCustId = merCustId;
        String UsrCustId = cond.usrCustId();
        String OpenAcctId = cond.openAcctId();
        String OpenBankId = cond.openBankId();
        String OpenProvId = cond.openProvId();
        String OpenAreaId = cond.openAreaId();
        String OpenBranchName = cond.openBranchName();
        String IsDefault = cond.isDefault();
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrCustId);
        params.add(OpenAcctId);
        params.add(OpenBankId);
        params.add(OpenProvId);
        params.add(OpenAreaId);
        params.add(OpenBranchName);
        params.add(IsDefault);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        map.put("OpenAcctId", OpenAcctId);
        map.put("OpenBankId", OpenBankId);
        map.put("OpenProvId", OpenProvId);
        map.put("OpenAreaId", OpenAreaId);
        map.put("OpenBranchName", OpenBranchName);
        map.put("IsDefault", IsDefault);
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);
        if (result == null)
        {
            return null;
        }
        Map<String, String> jsonMap = jsonParse(result);
        
        BgBindCardEntity entity = new BgBindCardEntity();
        entity.chkValue = jsonMap.get("ChkValue");
        entity.cmdId = jsonMap.get("CmdId");
        entity.merCustId = jsonMap.get("MerCustId");
        entity.merPriv = jsonMap.get("MerPriv");
        entity.openAcctId = jsonMap.get("OpenAcctId");
        entity.openBankId = jsonMap.get("OpenBankId");
        entity.respCode = jsonMap.get("RespCode");
        entity.respDesc = jsonMap.get("RespDesc");
        entity.usrCustId = jsonMap.get("UsrCustId");
        
        List<String> list = new ArrayList<>();
        list.add(entity.cmdId);
        list.add(entity.respCode);
        list.add(entity.merCustId);
        list.add(entity.usrCustId);
        list.add(entity.openAcctId);
        list.add(entity.openBankId);
        list.add(entity.merPriv);
        
        String str = forEncryptionStr(params);
        return verifyByRSA(str, entity.chkValue) && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode) ? entity
            : null;
    }
    
    @Override
    public UnbindCardEntity sendUnbindCard(String usrCustId, String cardId)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "DelCard";
        String MerCustId = merCustId;
        String UsrCustId = usrCustId;
        String CardId = cardId;
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrCustId);
        params.add(CardId);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        map.put("CardId", CardId);
        map.put("ChkValue", ChkValue);
        
        logger.info("汇付 - 删除银行卡请求参数：" + map);
        
        String result = doPost(map);
        
        logger.info("汇付 - 删除银行卡回调参数：" + result);
        
        if (result == null)
        {
            logger.info("汇付 - 删除银行卡回调参数为空");
            return null;
        }
        Map<String, String> jsonMap = jsonParse(result);
        
        UnbindCardEntity entity = new UnbindCardEntity();
        entity.cardId = jsonMap.get("CardId");
        entity.chkValue = jsonMap.get("ChkValue");
        entity.cmdId = jsonMap.get("CmdId");
        entity.merCustId = jsonMap.get("MerCustId");
        entity.respCode = jsonMap.get("RespCode");
        entity.respDesc = jsonMap.get("RespDesc");
        entity.usrCustId = jsonMap.get("UsrCustId");
        
        List<String> list = new ArrayList<>();
        list.add(entity.cmdId);
        list.add(entity.respCode);
        list.add(entity.merCustId);
        list.add(entity.usrCustId);
        list.add(entity.cardId);
        
        String str = forEncryptionStr(list);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public void insertT6114(BindCardEntity entity)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            T6110 t6110 = selectT6110(connection, getAccountId(entity.usrCustId));
            T6141 t6141 = selectT6141(connection, t6110.F01);
            
            T6114 t6114 = new T6114();
            t6114.F02 = t6110.F01;
            t6114.F03 = selectT5020(connection, entity.openBankId).F01;
            t6114.F04 = 0;
            t6114.F05 = bankMap.get(entity.openBankId);
            t6114.F06 = rule(entity.openAcctId);
            t6114.F07 = StringHelper.encode(entity.openAcctId);
            t6114.F08 = T6114_F08.QY;
            t6114.F10 = T6114_F10.TG;
            t6114.F11 = t6141.F02;
            t6114.F12 = T6110_F06.ZRR == t6110.F06 ? 1 : 2;
            
            int id = 0;
            try (PreparedStatement ps =
                connection.prepareStatement("SELECT F01 FROM S61.T6114 WHERE F02 = ? AND F07 = ?"))
            {
                ps.setInt(1, t6114.F02);
                ps.setString(2, t6114.F07);
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        id = resultSet.getInt(1);
                    }
                }
            }
            if (id > 0)
            {
                try (PreparedStatement ps = connection.prepareStatement("UPDATE S61.T6114 SET F08 = ? WHERE F01 = ?"))
                {
                    ps.setString(1, T6114_F08.QY.name());
                    ps.setInt(2, id);
                    ps.executeUpdate();
                }
                return;
            }
            insertT6114(connection, t6114);
        }
    }
    
    protected T5020 selectT5020(Connection connection, String F04)
        throws SQLException
    {
        T5020 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04 FROM S50.T5020 WHERE T5020.F04 = ? LIMIT 1"))
        {
            pstmt.setString(1, F04);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T5020();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getString(2);
                    record.F03 = T5020_F03.parse(resultSet.getString(3));
                    record.F04 = resultSet.getString(4);
                }
            }
        }
        return record;
    }
    
    private String rule(String bandCard)
    {
        if (StringHelper.isEmpty(bandCard))
        {
            return null;
        }
        char[] chars = bandCard.toCharArray();
        for (int i = 0; i < chars.length; i++)
        {
            if (i > 4 && i < chars.length - 4)
            {
                chars[i] = '*';
            }
        }
        return String.valueOf(chars);
    }
    
    private int insertT6114(Connection connection, T6114 entity)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S61.T6114 SET F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?, F07 = ?, F08 = ?, F09 = ?, F10 = ?, F11 = ?, F12 = ?",
                PreparedStatement.RETURN_GENERATED_KEYS))
        {
            pstmt.setInt(1, entity.F02);
            pstmt.setInt(2, entity.F03);
            pstmt.setInt(3, entity.F04);
            pstmt.setString(4, entity.F05);
            pstmt.setString(5, entity.F06);
            pstmt.setString(6, entity.F07);
            pstmt.setString(7, entity.F08.name());
            pstmt.setTimestamp(8, getCurrentTimestamp(connection));
            pstmt.setString(9, entity.F10.name());
            pstmt.setString(10, entity.F11);
            pstmt.setInt(11, entity.F12);
            pstmt.execute();
            try (ResultSet resultSet = pstmt.getGeneratedKeys();)
            {
                if (resultSet.next())
                {
                    return resultSet.getInt(1);
                }
                return 0;
            }
        }
    }
    
    @Override
    public String getUserCustId()
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F03 FROM S61.T6119 WHERE T6119.F01 = ?"))
            {
                ps.setInt(1, serviceResource.getSession().getAccountId());
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
    
    @Override
    public String getBankCard(int id)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F07 FROM S61.T6114 WHERE T6114.F01 = ? AND T6114.F08= ? LIMIT 1"))
            {
                pstmt.setInt(1, id);
                pstmt.setString(2, T6114_F08.QY.name());
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        return StringHelper.decode(resultSet.getString(1));
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public void unbindCard(UnbindCardEntity entity)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            int accoutId = getAccountId(entity.usrCustId);
//            updateT6114(connection, T6114_F08.TY, accoutId, entity.cardId);
            deleteT6114(connection, accoutId, entity.cardId);
        }
    }
    
    private void updateT6114(Connection connection, T6114_F08 F01, int F02, String F03)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S61.T6114 SET F08 = ? WHERE F02 = ? AND F07 = ?"))
        {
            pstmt.setString(1, F01.name());
            pstmt.setInt(2, F02);
            pstmt.setString(3, StringHelper.encode(F03));
            pstmt.execute();
        }
    }
    
    private void deleteT6114(Connection connection, int F02, String F03)
            throws Throwable
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("DELETE FROM S61.T6114 WHERE F02 = ? AND F07 = ?"))
            {
            	String cId = StringHelper.encode(F03);
                pstmt.setInt(1, F02);
                pstmt.setString(2, cId);
                pstmt.execute();
            }
        }
    
    @Override
    public int getBankCardCount()
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps =
                connection.prepareStatement("SELECT COUNT(*) FROM S61.T6114 WHERE T6114.F02 = ? AND T6114.F08 = ?"))
            {
                ps.setInt(1, serviceResource.getSession().getAccountId());
                ps.setString(2, T6114_F08.QY.name());
                try (ResultSet resultSet = ps.executeQuery())
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
    
    @Override
    public T6114 getPtBankCard()
        throws Throwable
    {
        int accountId = 0;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement("SELECT F01 FROM S71.T7101 LIMIT 1"))
            {
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        accountId = resultSet.getInt(1);
                    }
                }
            }
            if (accountId <= 0)
            {
                throw new LogicalException("平台账户不存在");
            }
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10 FROM S61.T6114 WHERE T6114.F02 = ? LIMIT 1"))
            {
                pstmt.setInt(1, accountId);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        T6114 record = new T6114();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getInt(3);
                        record.F04 = resultSet.getInt(4);
                        record.F05 = resultSet.getString(5);
                        record.F06 = resultSet.getString(6);
                        record.F07 = resultSet.getString(7);
                        record.F08 = T6114_F08.parse(resultSet.getString(8));
                        record.F09 = resultSet.getTimestamp(9);
                        record.F10 = T6114_F10.parse(resultSet.getString(10));
                        return record;
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public T5020[] getBankList()
        throws Throwable
    {
        ArrayList<T5020> list = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt = connection.prepareStatement("SELECT F01, F02, F03, F04 FROM S50.T5020"))
            {
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    while (resultSet.next())
                    {
                        T5020 record = new T5020();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getString(2);
                        record.F03 = T5020_F03.parse(resultSet.getString(3));
                        record.F04 = resultSet.getString(4);
                        if (list == null)
                        {
                            list = new ArrayList<>();
                        }
                        list.add(record);
                    }
                }
            }
        }
        return ((list == null || list.size() == 0) ? null : list.toArray(new T5020[list.size()]));
    }
    
    @Override
    public void addPtCard(int bankId, String bankCard)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            int accountId = 0;
            try (PreparedStatement ps = connection.prepareStatement("SELECT F01 FROM S71.T7101 LIMIT 1"))
            {
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        accountId = resultSet.getInt(1);
                    }
                }
            }
            String bank = "";
            try (PreparedStatement ps = connection.prepareStatement("SELECT F02 FROM S50.T5020 WHERE F01 = ?"))
            {
                ps.setInt(1, bankId);
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        bank = resultSet.getString(1);
                    }
                }
            }
            T6114 t6114 = new T6114();
            t6114.F02 = accountId;
            t6114.F03 = bankId;
            t6114.F04 = 0;
            t6114.F05 = bank;
            t6114.F06 = rule(bankCard);
            t6114.F07 = StringHelper.encode(bankCard);
            t6114.F08 = T6114_F08.QY;
            t6114.F10 = T6114_F10.TG;
            insertT6114(connection, t6114);
        }
    }
    
    @Override
    public void updatePtCard(int bankId, String bankCard, int cardId)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            String bank = "";
            try (PreparedStatement ps = connection.prepareStatement("SELECT F02 FROM S50.T5020 WHERE F01 = ?"))
            {
                ps.setInt(1, bankId);
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        bank = resultSet.getString(1);
                    }
                }
            }
            try (PreparedStatement ps =
                connection.prepareStatement("UPDATE S61.T6114 SET F03 = ?, F05 = ?, F06 = ?, F07 = ? WHERE F01 = ?"))
            {
                ps.setInt(1, bankId);
                ps.setString(2, bank);
                ps.setString(3, rule(bankCard));
                ps.setString(4, StringHelper.encode(bankCard));
                ps.setInt(5, cardId);
                ps.executeUpdate();
            }
        }
    }
    
    @Override
    public void addCard(String name, int bankId, String bankCard)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            
            try
            {
                serviceResource.openTransactions(connection);
                int accountId = 0;
                try (PreparedStatement ps = connection.prepareStatement("SELECT F01 FROM S61.T6110 WHERE F02 = ?"))
                {
                    ps.setString(1, name);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            accountId = resultSet.getInt(1);
                        }
                    }
                }
                if (accountId <= 0)
                {
                    throw new ParameterException("用户不存在");
                }
                int cardId = 0;
                try (PreparedStatement ps =
                    connection.prepareStatement("SELECT F01 FROM S61.T6114 WHERE F01 = ? AND F07 = ?"))
                {
                    ps.setInt(1, accountId);
                    ps.setString(2, StringHelper.encode(bankCard));
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            cardId = resultSet.getInt(1);
                        }
                    }
                }
                if (cardId > 0)
                {
                    try (PreparedStatement ps =
                        connection.prepareStatement("UPDATE S61.T6114 SET F08 = ? WHERE F01 = ?"))
                    {
                        ps.setString(1, T6114_F08.QY.name());
                        ps.setInt(2, cardId);
                        ps.executeUpdate();
                    }
                    return;
                }
                String bank = "";
                try (PreparedStatement ps = connection.prepareStatement("SELECT F02 FROM S50.T5020 WHERE F01 = ?"))
                {
                    ps.setInt(1, bankId);
                    try (ResultSet resultSet = ps.executeQuery())
                    {
                        if (resultSet.next())
                        {
                            bank = resultSet.getString(1);
                        }
                    }
                }
                T6114 t6114 = new T6114();
                t6114.F02 = accountId;
                t6114.F03 = bankId;
                t6114.F04 = 0;
                t6114.F05 = bank;
                t6114.F06 = rule(bankCard);
                t6114.F07 = StringHelper.encode(bankCard);
                t6114.F08 = T6114_F08.QY;
                t6114.F10 = T6114_F10.TG;
                insertT6114(connection, t6114);
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    //增加快捷支付的卡信息
    @Override
    public void addQuickPayCardId(ChargeEntity chargeEntity)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            try
            {
                serviceResource.openTransactions(connection);
                BindCardEntity bindCardEntity = new BindCardEntity();
                bindCardEntity.usrCustId = chargeEntity.usrCustId;
                bindCardEntity.openBankId = chargeEntity.gateBankId;
                bindCardEntity.openAcctId = chargeEntity.cardId;
                
                //如果用户在绑定快捷卡前有绑定其他提现卡，则将之前绑定的卡停用。
                int userId = getAccountId(bindCardEntity.usrCustId);
                List<T6114> t6114s = selectT6114s(connection, userId);
                if (t6114s.size() >= 1)
                {
                    PreparedStatement ps = connection.prepareStatement("UPDATE S61.T6114 SET F08 = ? WHERE F01 = ?");
                    for (T6114 t6114 : t6114s)
                    {
                        ps.setString(1, T6114_F08.TY.name());
                        ps.setInt(2, t6114.F01);
                        ps.executeUpdate();
                    }
                }
                //新增银行卡
                insertT6114(bindCardEntity);
                
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
        catch (Exception e)
        {
            logger.error(e, e);
            throw e;
        }
    }
    
    //更新快捷卡为停用
    @Override
    public void updateQuickCard(UnbindCardEntity entity)
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            int userId = getAccountId(entity.usrCustId);
            List<T6114> t6114s = selectT6114s(connection, userId);
            if (t6114s.size() >= 1)
            {
                PreparedStatement ps = connection.prepareStatement("UPDATE S61.T6114 SET F08 = ? WHERE F01 = ?");
                for (T6114 t6114 : t6114s)
                {
                    ps.setString(1, T6114_F08.TY.name());
                    ps.setInt(2, t6114.F01);
                    ps.executeUpdate();
                }
            }
        }
        
    }
    
    //查询用户绑定的银行卡
    public List<T6114> selectT6114s(Connection connection, int F02)
        throws SQLException
    {
        T6114 record = null;
        List<T6114> list = new ArrayList<T6114>();
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10 FROM S61.T6114 WHERE T6114.F02 = ? AND F08 = ?"))
        {
            pstmt.setInt(1, F02);
            pstmt.setString(2, T6114_F08.QY.name());
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                while (resultSet.next())
                {
                    record = new T6114();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getInt(2);
                    record.F03 = resultSet.getInt(3);
                    record.F04 = resultSet.getInt(4);
                    record.F05 = resultSet.getString(5);
                    record.F06 = resultSet.getString(6);
                    record.F07 = resultSet.getString(7);
                    record.F08 = T6114_F08.parse(resultSet.getString(8));
                    record.F09 = resultSet.getTimestamp(9);
                    record.F10 = T6114_F10.parse(resultSet.getString(10));
                    list.add(record);
                }
            }
        }
        return list;
    }
    
    @Override
    public UnbindCardEntity unbindQuickCardRetDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        UnbindCardEntity entity = new UnbindCardEntity();
        entity.cmdId = request.getParameter("CmdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.merCustId = request.getParameter("MerCustId");
        entity.usrCustId = request.getParameter("CustId");
        entity.trxId = request.getParameter("TrxId");
        entity.bankId = request.getParameter("BankId");
        entity.cardId = request.getParameter("CardId");
        entity.expressFlag = request.getParameter("ExpressFlag");
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.usrCustId);
        params.add(entity.trxId);
        params.add(entity.bankId);
        params.add(entity.cardId);
        params.add(entity.expressFlag);
        params.add(entity.bgRetUrl);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(DigestUtils.md5Hex(str), entity.chkValue) ? entity : null;
    }
    
    /**
     * 查询用户基本信息
     * <功能详细描述>
     * @param connection
     * @return
     * @throws SQLException
     */
    private T6141 selectT6141(Connection connection, int userId)
        throws SQLException
    {
        T6141 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08 FROM S61.T6141 WHERE T6141.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, userId);
            try (ResultSet resultSet = pstmt.executeQuery())
            {
                if (resultSet.next())
                {
                    record = new T6141();
                    record.F01 = resultSet.getInt(1);
                    record.F02 = resultSet.getString(2);
                    record.F03 = T6141_F03.parse(resultSet.getString(3));
                    record.F04 = T6141_F04.parse(resultSet.getString(4));
                    record.F05 = resultSet.getString(5);
                    record.F06 = resultSet.getString(6);
                    record.F07 = resultSet.getString(7);
                    record.F08 = resultSet.getDate(8);
                }
            }
        }
        return record;
    }
    
}
