package org.shenjia.sqlaudit;

import org.junit.Test;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcConstants;

public class AuditObjectBuilderTest {

    @Test
    public void test_convert() {
        String updateSql = "update user set username=?,email=? where userid=?";
        AuditObjectBuilder builder = new AuditObjectBuilder();
        String selectSql = builder.build(JdbcConstants.MYSQL, updateSql).getSelectSql();

        System.err.println("-- UPDATE SQL -- :");
        System.out.println(SQLUtils.format(updateSql, JdbcConstants.MYSQL));

        System.err.println("-- SELECT SQL -- :");
        System.out.println(selectSql);
    }
}
