package com.wangweihao.Xl_db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;

/**
 * Created by wwh on 15-10-18.
 */
public class DatabasePool {
    private static ComboPooledDataSource poolSource;
    private static final DatabasePool dbPool;
    private static DatabaseUserInfo userInfo;

    static {
        dbPool = new DatabasePool("root", "w13659218813", "127.0.0.1", "XL_db");
    }

    public DatabasePool(String account, String password, String ip, String databaseName){
        try{
            userInfo = new DatabaseUserInfo(account, password, ip, databaseName);
            poolSource = new ComboPooledDataSource();
            poolSource.setUser(userInfo.getUserAccount());
            poolSource.setPassword(userInfo.getUserPassword());
            String jdbcUrl = "jdbc:mysql://"+ userInfo.getUserIp() + "/" + databaseName + "?user=" +
                    userInfo.getUserAccount() + "&password=" + userInfo.getUserPassword() + "&useUnicode=true";
            poolSource.setJdbcUrl(jdbcUrl);
            poolSource.setDriverClass("com.mysql.jdbc.Driver");
            poolSource.setInitialPoolSize(1);
            poolSource.setMinPoolSize(2);
            poolSource.setMaxPoolSize(10);
            poolSource.setMaxStatements(50);
            poolSource.setMaxIdleTime(60);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        try{
            return poolSource.getConnection();
        }catch (SQLException sqlEx){
            sqlEx.printStackTrace();
            throw new RuntimeException("无法获取连接", sqlEx);
        }
    }

    /* 单元测试 */
    public static void main(String[] aegs) throws SQLException {
        Connection con = getConnection();
        String sql = "select account,password from UserInfo where account = 13659218813";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        rs.next();
        System.out.println(rs.getString(1) + "|" + rs.getString(2));
    }
}
