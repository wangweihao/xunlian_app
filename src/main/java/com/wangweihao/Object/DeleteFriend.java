package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 15-10-21.
 */

/* update:
*  增添 relation 关系，若对方删除用户，则双向删除
*  否则，仅修改 relation 标记即可
*  后续版本可能增加黑名单或特殊关心 relation */

public class DeleteFriend extends AccessDatabase {
    public DeleteFriend() {
        super();
    }
    public DeleteFriend(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("删除好友");
        setDerivedClassOtherMeber();
        /* 先检测好友是否存在 */
        if(detectFriendExist() && getUserUid() && getFriendUid() && detectFriendAndUserRelation()){
            /* 说明对方已经删除你，你现在要删除对方，所以同时删除关系即可 */
            if(friendAndUserRelation == 1){
                deleteFriend();
            }else if(friendAndUserRelation == 0){
                /* 说明你还是对方的好友，仅修改不是好友标记即可 */
                modifyRelationToDelete();
            }
        }
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        friendAccount = new String(jsonObject.getString("friendaccount"));
    }

    /* 修改标记为删除关系 relation = 1 */
    private boolean modifyRelationToDelete() throws SQLException{
        String modifyRelationToDeleteSql = "update UserFriend set relation = 1 where uid = \"" + userUid
                + "\" and friendId = \"" + friendUid + "\";";
        try {
            preparedStatement = DBPoolConnection.prepareStatement(modifyRelationToDeleteSql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试~");
            return false;
        }
        buildReturnValue(0, "success", "success", "删除好友成功~");
        return true;
    }

    /* 获取用户 uid */
    private boolean getUserUid() throws SQLException{
        String getUserUidSql = "select uid from UserInfo where account = \"" + basicObject.getAccount()
                + "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(getUserUidSql);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                userUid = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试哦~");
            return false;
        }
        return true;
    }

    /* 获取好友 uid */
    private boolean getFriendUid() throws SQLException{
        String getFriendUidSql = "select uid from UserInfo where account = \"" + friendAccount
                + "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(getFriendUidSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                friendUid = resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "failure", "success", "系统错误，请稍后再试哦~");
            return false;
        }
        return true;
    }

    /* 获取用户和好友关系 */
    private boolean detectUserAndFriendRelation() throws SQLException{
        String detectUserAndFriendRelationSql = "select relation from UserFriend where uid = \"" + userUid
                + "\" and friendId = \"" + friendUid + "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(detectUserAndFriendRelationSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                userAndFriendRelation = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "failure", "success", "系统错误，请稍后再试哦~");
            return false;
        }
        return true;
    }

    /* 获取好友和用户关系 */
    private boolean detectFriendAndUserRelation() throws SQLException{
        String detectFriendAndUserRelationSql = "select relation from UserFriend where uid = \"" + friendUid
                + "\" and friendId = \"" + userUid + "\";";
        try {
            preparedStatement = DBPoolConnection.prepareStatement(detectFriendAndUserRelationSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                friendAndUserRelation = resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "failure", "success", "系统错误，请稍后再试哦~");
            return false;
        }
        return true;
    }

    private boolean detectFriendExist() throws SQLException {
        String detectFriendExistSql = "select count(*) from UserInfo where account = \"" +
                friendAccount + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(detectFriendExistSql);
        try {
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if(resultSet.getInt(1) != 1){
                buildReturnValue(0, "success", "success", "对方不是您的好友，请确认后删除");
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试~");
            return false;
        }
        return true;
    }

    private boolean deleteFriend() throws SQLException {
        String sqlDeleteFriend =  "delete from UserFriend where (uid = \"" + userUid +
                "\" and friendId = \"" + friendUid + "\") or ( uid = \"" + friendUid +
                "\" and friendId = \"" + userUid + "\");";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(sqlDeleteFriend);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "删除好友失败，请稍后再试~");
            return false;
        }
        buildReturnValue(1, "success", "success", "删除好友成功~");
        return true;
    }

    private String friendAccount;
    private ResultSet resultSet;
    private int friendUid;
    private int userUid;
    private int friendAndUserRelation;
    private int userAndFriendRelation;
}
