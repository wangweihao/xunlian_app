package com.wangweihao.AccessDatabase;

import com.wangweihao.Object.RecvBasicMessageObject;
import com.wangweihao.Xl_db.DatabasePool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by wwh on 15-10-21.
 */
public class AccessDatabase {
    public AccessDatabase(){
        basicObject = new RecvBasicMessageObject();
        sqlString = new String();
        DBPoolConnection = DatabasePool.getConnection();
    }
    public AccessDatabase(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
        sqlString = new String();
    }

    public RecvBasicMessageObject AccessXlDatabase() throws SQLException {
        return null;
    }

    public void ConstructObject(){

    }

    public RecvBasicMessageObject basicObject;
    public String sqlString;
    public Connection DBPoolConnection;
    public PreparedStatement preparedStatement;
}
