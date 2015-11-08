package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 15-10-21.
 */
public class UserLogIn extends AccessDatabase {
    public UserLogIn() {
        super();
    }
    public UserLogIn(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("用户登录");
        setDerivedClassOtherMeber();
        UserLogIn();
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        secret = new String(jsonObject.getString("secret"));
    }

    private void UserLogIn() throws SQLException {
        String sqlUserLogIn = "select count(*) from UserInfo where account = \'" + basicObject.getAccount()
                + "\'and password = \'" + secret + "\';";
        preparedStatement = DBPoolConnection.prepareStatement(sqlUserLogIn);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        JSONObject retInfo = new JSONObject();
        JSONObject result = new JSONObject();
        retInfo.put("date", ObtainData.getData());
        result.put("requestPhoneNumber", basicObject.getAccount());
        if(resultSet.getInt(1) == 1){
            retInfo.put("error", 0);
            retInfo.put("status", "success");
            result.put("ResultINFO", "登录成功");
            result.put("IsSuccess", "success");
        }else {
            retInfo.put("error", 1);
            retInfo.put("status", "failure");
            result.put("ResultINFO", "登录失败");
            result.put("IsSuccess", "failure");
        }
        retInfo.put("result", result);
        ResponseString = retInfo.toString();
    }


    private String secret;
    private ResultSet resultSet;
}