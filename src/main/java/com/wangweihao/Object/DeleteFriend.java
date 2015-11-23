package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 15-10-21.
 */
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
        if(detectFriendExist() == true) {
            deleteFriend();
        }else{
            AssembleErrorReturnJson();
        }
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        friendAccount = new String(jsonObject.getString("friendaccount"));
    }

    private boolean detectFriendExist() throws SQLException {
        String detectFriendExistSql = "select count(*) from UserInfo where account = \"" +
                friendAccount + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(detectFriendExistSql);
        try {
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            if(resultSet.getInt(1) != 1){
                return false;
            }
        }catch (SQLException e){
            return false;
        }
        return true;
    }

    private void deleteFriend() throws SQLException {
        String sqlDeleteFriend =  "delete from UserFriend where " +
                "friendId = ( select uid from UserInfo where account = \'" + friendAccount
                + "\') and (select uid from UserInfo where account = \'"  + basicObject.getAccount() + "\');";
        preparedStatement = DBPoolConnection.prepareStatement(sqlDeleteFriend);
        JSONObject retObj = new JSONObject();
        JSONObject info = new JSONObject();
        retObj.put("data", ObtainData.getData());
        info.put("requestPhoneNum", basicObject.getAccount());
        info.put("mark", basicObject.getMark());
        try {
            preparedStatement.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
            retObj.put("error", 1);
            retObj.put("status", "failure");
            info.put("IsSuccess", "failure");
            info.put("ResultINFO", "删除好友失败");
        }
        retObj.put("error", 0);
        retObj.put("status", "success");
        info.put("IsSuccess", "success");
        info.put("ResultINFO", "删除好友成功");
        retObj.put("result", info);
        ResponseString = retObj.toString();
    }

    private void AssembleErrorReturnJson(){
        JSONObject retObj = new JSONObject();
        JSONObject info = new JSONObject();
        retObj.put("data", ObtainData.getData());
        info.put("requestPhoneNum", basicObject.getAccount());
        info.put("mark", basicObject.getMark());
        retObj.put("error", 1);
        retObj.put("status", "failure");
        info.put("IsSuccess", "failure");
        info.put("ResultINFO", "删除好友失败");
        retObj.put("result", info);
        ResponseString = retObj.toString();
    }

    private String friendAccount;
    private ResultSet resultSet;
}
