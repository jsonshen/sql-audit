package org.shenjia.sqlaudit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class SqlAuditDataSourceTest {

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void test_audit() throws Exception {
        Map properties = new HashMap<>();
        properties.put("driverClassName", "com.mysql.jdbc.Driver");
        properties.put("url", "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false");
        properties.put("username", "root");
        properties.put("password", "123456");
        //初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时 
        properties.put("initialSize", "1");
        //定义最大连接池数量  
        properties.put("maxActive", "20");
        //获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
        properties.put("maxWait", "60000");
        //是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。
        //在mysql5.5以下的版本中没有PSCache功能，建议关闭掉。5.5及以上版本有PSCache，建议开启。 
        properties.put("poolPreparedStatements", "false");
        //要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
        //在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
        properties.put("maxPoolPreparedStatementPerConnectionSize", "100");
        //用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。 
        properties.put("validationQuery", "SELECT 'x'");
        //申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 
        properties.put("testOnBorrow", "false");
        //归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
        properties.put("testOnReturn", "false");
        //建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。 
        properties.put("testWhileIdle", "true");
        //属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：监控统计用的filter:stat;日志用的filter:log4j;防御sql注入的filter:wall 
        properties.put("filters", "stat,wall");
        //有两个含义：1) Destroy线程会检测连接的间隔时间;2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明 
        properties.put("timeBetweenEvictionRunsMillis", "3000");
        //配置一个连接在池中最小生存的时间，单位是毫秒
        properties.put("minEvictableIdleTimeMillis", "300000");
        DataSource ds = DruidDataSourceFactory.createDataSource(properties);
        
        SqlAuditor.setAccessId(UUID.randomUUID().toString());
        SqlAuditDataSource sads = new SqlAuditDataSource(ds);

        try (Connection conn = sads.getConnection(); Statement stmt = conn.createStatement();) {
            int count = stmt.executeUpdate("update acl_user set user_name='Test Audit' where user_id='12312323'");
            System.err.println("--- 1 COUNT -- : " + count);
        }
        
        String updateSql = "update acl_user set user_name=?,email_id=? where user_id=?";
        try (Connection conn = sads.getConnection(); PreparedStatement ps = conn.prepareStatement(updateSql);) {
            ps.setString(1, "Audit");
            ps.setString(2, "test@abc.com");
            ps.setString(3, "12312323");
            int count = ps.executeUpdate();
            System.err.println("--- 2 COUNT -- : " + count);
        }
    }
}
