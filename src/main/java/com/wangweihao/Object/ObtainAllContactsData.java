package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

/**
 * Created by wwh on 15-10-21.
 */
public class ObtainAllContactsData extends AccessDatabase {
    public ObtainAllContactsData() {
        super();
    }
    public ObtainAllContactsData(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() {
        System.out.println("获得所有的联系人数据");
        setDerivedClassOtherMeber();
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

    private void getUserFriendInfo() throws SQLException {
        for (int UserId : FriendId){
            String sqlGetFriendNameAndHead = "select type, content from UserContact where uid = \"" +
                    UserId + "\";";
            preparedStatement = DBPoolConnection.prepareStatement(sqlGetFriendNameAndHead);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();

        }
    }

    private ResultSet resultSet;
    private HashSet<Integer> FriendId;
}
