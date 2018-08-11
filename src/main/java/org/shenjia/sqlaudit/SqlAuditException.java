package org.shenjia.sqlaudit;

public class SqlAuditException extends RuntimeException {

    private static final long serialVersionUID = -634246966427584575L;

    public SqlAuditException(String message) {
        super(message);
    }

    public SqlAuditException(String message,
            Throwable cause) {
        super(message, cause);
    }

}
