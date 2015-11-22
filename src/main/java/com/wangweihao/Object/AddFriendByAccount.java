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
        addFriendByAccount();
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
        /* 组装返回 Json 信息 */
        JSONObject Info = new JSONObject();
        Info.put("requestPhoneNum", basicObject.getAccount());
        Info.put("mark", basicObject.getMark());
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            JSONObject errorRet = new JSONObject();
            errorRet.put("error", 1);
            errorRet.put("status", "success");
            errorRet.put("data", ObtainData.getData());
            Info.put("IsSuccess", "failure");
            Info.put("ResultINFO", "输入信息有误：对方已是您的好友或帐号不存在，请确认后重试");
            errorRet.put("result", Info);
            ResponseString = errorRet.toString();
            return 1;
        }
        JSONObject rightRet = new JSONObject();
        rightRet.put("error", 0);
        rightRet.put("status", "success");
        rightRet.put("data", ObtainData.getData());
        Info.put("IsSuccess", "success");
        Info.put("ResultINFO", "添加好友成功，对方已是您的好友");
        rightRet.put("result", Info);
        ResponseString = rightRet.toString();
        return 0;
    }

    private ResultSet resultSet;
    private int FriendUid;
    private String friendAccount;
}
