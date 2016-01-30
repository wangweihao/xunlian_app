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
        if(addFriendByAccount() && addFriendAndUser()){
            System.out.println("添加好友成功");
        }else{
            System.out.println("添加好友失败");
        }
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        friendAccount = new String(jsonObject.getString("friendaccount"));
    }
    private boolean addFriendByAccount() throws SQLException {
        String sqlGetUserAccountId = "insert into UserFriend (uid, friendId) values (" +
                "(select uid from UserInfo where account = \"" + basicObject.getAccount() + "\")," +
                "(select uid from UserInfo where account = \"" + friendAccount + "\"));";
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
        String sqlGetUserAccountId = "insert into UserFriend (uid, friendId) values (" +
                "(select uid from UserInfo where account = \"" + friendAccount + "\")," +
                "(select uid from UserInfo where account = \"" + basicObject.getAccount() + "\"));";
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
    private int FriendUid;
    private String friendAccount;
}
