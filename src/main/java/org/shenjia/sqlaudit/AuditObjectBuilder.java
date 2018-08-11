package org.shenjia.sqlaudit;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLUpdateSetItem;
import com.alibaba.druid.sql.ast.statement.SQLUpdateStatement;
import com.alibaba.druid.sql.builder.SQLSelectBuilder;
import com.alibaba.druid.sql.builder.impl.SQLSelectBuilderImpl;

class AuditObjectBuilder {
    
    private static final Logger LOG = Logger.getLogger(AuditObjectBuilder.class.getName());

    public AuditObject build(String dbType,
        String sql) {
        SQLStatement stmt = SQLUtils.parseStatements(sql, dbType).get(0);
        if (stmt instanceof SQLUpdateStatement) {
            return buildUpdateAuditObject(dbType, sql, (SQLUpdateStatement) stmt);
        }
        if (LOG.isLoggable(Level.FINER)) {
            LOG.finer("Unsupported SQL statement. " + sql);
        }
        return null;
    }

    private AuditObject buildUpdateAuditObject(final String dbType,
        final String sql,
        SQLUpdateStatement stmt) {
        AuditObject ao = new AuditObject(dbType, sql, "UPDATE");
        ao.setTableName(stmt.getTableName().getSimpleName());

        SQLSelectBuilder builder = new SQLSelectBuilderImpl(dbType);
        List<SQLUpdateSetItem> items = stmt.getItems();
        int size = items.size();
        String[] cols = new String[size];
        Map<String, Object> columns = new LinkedHashMap<>();
        int offset = 0;
        for (int i = 0; i < size; i++) {
            SQLUpdateSetItem item = items.get(i);
            cols[i] = item.getColumn().toString();
            String val = item.getValue().toString().replaceAll("'", "");
            columns.put(cols[i], val);
            if ("?".equals(val)) {
                offset++;
            }
        }

        builder.select(cols);
        builder.from(ao.getTableName());
        builder.where(stmt.getWhere().toString());
        
        ao.setColumns(columns);
        ao.setSelectParameterOffset(offset);
        ao.setSelectSql(builder.toString());

        return ao;
    }
}
