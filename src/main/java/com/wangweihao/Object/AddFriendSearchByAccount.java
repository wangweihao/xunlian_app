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
* 仅仅返回对方的信息，不添加，等待对方点击确定时，mark 15 添加好友
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
        setDerivedClassOtherMeber();
        JSONObject Info = new JSONObject();
        JSONObject errorRet = new JSONObject();
        errorRet.put("data", ObtainData.getData());
        Info.put("requestPhoneNum", basicObject.getAccount());
        Info.put("mark", basicObject.getMark());
        errorRet.put("error", 1);
        errorRet.put("status", "success");
        Info.put("IsSuccess", "failure");
        System.out.println("通过帐号查询好友");
        if(detectTheKeyIsRight() == false){
            Info.put("ResultINFO", "key值不对，请确认后重试");
            errorRet.put("result", Info);
            ResponseString = errorRet.toString();
            return this;
        }
        try {
            getFriendInfo();
        } catch (SQLException e) {
            e.printStackTrace();
            Info.put("ResultINFO", "输入信息有误：对方已是您的好友或帐号不存在，请确认后重试");
            errorRet.put("result", Info);
            ResponseString = errorRet.toString();
        }
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        friendAccount = new String(jsonObject.getString("friendaccount"));
        theKey = new String(jsonObject.getString("theKey"));
    }
    private boolean detectTheKeyIsRight() throws SQLException {
        String sqlGetTheKey = "select theKey from UserInfo where account = \""
                + friendAccount + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetTheKey);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        if(resultSet.getString(1).equalsIgnoreCase(theKey) || resultSet.getString(1).equalsIgnoreCase("")) {
            return true;
        }else {
            return false;
        }
    }

    private void getFriendInfo() throws SQLException {
        String sqlGetfriendUid = "select uid from UserInfo where account = \"" + friendAccount + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetfriendUid);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        FriendUid = resultSet.getInt(1);
        JSONObject friendInfo = new JSONObject();
        friendInfo.put("error", 0);
        friendInfo.put("status", "success");
        friendInfo.put("date", ObtainData.getData());
        getNameAndHead(friendInfo);
        getFriendContact(friendInfo);
    }

    private void getNameAndHead(JSONObject friendInfo) throws SQLException {
        String sqlGetNameAndHead = "select name, head from UserInfo where uid = \"" +
                FriendUid + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetNameAndHead);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        friendInfo.put("name", resultSet.getString(1));
        friendInfo.put("head", resultSet.getBytes(2));
    }

    private void getFriendContact(JSONObject friendInfo) throws SQLException {
        String sqlGetFriendContact = "select type, content from UserContact where uid = " + FriendUid + ";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetFriendContact);
        resultSet = preparedStatement.executeQuery();
        JSONObject friendContact = new JSONObject();
        int setIsNullFlag = 0;
        while (resultSet.next()){
            friendContact.put(ContactType.ContactMap.get(resultSet.getInt(1)), resultSet.getString(2));
            setIsNullFlag++;
        }
        if(setIsNullFlag == 0){
            friendContact.put("personNumber", basicObject.getAccount());
            friendContact.put("workNumber", "");
            friendContact.put("homeNumber", "");
            friendContact.put("personEmail", "");
            friendContact.put("workEmail", "");
            friendContact.put("homeEmail", "");
            friendContact.put("qqNumber", "");
            friendContact.put("weiboNumber", "");

        }
        friendInfo.put("result", friendContact);
        ResponseString = friendInfo.toString();
    }


    private ResultSet resultSet;
    private int FriendUid;
    private String friendAccount;
    private String theKey;
}

