package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 15-11-22.
 */
public class AddFriendByAccount extends AccessDatabase{
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
        /*
        * 0 好友关系存在，修改 relation
        * 1 好友关系不存在，双向添加
        * 2 系统异常
        * */
        if(getUserUid() && getFriendUid()) {
            System.out.println("查询用户 uid 成功");
            switch (detectRelationIsExist()) {
                case 0:
                    if (getUserAndFriendRelation()) {
                        if (userAndFriendRelation == 1) {
                            modifyUserAndFriendRelation();
                        } else {
                            buildReturnValue(0, "success", "success", "添加好友成功");
                        }
                    }
                    break;
                case 1:
                    if (addFriendByAccount() && addFriendAndUser()) {
                        System.out.println("添加好友成功");
                    } else {
                        System.out.println("添加好友失败");
                    }
                    break;
                default:
                    break;
            }
        }
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        friendAccount = new String(jsonObject.getString("friendaccount"));
    }

    /* 获取好友标记 relation */
    private boolean getUserAndFriendRelation() throws SQLException{
        String getUserAndFriendRelationSql = "select relation from UserFriend where uid = \"" + userUid +
                "\" and friendId = \"" + friendUid + "\";";
        try {
            preparedStatement = DBPoolConnection.prepareStatement(getUserAndFriendRelationSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                userAndFriendRelation = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试~");
            return false;
        }
        return true;
    }

    /* 修改好友标记 relation */
    private boolean modifyUserAndFriendRelation() throws SQLException{
        String modifyUserAndFriendRelationSql = "update UserFriend set relation = 0 where uid = \"" + userUid +
                "\" and friendId = \"" + friendUid + "\";";
        try {
            preparedStatement = DBPoolConnection.prepareStatement(modifyUserAndFriendRelationSql);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试~");
            return false;
        }
        buildReturnValue(0, "success", "success", "添加好友成功~");
        return true;
    }

    /* 检测好友关系是否存在 */
    private int detectRelationIsExist(){
        String detectRelationIsExistSql = "select count(*) from UserFriend where uid = \"" + userUid +
                "\" and friendId = \"" + friendUid + "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(detectRelationIsExistSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                /* 说明好友关系存在，仅修改标记即可 */
                if(resultSet.getInt(1) == 1){
                    return 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试~");
            return 2;
        }
        /* 说明好友关系不存在，双向添加 */
        return 1;
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



    private boolean addFriendByAccount() throws SQLException {
        String sqlGetUserAccountId = "insert into UserFriend (uid, friendId) values (\"" + userUid +
                "\", \"" + friendUid + "\");";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetUserAccountId);
        /* 组装返回 Json 信息 */
        JSONObject Info = new JSONObject();
        Info.put("requestPhoneNum", basicObject.getAccount());
        Info.put("mark", basicObject.getMark());
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍候再试");
            return false;
        }
        return true;
    }

    /* 双向添加 */
    public boolean addFriendAndUser() throws SQLException {
        String sqlGetUserAccountId = "insert into UserFriend (uid, friendId) values (\"" + friendUid +
                "\", \"" + userUid + "\");";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetUserAccountId);
        try{
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        buildReturnValue(0, "success", "success", "添加好友成功");
        return true;
    }


    private ResultSet resultSet;
    private String friendAccount;
    private int friendUid;
    private int userUid;
    private int friendAndUserRelation;
    private int userAndFriendRelation;
}
