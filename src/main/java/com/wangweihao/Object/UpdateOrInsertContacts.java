package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by wwh on 15-10-21.
 */
public class UpdateOrInsertContacts extends AccessDatabase {
    public UpdateOrInsertContacts(){
        super();
    }

    public UpdateOrInsertContacts(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("更新或者加入新的联系人");
        setDerivedClassOtherMeber();
        ObtainFriendAccount();
        switch (IsUpdateOrInsert){
            case 1:
                InsertContact();
                break;
            case 2:
                UpdateContact();
                break;
        }
        UpdateFriendFlagIsUpdate();
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        Type = jsonObject.getInt("type");
        IsUpdateOrInsert = jsonObject.getInt("isUpdateOrInsert");
        Contact = new String(jsonObject.getString("contact"));
    }


    private void InsertContact() throws SQLException {
        try{
            String sqlInsertContact = "insert into UserContact (uid, type, content) values ((select uid from UserInfo" +
                    " where account = \'" + basicObject.getAccount() + "\'), " + Type + ", \'" + Contact +"\');";
            preparedStatement = DBPoolConnection.prepareStatement(sqlInsertContact);
            if (preparedStatement.executeUpdate() == 1){
                ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                        "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                        "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"新建联系方式成功\"}}";
            }
        }catch (SQLException sqle){
            ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"新建联系方式失败\"}}";
            sqle.printStackTrace();
        }
    }

    private void UpdateContact() throws SQLException {
        String sqlUpdateContact = "update UserContact set content = \"" + Contact + "\" where " +
                "uid = \"" + UserId + "\" and type = \"" + Type + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlUpdateContact);
        if (preparedStatement.executeUpdate() == 1){
            ResponseString =  "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"更新联系方式成功\"}}";
        }else {
            ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"更新联系方式失败\"}}";
        }
    }

    private void ObtainFriendAccount() throws SQLException {

        String sqlObtainUserAccount = "select friendId from UserFriend where uid = \"" +
                ObtainUserAccount() + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlObtainUserAccount);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            FriendAccount.add(resultSet.getString(1));
        }
    }

    private String ObtainUserAccount() throws SQLException {
        String sqlUserAccount = "select uid from UserInfo where account = \"" +
                basicObject.getAccount() + "\";";
        FriendAccount = new LinkedList<String>();
        preparedStatement = DBPoolConnection.prepareStatement(sqlUserAccount);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String UserAccount = resultSet.getString(1);
        System.out.println("UserAccount:" + UserAccount);
        UserId = new String(UserAccount);
        return UserAccount;
    }

    private void UpdateFriendFlagIsUpdate() throws SQLException {
        for(String theFriendAccount : FriendAccount){
            String sqlUpdateFriendFlagIsUpdate = "select isUpdate from UserFriend where uid = \"" + UserId +"\"" +
                    " and friendId = \"" + theFriendAccount + "\";";
            preparedStatement = DBPoolConnection.prepareStatement(sqlUpdateFriendFlagIsUpdate);
            resultSet = preparedStatement.executeQuery();
            int oldFlagIsUpdate = 0;
            while (resultSet.next()){
                oldFlagIsUpdate += resultSet.getInt(1);
            }
            int newFlagIsUpdate = oldFlagIsUpdate | Type;
            String sqlUpdateFriendIsUpdate =  "update UserFriend set isUpdate = \"" + newFlagIsUpdate + "\" where uid = " +
                    "\"" + UserId + "\" and friendId = \"" + theFriendAccount + "\";";
            preparedStatement = DBPoolConnection.prepareStatement(sqlUpdateFriendIsUpdate);
            if(preparedStatement.executeUpdate() != 1){
                ResponseString = "{\"error\":1, \"status\":\"failure\", \"date\":\"" + ObtainData.getData() + "\", " +
                        "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"failure\"," +
                        "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"更新好友信息失败\"}}";
                break;
            }
        }
    }

    private int Type;
    private int IsUpdateOrInsert;
    private String Contact;
    private String UserId;
    private List<String> FriendAccount;
    private ResultSet resultSet;
}
