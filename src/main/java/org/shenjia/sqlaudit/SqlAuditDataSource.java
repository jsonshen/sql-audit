package org.shenjia.sqlaudit;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class SqlAuditDataSource implements
        DataSource {
    
    private DataSource proxy;
    
    public SqlAuditDataSource(DataSource proxy) {
        this.proxy = proxy;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return proxy.getLogWriter();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        proxy.setLogWriter(out);
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        proxy.setLoginTimeout(seconds);
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return proxy.getLoginTimeout();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return proxy.getParentLogger();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return proxy.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return proxy.isWrapperFor(iface);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new ConnectionProxy(proxy.getConnection());
    }

    @Override
    public Connection getConnection(String username,
        String password) throws SQLException {
        return new ConnectionProxy(proxy.getConnection(username, password));
    }

}
