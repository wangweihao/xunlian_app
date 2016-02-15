package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Created by wwh on 16-1-16.
 */
public class BackupLocalData extends AccessDatabase {
    public BackupLocalData(){
        super();
    }
    public BackupLocalData(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException{
        setDerivedClassOtherMeber();
        if(getUserUid() && addContacts()){
            System.out.println("备份数据成功");
        }else{
            System.out.println("备份数据失败");
        }
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        contacts = new LinkedList<JSONObject>();
        JSONArray contactsJsonArray = jsonObject.getJSONArray("contacts");
        int size = contactsJsonArray.length();
        System.out.println("size:" + size);
        for(int i = 0; i < size; ++i){
            contacts.add((JSONObject) contactsJsonArray.get(i));
        }
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
            buildReturnValue(1, "success", "failure", "帐号有误！请稍后再试");
            return false;
        }


        return true;
    }

    /*
    * 一次性插入多条数据的 sql
    *  insert into BackupLocalContacts(uid, name, contact) values (1,1,1),(2,2,2),(3,3,3);
    * 用来提升性能
    * */
    public boolean addContacts() throws SQLException{
        String addContactsSql = "insert into BackupLocalContacts(uid, name, contact) values ";
        for(JSONObject json : contacts){
            addContactsSql += "(" + userUid + ", \"" + json.getString("name") + "\", \"" + json.getString("phone") + "\"),";
        }
        addContactsSql = addContactsSql.substring(0, addContactsSql.length()-1);
        addContactsSql += ";";
        System.out.println("sql:" + addContactsSql);
        try{
            preparedStatement = DBPoolConnection.prepareStatement(addContactsSql);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        buildReturnValue(0, "success", "success", "备份数据成功");

        return true;
    }

    private int userUid;
    private LinkedList<JSONObject> contacts;
    private ResultSet resultSet;
}
