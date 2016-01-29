package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ContactType;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 15-10-21.
 * 需要改进对方账户不存在的情况
 */

/*
* 返回好友的信息
* */
public class AddFriendSearchByAccount extends AccessDatabase {
    public AddFriendSearchByAccount() {
        super();
    }
    public AddFriendSearchByAccount(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        JSONObject friendInfo = new JSONObject();
        setDerivedClassOtherMeber();
        if(getFriendInfo(friendInfo) && getNameAndHead(friendInfo) && getFriendContact(friendInfo)){
            System.out.println("获得好友信息成功...");
        }else{
            System.out.println("获得好友信息失败...");
        }

        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        friendAccount = new String(jsonObject.getString("friendaccount"));
    }

    private boolean getFriendInfo(JSONObject friendInfo) throws SQLException {
        String sqlGetfriendUid = "select uid from UserInfo where account = \"" + friendAccount + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetfriendUid);
        try {
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            FriendUid = resultSet.getInt(1);

            friendInfo.put("error", 0);
            friendInfo.put("status", "success");
            friendInfo.put("date", ObtainData.getData());
            getNameAndHead(friendInfo);
            getFriendContact(friendInfo);
        }catch (SQLException e){
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        return true;
    }

    private boolean getNameAndHead(JSONObject friendInfo) throws SQLException {
        String sqlGetNameAndHead = "select name, head from UserInfo where uid = \"" +
                FriendUid + "\";";
        try {
            preparedStatement = DBPoolConnection.prepareStatement(sqlGetNameAndHead);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            friendInfo.put("name", resultSet.getString(1));
            friendInfo.put("head", resultSet.getInt(2));
        }catch (SQLException e){
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        return true;
    }

    private boolean getFriendContact(JSONObject friendInfo) throws SQLException {
        String sqlGetFriendContact = "select type, content from UserContact where uid = " + FriendUid + ";";
        try {
            preparedStatement = DBPoolConnection.prepareStatement(sqlGetFriendContact);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e){
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        JSONObject friendContact = new JSONObject();
        friendContact.put("account", friendAccount);
        friendContact.put("personNumber", basicObject.getAccount());
        friendContact.put("workNumber", "");
        friendContact.put("homeNumber", "");
        friendContact.put("personEmail", "");
        friendContact.put("workEmail", "");
        friendContact.put("homeEmail", "");
        friendContact.put("qqNumber", "");
        friendContact.put("weiboNumber", "");
        while (resultSet.next()){
            friendContact.put(ContactType.ContactMap.get(resultSet.getInt(1)), resultSet.getString(2));
        }
        friendInfo.put("result", friendContact);
        ResponseString = friendInfo.toString();
        return true;
    }


    private ResultSet resultSet;
    private int FriendUid;
    private String friendAccount;
}

