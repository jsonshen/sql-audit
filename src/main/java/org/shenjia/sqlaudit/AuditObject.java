package org.shenjia.sqlaudit;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuditObject implements
        Serializable {

    private static final long serialVersionUID = -2941516993093238502L;

    private String accessId;
    private String accessType;
    private LocalDateTime accessTime;
    private String dbType;
    private String sql;

    private Map<Integer, Object> parameters;
    private String tableName;
    private Map<String, Object> columns;

    private int selectParameterOffset;
    private String selectSql;
    private List<Map<String, Object>> selectResult;

    public AuditObject(String dbType,
            String sql,
            String accessType) {
        this.dbType = dbType;
        this.sql = sql;
        this.accessType = accessType;
        this.accessTime = LocalDateTime.now();
        this.accessId = SqlAuditor.getAccessId();
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public LocalDateTime getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(LocalDateTime accessTime) {
        this.accessTime = accessTime;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Map<Integer, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<Integer, Object> parameters) {
        this.parameters = parameters;
    }

    public void setParameter(Integer index,
        Object value) {
        if (null == this.parameters) {
            this.parameters = new HashMap<>();
        }
        this.parameters.put(index, value);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Map<String, Object> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Object> columns) {
        this.columns = columns;
    }

    public int getSelectParameterOffset() {
        return selectParameterOffset;
    }

    public void setSelectParameterOffset(int selectParameterOffset) {
        this.selectParameterOffset = selectParameterOffset;
    }

    public String getSelectSql() {
        return selectSql;
    }

    public void setSelectSql(String selectSql) {
        this.selectSql = selectSql;
    }

    public List<Map<String, Object>> getSelectResult() {
        return selectResult;
    }

    public void setSelectResult(List<Map<String, Object>> selectResult) {
        this.selectResult = selectResult;
    }

    public void addSelectResult(Map<String, Object> resultRow) {
        if (null == this.selectResult) {
            this.selectResult = new ArrayList<>();
        }
        this.selectResult.add(resultRow);
    }

    @Override
    public String toString() {
        return "AuditObject {accessId=" + accessId + ", accessType=" + accessType + ", accessTime=" + accessTime
                + ", dbType=" + dbType + ", sql=" + sql + ", parameters=" + parameters + ", tableName=" + tableName
                + ", columns=" + columns + ", selectParameterOffset=" + selectParameterOffset + ", selectSql="
                + selectSql + ", selectResult=" + selectResult + "}";
    }
    
}
