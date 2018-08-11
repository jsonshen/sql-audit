package org.shenjia.sqlaudit;

import java.sql.Connection;
import java.util.ServiceLoader;
import java.util.logging.Logger;

public final class SqlAuditor {

    private static final Logger LOG = Logger.getLogger(SqlAuditor.class.getName());
    private static final ThreadLocal<String> ACCESS_ID = new ThreadLocal<>();
    private static SqlAuditService sqlAuditService;

    public static void setAccessId(String accessId) {
        ACCESS_ID.set(accessId);
    }

    public static String getAccessId() {
        return ACCESS_ID.get();
    }

    public static void removeAccessId() {
        ACCESS_ID.remove();
    }
    
    public static Connection proxyConnection(Connection proxy) {
        return new ConnectionProxy(proxy);
    }
    
    static void setParameter(AuditObject auditObject, int index, Object value) {
        if (null != auditObject) {
            auditObject.setParameter(index, value);
        }
    }

    static void saveAuditObject(AuditObject auditObject) {
        if (null == sqlAuditService) {
            ServiceLoader<SqlAuditService> loader = ServiceLoader.load(SqlAuditService.class);
            loader.forEach((service) -> {
                sqlAuditService = service;
            });
            if (null == sqlAuditService) {
                LOG.severe("Please set the META-INF/services/" + SqlAuditService.class.getName() + " file.");
                return;
            }
        }
        sqlAuditService.saveAuditObject(auditObject);
    }
}
