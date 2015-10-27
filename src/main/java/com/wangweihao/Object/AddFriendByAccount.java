package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ContactType;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 15-10-21.
 */
public class AddFriendByAccount extends AccessDatabase {
    public AddFriendByAccount() {
        super();
    }
    public AddFriendByAccount(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("通过帐号添加好友");
        setDerivedClassOtherMeber();
        if(addFriendByAccount() == 0)
            getFriendInfo();
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        friendAccount = new String(jsonObject.getString("friendaccount"));
    }

    private int addFriendByAccount() throws SQLException {
        String sqlGetUserAccountId = "insert into UserFriend (uid, friendId) values (" +
                "(select uid from UserInfo where account = \"" + basicObject.getAccount() + "\")," +
                "(select uid from UserInfo where account = \"" + friendAccount + "\"));";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetUserAccountId);
        try {
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            JSONObject Info = new JSONObject();
            JSONObject errorRet = new JSONObject();
            errorRet.put("error", 1);
            errorRet.put("status", "success");
            errorRet.put("data", ObtainData.getData());
            Info.put("requestPhoneNum", basicObject.getAccount());
            Info.put("IsSunncess", "failure");
            Info.put("mark", basicObject.getMark());
            Info.put("ResultINFO", "对方已是您的好友");
            errorRet.put("result", Info);
            ResponseString = errorRet.toString();
            return 1;
        }
        return 0;
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
        while (resultSet.next()){
            friendContact.put(ContactType.ContactMap.get(resultSet.getInt(1)), resultSet.getString(2));
        }
        friendInfo.put("result", friendContact);
        ResponseString = friendInfo.toString();
    }


    private ResultSet resultSet;
    private int FriendUid;
    private String friendAccount;
}

