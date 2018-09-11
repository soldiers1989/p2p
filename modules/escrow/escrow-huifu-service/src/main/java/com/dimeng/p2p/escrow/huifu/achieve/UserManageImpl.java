package com.dimeng.p2p.escrow.huifu.achieve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.framework.service.query.ArrayParser;
import com.dimeng.framework.service.query.Paging;
import com.dimeng.framework.service.query.PagingResult;
import com.dimeng.p2p.PaymentInstitution;
import com.dimeng.p2p.S50.entities.T5020;
import com.dimeng.p2p.S50.enums.T5020_F03;
import com.dimeng.p2p.S61.entities.T6110;
import com.dimeng.p2p.S61.entities.T6114;
import com.dimeng.p2p.S61.entities.T6119;
import com.dimeng.p2p.S61.entities.T6141;
import com.dimeng.p2p.S61.entities.T6161;
import com.dimeng.p2p.S61.enums.T6110_F06;
import com.dimeng.p2p.S61.enums.T6110_F07;
import com.dimeng.p2p.S61.enums.T6110_F08;
import com.dimeng.p2p.S61.enums.T6110_F10;
import com.dimeng.p2p.S61.enums.T6114_F08;
import com.dimeng.p2p.S61.enums.T6114_F10;
import com.dimeng.p2p.S61.enums.T6118_F02;
import com.dimeng.p2p.S61.enums.T6118_F03;
import com.dimeng.p2p.S61.enums.T6118_F04;
import com.dimeng.p2p.S61.enums.T6141_F03;
import com.dimeng.p2p.S61.enums.T6141_F04;
import com.dimeng.p2p.S61.enums.T6141_F09;
import com.dimeng.p2p.S61.enums.T6165_F02;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.cond.BgUserRegisterCond;
import com.dimeng.p2p.escrow.huifu.cond.CorpRegisterCond;
import com.dimeng.p2p.escrow.huifu.cond.UserRegisterCond;
import com.dimeng.p2p.escrow.huifu.entity.query.CorpQueryEntity;
import com.dimeng.p2p.escrow.huifu.entity.register.CorpAudit;
import com.dimeng.p2p.escrow.huifu.entity.register.CorpRegisterEntity;
import com.dimeng.p2p.escrow.huifu.entity.register.OpenEscrowGuideEntity;
import com.dimeng.p2p.escrow.huifu.entity.register.UserRegisterEntity;
import com.dimeng.p2p.escrow.huifu.service.UserManage;
import com.dimeng.p2p.escrow.huifu.variables.HuifuVariable;
import com.dimeng.p2p.variables.defines.URLVariable;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.BooleanParser;
import com.dimeng.util.parser.EnumParser;
import com.dimeng.util.parser.IntegerParser;
import com.dimeng.util.parser.TimestampParser;

/**
 * 
 * 用户管理 <功能详细描述>
 * 
 * @author lingyuanjie
 * @version [版本号, 2016年7月21日]
 */
public class UserManageImpl extends AbstractEscrowService implements UserManage
{
    
    public UserManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    @Override
    public String createRegisterUri(UserRegisterCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "UserRegister";
        String MerCustId = merCustId;
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_REGISTER);
        String RetUrl = configureProvider.format(URLVariable.USER_INDEX).concat("index.html");
        String UsrId = cond.usrId();
        String UsrName = cond.usrName();
        String IdType = idType;
        String IdNo = cond.idNo();
        String UsrMp = cond.usrMp();
        String UsrEmail = cond.usrEmail();
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(BgRetUrl);
        params.add(RetUrl);
        params.add(UsrId);
        params.add(UsrName);
        params.add(IdType);
        params.add(IdNo);
        params.add(UsrMp);
        params.add(UsrEmail);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("BgRetUrl", BgRetUrl);
        map.put("RetUrl", RetUrl);
        map.put("UsrId", UsrId);
        map.put("UsrName", urlEncode(UsrName));
        map.put("IdType", IdType);
        map.put("IdNo", IdNo);
        map.put("UsrMp", UsrMp);
        map.put("UsrEmail", UsrEmail);
        map.put("ChkValue", ChkValue);
        
