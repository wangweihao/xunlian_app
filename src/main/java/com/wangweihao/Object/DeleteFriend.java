package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

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
        deleteFriend();
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        friendAccount = new String(jsonObject.getString("friendaccount"));
    }

    private void deleteFriend() throws SQLException {
        String sqlDeleteFriend =  "delete from UserFriend where " +
                "friendId = ( select uid from UserInfo where account = \'" + friendAccount
                + "\') and (select uid from UserInfo where account = \'"  + basicObject.getAccount() + "\');";
        preparedStatement = DBPoolConnection.prepareStatement(sqlDeleteFriend);
        JSONObject retInfo = new JSONObject();
        JSONObject result = new JSONObject();
        retInfo.put("data", ObtainData.getData());
        result.put("requestPhoneNum", basicObject.getAccount());
        try {
            preparedStatement.executeUpdate();
            retInfo.put("error", 0);
            retInfo.put("status", "success");
            result.put("ResultINFO", "删除好友成功");
        }catch (SQLException e){
            e.printStackTrace();
            retInfo.put("error", 1);
            retInfo.put("status", "failure");
            result.put("ResultINFO", "删除好友失败");
        }
        retInfo.put("result", result);
        ResponseString = retInfo.toString();
    }

    private String friendAccount;
}
