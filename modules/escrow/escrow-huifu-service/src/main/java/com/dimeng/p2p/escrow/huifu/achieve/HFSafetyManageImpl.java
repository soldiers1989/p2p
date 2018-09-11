package com.dimeng.p2p.escrow.huifu.achieve;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.dimeng.framework.service.ServiceResource;
import com.dimeng.p2p.account.user.service.achieve.SafetyManageImpl;
import com.dimeng.p2p.escrow.huifu.service.HFSafetyManage;

/**
 * 
 *  托管引导页实名认证管理 
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月19日]
 */
public class HFSafetyManageImpl extends SafetyManageImpl implements HFSafetyManage
{
    
    public HFSafetyManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    @Override
    public boolean checkPhoneIsExist(String phone)
        throws Throwable
    {
        boolean isExist = false;
        try (Connection connection = getConnection())
        {
            try (PreparedStatement pstmt =
                connection.prepareStatement("SELECT F01 FROM S61.T6110 WHERE T6110.F04 = ? LIMIT 1"))
            {
                pstmt.setString(1, phone);
                try (ResultSet resultSet = pstmt.executeQuery())
                {
                    if (resultSet.next())
                    {
                        if (resultSet.getInt(1) != serviceResource.getSession().getAccountId())
                        {
                            isExist = true;
                        }
                    }
                }
            }
        }
        return isExist;
    }
    
}
