package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 16-1-18.
 */

/**
 * 检测用户是否存在，若存在则返回该用户的密宝问题
 * 返回客户端 Info 三种值
 * 1.该用户不存在，返回 info 用户不存在
 * 2.该用户存在，验证好友问题不存在，返回 info 无添加好友问题，可直接添加
 * 3.该用户存在，验证好友问题存在，返回 info 添加好友问题
* */
public class CheckUserIsExit  extends AccessDatabase {
    public CheckUserIsExit(){
        super();
    }
    public CheckUserIsExit(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        setDerivedClassOtherMeber();
        checkUserIsExit();
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        friendAccount = jsonObject.getString("friendaccount");
    }

    public void checkUserIsExit() throws SQLException{
        String checkUserIsExitSql = "select addquestion from UserInfo where account = \"" +
                friendAccount + "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(checkUserIsExitSql);
            resultSet = preparedStatement.executeQuery();
            isHaveData = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return;
        }
        if(isHaveData == false){
            buildReturnValue(0, "success", "failure", "该用户不存在");
            return;
        }
        String addquestion = resultSet.getString(1);
        if(addquestion.equals("")){
            buildReturnValue(0, "success", "success", "无添加好友问题，可直接添加");
            return;
        }else{
            buildReturnValue(0, "success", "success", addquestion);
        }
        return;
    }

    private String friendAccount;
    private ResultSet resultSet;
    private boolean isHaveData;
}
