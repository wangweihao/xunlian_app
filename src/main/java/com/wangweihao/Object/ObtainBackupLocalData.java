package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 16-1-19.
 */
public class ObtainBackupLocalData extends AccessDatabase {
    public ObtainBackupLocalData(){
        super();
    }
    public ObtainBackupLocalData(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        if(getUserUid() && getBackupData()){
            System.out.println("获取备份数据成功");
        }else  {
            System.out.println("获取备份数据失败");
        }

        return this;
    }

    public boolean getUserUid() throws SQLException {
        String getUserUidSql = "select uid from UserInfo where account = \"" + basicObject.getAccount() + "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(getUserUidSql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            userUid = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }

        return true;
    }

    public boolean getBackupData() throws SQLException {
        String getBackupDataSql = "select name, contact from BackupLocalContacts where uid = " + userUid + ";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(getBackupDataSql);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(11, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        JSONArray contacts = new JSONArray();
        /* 将结果组装成 json 发回客户端 */
        while (resultSet.next()){
            JSONObject one = new JSONObject();
            one.put("name", resultSet.getString(1));
            one.put("phone", resultSet.getString(2));
            contacts.put(one);
        }
        buildReturnValue(0, "success", "success", contacts.toString());

        return true;
    }

    private int userUid;
    private ResultSet resultSet;
}
