package org.shenjia.sqlaudit;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

class ConnectionProxy implements
        Connection {

    private Connection proxy;

    ConnectionProxy(Connection proxy) {
        this.proxy = proxy;
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
    public Statement createStatement() throws SQLException {
        return new StatementProxy(proxy.createStatement(), getDbType());
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return new PreparedStatementProxy<PreparedStatement>(proxy.prepareStatement(sql), createAuditObject(sql),
                getDbType());
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException {
        return new CallableStatementProxy(proxy.prepareCall(sql), createAuditObject(sql));
    }

    @Override
    public String nativeSQL(String sql) throws SQLException {
        return proxy.nativeSQL(sql);
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException {
        proxy.setAutoCommit(autoCommit);
    }

    @Override
    public boolean getAutoCommit() throws SQLException {
        return proxy.getAutoCommit();
    }

    @Override
    public void commit() throws SQLException {
        proxy.commit();
    }

    @Override
    public void rollback() throws SQLException {
        proxy.rollback();
    }

    @Override
    public void close() throws SQLException {
        proxy.close();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return proxy.isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException {
        return proxy.getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException {
        proxy.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException {
        return proxy.isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException {
        proxy.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException {
        return proxy.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException {
        proxy.setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException {
        return proxy.getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        return proxy.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException {
        proxy.clearWarnings();
    }

    @Override
    public Statement createStatement(int resultSetType,
        int resultSetConcurrency) throws SQLException {
        return new StatementProxy(proxy.createStatement(resultSetType, resultSetConcurrency), getDbType());
    }

    @Override
    public PreparedStatement prepareStatement(String sql,
        int resultSetType,
        int resultSetConcurrency) throws SQLException {
        return new PreparedStatementProxy<PreparedStatement>(
                proxy.prepareStatement(sql, resultSetType, resultSetConcurrency), createAuditObject(sql), getDbType());
    }

    @Override
    public CallableStatement prepareCall(String sql,
        int resultSetType,
        int resultSetConcurrency) throws SQLException {
        return new CallableStatementProxy(proxy.prepareCall(sql, resultSetType, resultSetConcurrency),
                createAuditObject(sql));
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return proxy.getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
        proxy.setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws SQLException {
        proxy.setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException {
        return proxy.getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException {
        return proxy.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException {
        return proxy.setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException {
        proxy.rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException {
        proxy.releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType,
        int resultSetConcurrency,
        int resultSetHoldability) throws SQLException {
        return new StatementProxy(proxy.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability),
                getDbType());
    }

    @Override
    public PreparedStatement prepareStatement(String sql,
        int resultSetType,
        int resultSetConcurrency,
        int resultSetHoldability) throws SQLException {
        return new PreparedStatementProxy<PreparedStatement>(
                proxy.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability),
                createAuditObject(sql), getDbType());
    }

    @Override
    public CallableStatement prepareCall(String sql,
        int resultSetType,
        int resultSetConcurrency,
        int resultSetHoldability) throws SQLException {
        return new CallableStatementProxy(
                proxy.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability),
                createAuditObject(sql));
    }

    @Override
    public PreparedStatement prepareStatement(String sql,
        int autoGeneratedKeys) throws SQLException {
        return new PreparedStatementProxy<PreparedStatement>(proxy.prepareStatement(sql, autoGeneratedKeys),
                createAuditObject(sql), getDbType());
    }

    @Override
    public PreparedStatement prepareStatement(String sql,
        int[] columnIndexes) throws SQLException {
        return new PreparedStatementProxy<PreparedStatement>(proxy.prepareStatement(sql, columnIndexes),
                createAuditObject(sql), getDbType());
    }

    @Override
    public PreparedStatement prepareStatement(String sql,
        String[] columnNames) throws SQLException {
        return new PreparedStatementProxy<PreparedStatement>(proxy.prepareStatement(sql, columnNames),
                createAuditObject(sql), getDbType());
    }

    @Override
    public Clob createClob() throws SQLException {
        return proxy.createClob();
    }

    @Override
    public Blob createBlob() throws SQLException {
        return proxy.createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException {
        return proxy.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return proxy.createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException {
        return proxy.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name,
        String value) throws SQLClientInfoException {
        proxy.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        proxy.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException {
        return proxy.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException {
        return proxy.getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName,
        Object[] elements) throws SQLException {
        return proxy.createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName,
        Object[] attributes) throws SQLException {
        return proxy.createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException {
        proxy.setSchema(schema);
    }

    @Override
    public String getSchema() throws SQLException {
        return proxy.getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException {
        proxy.abort(executor);
    }

    @Override
    public void setNetworkTimeout(Executor executor,
        int milliseconds) throws SQLException {
        proxy.setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException {
        return proxy.getNetworkTimeout();
    }

    private AuditObject createAuditObject(String sql) throws SQLException {
        return new AuditObjectBuilder().build(getDbType(), sql);
    }

    private String getDbType() throws SQLException {
        return proxy.getMetaData().getDatabaseProductName().toLowerCase();
    }
}
