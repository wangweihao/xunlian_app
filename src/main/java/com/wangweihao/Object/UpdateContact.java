package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by wwh on 15-11-22.
 */
public class UpdateContact extends AccessDatabase {
    public UpdateContact() {
        super();
    }

    public UpdateContact(int _mark, String _account) {
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        try{
            setDerivedClassOtherMeber();
            getUserAccountId();
            updateNameAndHead();
            updateContact();
            updateFriendFlagIsUpdate();
            ResponseString =  "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"更新联系方式成功\"}}";
        }catch (SQLException e){
            e.printStackTrace();
            ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"更新联系方式失败\"}}";
        }
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber() {
        JSONObject reqJson = new JSONObject(RequestString);
        JSONObject infoJson = reqJson.getJSONObject("info");
        name = new String(infoJson.getString("name"));
        head = new String(infoJson.getString("head"));
        JSONArray contactArray = reqJson.getJSONArray("update");
        updateContact = new HashMap<Integer, String>();
        for(int i = 0; i < contactArray.length(); ++i){
            JSONObject oneContact = contactArray.getJSONObject(i);
            updateContact.put(oneContact.getInt("type"), oneContact.getString("content"));
            typeSum += oneContact.getInt("type");
        }
    }

    private void updateNameAndHead() throws SQLException {
        if(name != "" || head != ""){
            String updateNameAndHeadSql = "update UserInfo set name = \"" + name + "\", head = \""
                    + head + "\" where uid = \"" + accountUid + "\";";
            preparedStatement = DBPoolConnection.prepareStatement(updateNameAndHeadSql);
            try{
                preparedStatement.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
                System.out.println("updateNameAndHead error");
            }
        }
    }

    private void getUserAccountId() throws SQLException {
        String sqlGetUserAccountId = "select uid from UserInfo where account = \'" +
                basicObject.getAccount() + "\';";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetUserAccountId);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        accountUid = resultSet.getInt(1);
    }

    private void updateContact() throws SQLException {
        Set<Integer> typeSet = updateContact.keySet();
        for(Integer type : typeSet){
            updateOneContact(type, updateContact.get(type));
        }
    }

    private void updateOneContact(int type, String content) throws SQLException {
        String sqlUpdateContact = "update UserContact set content = \"" + content + "\" where " +
                "uid = \"" + accountUid + "\" and type = \"" + type + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlUpdateContact);
        preparedStatement.executeUpdate();
    }

    private void updateFriendFlagIsUpdate() throws SQLException {
        ObtainFriendAccount();
        for(String theFriendAccount : FriendAccount){
            String sqlUpdateFriendFlagIsUpdate = "select isUpdate from UserFriend where uid = \"" + accountUid +"\"" +
                    " and friendId = \"" + theFriendAccount + "\";";
            preparedStatement = DBPoolConnection.prepareStatement(sqlUpdateFriendFlagIsUpdate);
            resultSet = preparedStatement.executeQuery();
            int oldFlagIsUpdate = 0;
            while (resultSet.next()){
                oldFlagIsUpdate += resultSet.getInt(1);
            }
            int newFlagIsUpdate = oldFlagIsUpdate | typeSum;
            String sqlUpdateFriendIsUpdate =  "update UserFriend set isUpdate = \"" + newFlagIsUpdate + "\" where uid = " +
                    "\"" + accountUid + "\" and friendId = \"" + theFriendAccount + "\";";
            preparedStatement = DBPoolConnection.prepareStatement(sqlUpdateFriendIsUpdate);
            if(preparedStatement.executeUpdate() != 1){
                ResponseString = "{\"error\":1, \"status\":\"failure\", \"date\":\"" + ObtainData.getData() + "\", " +
                        "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"failure\"," +
                        "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"更新好友信息失败\"}}";
                break;
            }
        }
    }

    private void ObtainFriendAccount() throws SQLException {
        FriendAccount = new ArrayList<String>();
        String sqlObtainUserAccount = "select friendId from UserFriend where uid = \"" +
                accountUid + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlObtainUserAccount);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            FriendAccount.add(resultSet.getString(1));
        }
    }

    private String name;
    private String head;
    private int accountUid;
    private int typeSum = 0;
    private ResultSet resultSet;
    private Map<Integer, String> updateContact;
    private List<String> FriendAccount;

}

