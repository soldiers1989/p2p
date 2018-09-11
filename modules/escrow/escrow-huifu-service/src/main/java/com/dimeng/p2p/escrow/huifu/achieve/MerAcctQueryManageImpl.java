package com.dimeng.p2p.escrow.huifu.achieve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dimeng.framework.service.ServiceResource;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.query.MerAcctQueryEntity;
import com.dimeng.p2p.escrow.huifu.service.MerAcctQueryManage;

public class MerAcctQueryManageImpl extends AbstractEscrowService implements MerAcctQueryManage
{
    
    public MerAcctQueryManageImpl(ServiceResource serviceResource)
    {
        super(serviceResource);
    }
    
    @Override
    public MerAcctQueryEntity query()
        throws Throwable
    {
        String Version = HuiFuConstants.Version.VERSION_10;
        String CmdId = "QueryAccts";
        String MerCustId = merCustId;
        
        List<String> params = new ArrayList<>();
        params.add(Version);
        params.add(CmdId);
        params.add(MerCustId);
        
        String ChkValue = chkValue(params);
        
        Map<String, String> map = new HashMap<>();
        map.put("Version", Version);
        map.put("CmdId", CmdId);
        map.put("MerCustId", MerCustId);
        map.put("ChkValue", ChkValue);
        
        String result = doPost(map);
        MerAcctQueryEntity entity = jsonReflectParser(result, MerAcctQueryEntity.class);
        
        return entity;
    }
    
}
