package com.dimeng.p2p.front.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dimeng.framework.data.sql.SQLConnectionProvider;
import com.dimeng.framework.resource.ResourceNotFoundException;
import com.dimeng.framework.service.ServiceResource;
import com.dimeng.p2p.P2PConst;
import com.dimeng.p2p.service.AbstractP2PService;

public abstract class AbstractFrontService extends AbstractP2PService {

	public AbstractFrontService(ServiceResource serviceResource) {
		super(serviceResource);
	}

	protected SQLConnectionProvider getConnectionProvider()
			throws ResourceNotFoundException {
		return serviceResource.getDataConnectionProvider(
				SQLConnectionProvider.class, P2PConst.DB_MASTER_PROVIDER);
	}

	@Override
    protected Connection getConnection() throws ResourceNotFoundException,
			SQLException {
		return serviceResource.getDataConnectionProvider(
				SQLConnectionProvider.class, P2PConst.DB_MASTER_PROVIDER)
				.getConnection("S51");
	}

	protected Connection getConnection(String db)
			throws ResourceNotFoundException, SQLException {
		return serviceResource.getDataConnectionProvider(
				SQLConnectionProvider.class, P2PConst.DB_MASTER_PROVIDER)
				.getConnection(db);
	}

	@Override
	protected void writeLog(Connection connection, String type, String log) throws Throwable {
        try (PreparedStatement pstmt =
            connection.prepareStatement("INSERT INTO S61.T6190 SET F02 = ?, F03 = ?, F04 = ?, F05 = ?, F06 = ?"))
        {
            pstmt.setInt(1, serviceResource.getSession().getAccountId());
            pstmt.setTimestamp(2, getCurrentTimestamp(connection));
            pstmt.setString(3, type);
            pstmt.setString(4, log);
            pstmt.setString(5, serviceResource.getSession().getRemoteIP());
            pstmt.execute();
		}
	}
}