        return concatUrl(map);
    }
    
    @Override
    public UserRegisterEntity registerReturnDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        UserRegisterEntity entity = new UserRegisterEntity();
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        entity.cmdId = request.getParameter("CmdId");
        entity.idNo = request.getParameter("IdNo");
        entity.idType = request.getParameter("IdType");
        entity.merCustId = request.getParameter("MerCustId");
        entity.merPriv = urlDecoder(request.getParameter("Merpriv"));
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.retUrl = urlDecoder(request.getParameter("RetUrl"));
        entity.trxId = request.getParameter("TrxId");
        entity.usrCustId = request.getParameter("UsrCustId");
        entity.usrEmail = request.getParameter("UsrEmail");
        entity.usrId = request.getParameter("UsrId");
        entity.usrMp = request.getParameter("UsrMp");
        entity.usrName = urlDecoder(request.getParameter("UsrName"));
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.usrId);
        params.add(entity.usrCustId);
        params.add(entity.bgRetUrl);
        params.add(entity.trxId);
        params.add(entity.retUrl);
        params.add(entity.merPriv);
        
        String str = forEncryptionStr(params);
        
        return verifyByRSA(str, entity.chkValue) ? entity : null;
    }
    
    @Override
    public String sendBgUserRegister(BgUserRegisterCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "BgRegister";
        String MerCustId = merCustId;
        String UsrId = cond.usrId();
        String UsrName = cond.usrName();
        String LoginPwd = cond.loginPwd();
        String TransPwd = cond.transPwd();
        String IdType = idType;
        String IdNo = cond.idNo();
        String UsrMp = cond.usrMp();
        String UsrEmail = cond.usrEmail();
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrId);
        params.add(UsrName);
        params.add(LoginPwd);
        params.add(TransPwd);
        params.add(IdType);
        params.add(IdNo);
        params.add(UsrMp);
        params.add(UsrEmail);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrId", UsrId);
        map.put("UsrName", UsrName);
        map.put("LoginPwd", LoginPwd);
        map.put("TransPwd", TransPwd);
        map.put("IdType", IdType);
        map.put("IdNo", IdNo);
        map.put("UsrMp", UsrMp);
        map.put("UsrEmail", UsrEmail);
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);
        
        return result;
    }
    
    @Override
    public String createLoginUri(String usrCustId)
        throws Throwable
    {
        if (StringHelper.isEmpty(usrCustId))
        {
            return null;
        }
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "UserLogin";
        String MerCustId = merCustId;
        String UsrCustId = usrCustId;
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        
        return concatUrl(map);
    }
    
    @Override
    public String createEditUri(String usrCustId)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "AcctModify";
        String MerCustId = merCustId;
        String UsrCustId = usrCustId;
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrCustId);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrCustId", UsrCustId);
        map.put("ChkValue", ChkValue);
        
        return concatUrl(map);
    }
    
    @Override
    public String createCorpRegisterUri(CorpRegisterCond cond)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "CorpRegister";
        String MerCustId = merCustId;
        String UsrId = cond.usrId();
        String UsrName = cond.usrName();
        String InstuCode = cond.instuCode();
        String BusiCode = cond.busiCode();
        String TaxCode = cond.taxCode();
        String GuarType = cond.GuarType();
        String BgRetUrl = configureProvider.format(HuifuVariable.HF_CROPREGISTER);
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(UsrId);
        params.add(UsrName);
        params.add(InstuCode);
        params.add(BusiCode);
        params.add(TaxCode);
        params.add(GuarType);
        params.add(BgRetUrl);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("UsrId", UsrId);
        // map.put("UsrName", UsrName);
        map.put("InstuCode", InstuCode);
        map.put("BusiCode", BusiCode);
        map.put("TaxCode", TaxCode);
        map.put("GuarType", GuarType);
        map.put("BgRetUrl", BgRetUrl);
        map.put("ChkValue", ChkValue);
        
        return concatUrl(map);
    }
    
    @Override
    public CorpRegisterEntity corpRegisterDecoder(HttpServletRequest request)
        throws Throwable
    {
        parameters(request);
        CorpRegisterEntity entity = new CorpRegisterEntity();
        entity.cmdId = request.getParameter("CmdId");
        entity.respCode = request.getParameter("RespCode");
        entity.respDesc = urlDecoder(request.getParameter("RespDesc"));
        entity.merCustId = request.getParameter("MerCustId");
        entity.usrId = request.getParameter("UsrId");
        entity.usrName = urlDecoder(request.getParameter("UsrName"));
        entity.usrCustId = request.getParameter("UsrCustId");
        entity.auditStat = request.getParameter("AuditStat");
        entity.auditDesc = urlDecoder(request.getParameter("AuditDesc"));
        entity.merPriv = urlDecoder(request.getParameter("MerPriv"));
        entity.trxId = request.getParameter("TrxId");
        entity.openBankId = request.getParameter("OpenBankId");
        entity.cardId = request.getParameter("CardId");
        entity.bgRetUrl = urlDecoder(request.getParameter("BgRetUrl"));
        entity.chkValue = request.getParameter("ChkValue");
        
        List<String> params = new ArrayList<>();
        params.add(entity.cmdId);
        params.add(entity.respCode);
        params.add(entity.merCustId);
        params.add(entity.usrId);
        params.add(entity.usrName);
        params.add(entity.usrCustId);
        params.add(entity.auditStat);
        params.add(entity.trxId);
        params.add(entity.openBankId);
        params.add(entity.cardId);
        params.add(entity.bgRetUrl);
        
        //String str = forEncryptionStr(params);
        //return verifyByRSA(str, entity.chkValue) ? entity : null;
        return entity;
    }
    
    @Override
    public void updateUserInfo(UserRegisterEntity entity)
        throws Throwable
    {
        if (entity == null)
        {
            return;
        }
        try (Connection connection = getConnection())
        {
            if (StringHelper.isEmpty(entity.usrId))
            {
                return;
            }
            String[] usrIds = entity.usrId.split("_");
            if (usrIds == null || usrIds.length != 2)
            {
                return;
            }
            int userId = IntegerParser.parse(usrIds[1].substring(5));
            ConfigureProvider configureProvider = serviceResource.getResource(ConfigureProvider.class);
            boolean retPhone = BooleanParser.parse(configureProvider.getProperty(HuifuVariable.HF_RETURN_PHONE));
            boolean retEmail = BooleanParser.parse(configureProvider.getProperty(HuifuVariable.HF_RETURN_EMAIL));
            T6118_F03 t6118_F03 = T6118_F03.BTG;
            T6118_F04 t6118_F04 = T6118_F04.BTG;
            try (PreparedStatement ps = connection.prepareStatement("SELECT F03, F04 FROM S61.T6118 WHERE F01 = ?"))
            {
                ps.setInt(1, userId);
                try (ResultSet resultSet = ps.executeQuery())
                {
                    if (resultSet.next())
                    {
                        t6118_F03 = EnumParser.parse(T6118_F03.class, resultSet.getString(1));
                        t6118_F04 = EnumParser.parse(T6118_F04.class, resultSet.getString(2));
                    }
                }
            }
            
            try
            {
                serviceResource.openTransactions(connection);
                if (retPhone)
                {
                    // 用户账号表
                    updatePhone(connection, entity.usrMp, userId);
                    t6118_F03 = T6118_F03.TG;
                }
                if (retEmail && !StringHelper.isEmpty(entity.usrEmail))
                {
                    // 用户账号表
                    updateEmail(connection, urlDecoder(entity.usrEmail), userId);
                    t6118_F04 = T6118_F04.TG;
                }
                // 个人基础信息
                updateT6141(connection, entity.usrName, T6141_F04.TG.name(), entity.idNo, entity.idNo, userId);
                // 用户安全认证
                updateT6118(connection, T6118_F02.TG, t6118_F03, t6118_F04, userId, entity.usrMp);
                // 用户第三方托管账号
                insertT6119(connection, userId, PaymentInstitution.CHINAPNR.getInstitutionCode(), entity.usrCustId);
                
                serviceResource.commit(connection);
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
        }
    }
    
    protected void updateT6118(Connection connection, T6118_F02 F01, T6118_F03 F02, T6118_F04 F03, int F04,
        String mobile)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S61.T6118 SET F02 = ?, F03 = ?, F04 = ?, F06 = ? WHERE F01 = ?"))
        {
            pstmt.setString(1, F01.name());
            pstmt.setString(2, F02.name());
            pstmt.setString(3, F03.name());
            pstmt.setString(4, mobile);
            pstmt.setInt(5, F04);
            pstmt.execute();
        }
    }
    
    /**
     * 插入第三方托管账号
     * 
     * @param connection
     * @param F01
     * @param F02
     * @param F03
     * @throws SQLException
     */
    protected void insertT6119(Connection connection, int F01, int F02, String F03)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S61.T6119 SET F01 = ?, F02 = ?, F03 = ?"))
        {
            pstmt.setInt(1, F01);
            pstmt.setInt(2, F02);
            pstmt.setString(3, F03);
            pstmt.execute();
        }
    }
    
    /**
     * 更新用户手机
     * 
     * @param connection
     * @param F01
     * @param F02
     * @param F03
     * @throws SQLException
     */
    protected void updatePhone(Connection connection, String phone, int id)
        throws SQLException
    {
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE S61.T6110 SET F04 = ? WHERE F01 = ?"))
        {
            pstmt.setString(1, phone);
            pstmt.setInt(2, id);
            pstmt.execute();
        }
    }
    
    /**
     * 更新用户邮箱
     * 
     * @param connection
     * @param F01
     * @param F02
     * @param F03
     * @throws SQLException
     */
    protected void updateEmail(Connection connection, String email, int id)
        throws SQLException
    {
        try (PreparedStatement pstmt = connection.prepareStatement("UPDATE S61.T6110 SET F05 = ? WHERE F01 = ?"))
        {
            pstmt.setString(1, email);
            pstmt.setInt(2, id);
            pstmt.execute();
        }
    }
    
    /**
     * 更新身份证、真实姓名
     * 
     * @param connection
     * @param F01
     * @param F02
     * @param F03
     * @throws Throwable
     */
    protected void updateT6141(Connection connection, String F01, String F02, String F03, String F04, int F05)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("UPDATE S61.T6141 SET F02 = ?, F04 = ?, F06 = ?, F07 = ? ,F08 = ?, F09 = ? WHERE F01 = ?"))
        {
            pstmt.setString(1, F01);
            pstmt.setString(2, F02);
            pstmt.setString(3, StringHelper.truncation(F03, 3, "***************"));
            pstmt.setString(4, StringHelper.encode(F04));
            Timestamp date = getBirthday(F03);
            pstmt.setTimestamp(5, date);
            pstmt.setString(6, getSexNotDecode(F03));
            pstmt.setInt(7, F05);
            pstmt.execute();
        }
    }
    
    /**
     * 根据身份证号码获取性别
     * <功能详细描述>
     * @param sfzh
     * @return
     */
    private String getSexNotDecode(String sfzh)
    {
        if (StringHelper.isEmpty(sfzh))
        {
            return null;
        }
        String sex = null;
        try
        {
            if (Integer.parseInt(sfzh.substring(sfzh.length() - 2, sfzh.length() - 1)) % 2 != 0)
            {
                sex = T6141_F09.M.name();
            }
            else
            {
                sex = T6141_F09.F.name();
            }
        }
        catch (Throwable throwable)
        {
            logger.error("UserManageImpl.getSexNotDecode error", throwable);
        }
        return sex;
    }
    
    /**
     * 根据身份证得到出生年月
     * 
     * @param cardID
     * @return
     */
    private static Timestamp getBirthday(String cardID)
    {
        StringBuffer tempStr = new StringBuffer("");
        if (cardID != null && cardID.trim().length() > 0)
        {
            if (cardID.trim().length() == 15)
            {
                tempStr.append(cardID.substring(6, 12));
                tempStr.insert(4, '-');
                tempStr.insert(2, '-');
                tempStr.insert(0, "19");
            }
            else if (cardID.trim().length() == 18)
            {
                tempStr = new StringBuffer(cardID.substring(6, 14));
                tempStr.insert(6, '-');
                tempStr.insert(4, '-');
            }
        }
        return TimestampParser.parse(tempStr.toString());
    }
    
    @Override
    public T6110 selectT6110()
        throws SQLException
    {
        T6110 record = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10 FROM S61.T6110 WHERE T6110.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, serviceResource.getSession().getAccountId());
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6110();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getString(2);
                        record.F03 = resultSet.getString(3);
                        record.F04 = resultSet.getString(4);
                        record.F05 = resultSet.getString(5);
                        record.F06 = T6110_F06.parse(resultSet.getString(6));
                        record.F07 = T6110_F07.parse(resultSet.getString(7));
                        record.F08 = T6110_F08.parse(resultSet.getString(8));
                        record.F09 = resultSet.getTimestamp(9);
                        record.F10 = T6110_F10.parse(resultSet.getString(10));
                    }
                }
            }
        }
        return record;
    }
    
    @Override
    public T6119 selectT6119()
        throws Throwable
    {
        T6119 record = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03 FROM S61.T6119 WHERE T6119.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, serviceResource.getSession().getAccountId());
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6119();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getInt(2);
                        record.F03 = resultSet.getString(3);
                    }
                }
            }
        }
        return record;
    }
    
    @Override
    public void updateCropInfo(CorpRegisterEntity entity)
        throws Throwable
    {
        if (entity == null)
        {
            logger.info("===================返回参数解析错误");
            return;
        }
        logger.info("===================进入注册");
        try (Connection connection = getConnection())
        {
            if (StringHelper.isEmpty(entity.usrId))
            {
                logger.info("===================用户id为空");
                return;
            }
            String[] usrIds = entity.usrId.split("_");
            if (usrIds == null || usrIds.length != 2)
            {
                logger.info("===================拆分用户名出错，或用户名不能拆分");
                return;
            }
            int userId = IntegerParser.parse(usrIds[1].substring(5));
            
            try
            {
                serviceResource.openTransactions(connection);
                insertT6165(connection, userId, EnumParser.parse(T6165_F02.class, entity.auditStat), entity.auditDesc);
                // 更新企业名称信息
                execute(connection, "UPDATE S61.T6161 SET F04 = ? WHERE F01 = ?", entity.usrName, userId);
                
                if (!"Y".equals(entity.auditStat))
                {
                    logger.info("===================企业开户 审核状态不是开户成功,return！");
                    return;
                }
                insertT6119(connection, userId, PaymentInstitution.CHINAPNR.getInstitutionCode(), entity.usrCustId);
                T6114 t6114 = new T6114();
                t6114.F02 = getAccountId(entity.usrCustId);
                t6114.F03 = selectT5020(connection, entity.openBankId).F01;
                t6114.F04 = 0;
                t6114.F05 = bankMap.get(entity.openBankId);
                t6114.F06 = rule(entity.cardId);
                t6114.F07 = StringHelper.encode(entity.cardId);
                t6114.F08 = T6114_F08.QY;
                t6114.F10 = T6114_F10.TG;
                t6114.F11 = entity.usrName;
                insertT6114(connection, t6114);
                logger.info("===================插入企业用户完毕");
            }
            catch (Exception e)
            {
                serviceResource.rollback(connection);
                throw e;
            }
            finally
            {
                serviceResource.commit(connection);
            }
        }
    }
    
    private void insertT6165(Connection connection, int F01, T6165_F02 F02, String F03)
        throws SQLException
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S61.T6165 SET F01 = ?, F02 = ?, F03 = ? ON DUPLICATE KEY UPDATE F01 = VALUES(F01), F02 = VALUES(F02), F03 = VALUES(F03)"))
        {
            pstmt.setInt(1, F01);
            pstmt.setString(2, F02.name());
            pstmt.setString(3, F03);
            pstmt.execute();
        }
    }
    
    private int insertT6114(Connection connection, T6114 entity)
        throws Throwable
    {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S61.T6114 SET F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?, F07 = ?, F08 = ?, F09 = ?, F10 = ?,F11 = ?, F12 = 2",
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
    
    @Override
    public T6161 selectT6161()
        throws Throwable
    {
        T6161 record = null;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08, F09, F10, F11, F12, F13, F14, F15, F16, F17, F18, F19, F20, F21 FROM S61.T6161 WHERE T6161.F01 = ? LIMIT 1"))
            {
                pstmt.setInt(1, serviceResource.getSession().getAccountId());
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        record = new T6161();
                        record.F01 = resultSet.getInt(1);
                        record.F02 = resultSet.getString(2);
                        record.F03 = resultSet.getString(3);
                        record.F04 = resultSet.getString(4);
                        record.F05 = resultSet.getString(5);
                        record.F06 = resultSet.getString(6);
                        record.F07 = resultSet.getInt(7);
                        record.F08 = resultSet.getBigDecimal(8);
                        record.F09 = resultSet.getString(9);
                        record.F10 = resultSet.getInt(10);
                        record.F11 = resultSet.getString(11);
                        record.F12 = resultSet.getString(12);
                        record.F13 = resultSet.getString(13);
                        record.F14 = resultSet.getBigDecimal(14);
                        record.F15 = resultSet.getBigDecimal(15);
                        record.F16 = resultSet.getString(16);
                        record.F17 = resultSet.getString(17);
                        record.F18 = resultSet.getString(18);
                        record.F19 = resultSet.getString(19);
                        record.F20 = resultSet.getString(20);
                        record.F21 = resultSet.getString(21);
                    }
                }
            }
        }
        return record;
    }
    
    @Override
    public CorpQueryEntity cropInfo(String busiCode)
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "CorpRegisterQuery";
        String MerCustId = merCustId;
        String BusiCode = busiCode;
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        params.add(BusiCode);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("BusiCode", BusiCode);
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);
        
        CorpQueryEntity entity = jsonReflectParser(result, CorpQueryEntity.class);
        entity.RespDesc = urlDecoder(entity.RespDesc);
        return entity;
    }
    
    @Override
    public PagingResult<CorpAudit> auditStat(String account, Paging paging)
        throws Throwable
    {
        StringBuilder builder =
            new StringBuilder(
                "SELECT T6110.F02 AS F01, T6110.F06 AS F02, T6165.F02 AS F03, T6165.F03 AS F04, T6165.F04 AS F05 FROM S61.T6165 INNER JOIN S61.T6110 WHERE T6165.F01 = T6110.F01 AND T6110.F06 = ?");
        List<Object> params = new ArrayList<>();
        params.add(T6110_F06.FZRR.name());
        if (!StringHelper.isEmpty(account))
        {
            builder.append(" AND T6110.F02 LIKE ?");
            params.add("%" + account + "%");
        }
        try (Connection connection = getConnection())
        {
            return selectPaging(connection, new ArrayParser<CorpAudit>()
            {
                
                @Override
                public CorpAudit[] parse(ResultSet resultSet)
                    throws SQLException
                {
                    List<CorpAudit> corps = null;
                    while (resultSet.next())
                    {
                        if (corps == null)
                        {
                            corps = new ArrayList<>();
                        }
                        CorpAudit corp = new CorpAudit();
                        corp.account = resultSet.getString(1);
                        corp.type = EnumParser.parse(T6110_F06.class, resultSet.getString(2));
                        corp.status = EnumParser.parse(T6165_F02.class, resultSet.getString(3));
                        corp.desc = resultSet.getString(4);
                        corp.time = resultSet.getTimestamp(5);
                        corps.add(corp);
                    }
                    return corps == null ? null : corps.toArray(new CorpAudit[corps.size()]);
                }
            }, paging, builder.toString(), params);
        }
    }
    
    @Override
    public OpenEscrowGuideEntity getOpenEscrowGuideEntity()
        throws Throwable
    {
        try (Connection connection = getConnection())
        {
            OpenEscrowGuideEntity entity = new OpenEscrowGuideEntity();
            T6110 t6110 = selectT6110();
            if (t6110 == null)
                return null;
            if (T6110_F06.ZRR.equals(t6110.F06))
            {
                entity.setRealNameLabel("真实姓名");
                entity.setIdentificationNoLabel("身份证号");
                T6141 t6141 = selectT6141(connection);
                entity.setRealName(t6141.F02 == null ? "" : t6141.F02);
                entity.setIdentificationNo(t6141.F07 == null ? "" : StringHelper.decode(t6141.F07));
                entity.setUserTag("ZRR");
                entity.setMaxLength("18");
            }
            else
            {
                String prefix = "";
                if (T6110_F10.S.equals(t6110.F10))
                {
                    prefix = "机构";
                    entity.setUserTag("JG");
                }
                else
                {
                    prefix = "企业";
                    entity.setUserTag("QY");
                }
                entity.setRealNameLabel(prefix + "名称");
                T6161 t6161 = selectT6161();
                entity.setRealName(t6161.F04 == null ? "" : t6161.F04);
                if ("Y".equals(t6161.F20))
                {
                    entity.setIdentificationNoLabel(prefix + "社会信用代码");
                    entity.setIdentificationNo(t6161.F19 == null ? "" : t6161.F19);
                    entity.setMaxLength("18");// 社信代码只有18位
                }
                else
                {
                    entity.setIdentificationNoLabel(prefix + "营业执照号码");
                    entity.setIdentificationNo(t6161.F03 == null ? "" : t6161.F03);
                    entity.setMaxLength("20");
                }
            }
            entity.setMobile(t6110.F04 == null ? "" : t6110.F04);
            return entity;
        }
    }
    
    /**
     * 查询个人基础信息 <功能详细描述>
     * 
     * @param connection
     * @return
     * @throws SQLException
     */
    private T6141 selectT6141(Connection connection)
        throws SQLException
    {
        T6141 record = null;
        try (PreparedStatement pstmt =
            connection.prepareStatement("SELECT F01, F02, F03, F04, F05, F06, F07, F08 FROM S61.T6141 WHERE T6141.F01 = ? LIMIT 1"))
        {
            pstmt.setInt(1, serviceResource.getSession().getAccountId());
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
