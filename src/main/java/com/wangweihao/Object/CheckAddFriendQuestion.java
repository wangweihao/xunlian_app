package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 16-1-18.
 */
public class CheckAddFriendQuestion extends AccessDatabase {
    public CheckAddFriendQuestion(){
        super();
    }
    public CheckAddFriendQuestion(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("---------------------------------");
        System.out.println("检验添加好友问题");
        System.out.println("---------------------------------");
        setDerivedClassOtherMeber();
        checkAddFriendQuestion();
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        friendAccount = jsonObject.getString("friendaccount");
        answer = jsonObject.getString("answer");
    }

    public void checkAddFriendQuestion() throws SQLException {
        String checkAddFriendQuestionSql = "select addanswer from UserInfo where account = \"" +
                friendAccount + "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(checkAddFriendQuestionSql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return;
        }
        String getAnswer = resultSet.getString(1);
        if(getAnswer.equals(answer)){
            buildReturnValue(1, "success", "success", "yes");
        }else{
            buildReturnValue(1, "success", "success", "no");
        }
    }

    private String friendAccount;
    private String answer;
    private ResultSet resultSet;
}
