package com.wangweihao.Object;
import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.OneFriendInfo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by wwh on 15-10-21.
 */
public class ObtainAllContactsData extends AccessDatabase {
    public ObtainAllContactsData() {
        super();
        notExist = new String("");
    }
    public ObtainAllContactsData(int _mark, String _account){
        super(_mark, _account);
        notExist = new String("");
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("获得所有的联系人数据");
        setDerivedClassOtherMeber();
        getUserFriendInfo();
        return this;
    }

    private int getUserAccountId() throws SQLException {
        String sqlGetUserAccountId = "select uid from UserInfo where account = \'" +
                basicObject.getAccount() + "\';";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetUserAccountId);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    private void getUserFriendAccountId() throws SQLException {
        String sqlGetUserFriendAccountId = "select friendId from UserFriend where uid = " +
                getUserAccountId() + ";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetUserFriendAccountId);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            FriendId.add(resultSet.getInt(1));
        }
    }

    /* 设置一位好友信息 */
    private void setOneFriendInfo(int userId) throws SQLException {
        OneFriendInfo myFriend = new OneFriendInfo();
        String sqlGetFriendNameAndHead = "select name, head from UserInfo where uid = \"" +
                userId + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetFriendNameAndHead);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        myFriend.setName(resultSet.getString(1));
        myFriend.setHead(resultSet.getBytes(2));
        String sqlGetFriendContact = "select type, content from UserContact where uid = \"" + userId + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetFriendContact);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            if(myFriend.contactSet.get(resultSet.getInt(1)).getContact() != notExist){
                myFriend.contactSet.get(resultSet.getInt(1)).update(resultSet.getInt(1), resultSet.getString(2));
            }
        }
        myFriendInfo.add(myFriend);
    }

    /* 组装好友信息 */
    private void packFriendInfo(){
        ResponseString += "{\"error\":0, \"status\":\"success\", \"date\":\"2015-08\", " +
                "\"result\":[";
        for (OneFriendInfo oneFriend : myFriendInfo){
            JSONArray a = new JSONArray();

        }
    }

    /* 得到所有好友的信息并组床成响应 */
    private void getUserFriendInfo() throws SQLException {
        getUserFriendAccountId();
        for (int UserId : FriendId){
            setOneFriendInfo(UserId);
        }
    }

    private ResultSet resultSet;
    private HashSet<Integer> FriendId;
    private ArrayList<OneFriendInfo> myFriendInfo;
    private String notExist;
}
