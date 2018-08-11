package org.shenjia.sqlaudit;

import java.util.logging.Logger;

public class DefaultSqlAuditService implements
        SqlAuditService {

    private static final Logger LOG = Logger.getLogger(DefaultSqlAuditService.class.getName());

    @Override
    public void saveAuditObject(AuditObject auditObject) {
        LOG.info(auditObject.toString());
    }

}
