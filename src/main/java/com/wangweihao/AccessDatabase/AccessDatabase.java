package com.wangweihao.AccessDatabase;

import com.wangweihao.HelpClass.ObtainData;
import com.wangweihao.Object.RecvBasicMessageObject;
import com.wangweihao.Xl_db.DatabasePool;
import org.json.JSONObject;

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
        RequestString = new String();
    }
    public AccessDatabase(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
        sqlString = new String();
        DBPoolConnection = DatabasePool.getConnection();
        RequestString = new String();
    }

    public AccessDatabase AccessXlDatabase() throws SQLException {
        return null;
    }

    public RecvBasicMessageObject getRecvBasicMessageObject(){
        return basicObject;
    }

    public void ConstructSelfInfo(int SuccessOrFailure){
    }

    public String getResponseString(){
        return ResponseString;
    }

    public void setDerivedClassOtherMeber(){

    }

    public void setRequestString(String _requestString){
        RequestString = _requestString;
        System.out.println("requestString:" + RequestString);
    }

    public RecvBasicMessageObject basicObject;
    public String sqlString;
    public Connection DBPoolConnection;
    public PreparedStatement preparedStatement;
    public String ResponseString;
    public String RequestString;
    public JSONObject jsonObject;
}
