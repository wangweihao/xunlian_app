package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * Created by wwh on 15-11-25.
 */
public class SetAddFriendQuestion extends AccessDatabase {
    public SetAddFriendQuestion(){
        super();
    }
    public SetAddFriendQuestion(int _mark, String _account) {
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        setDerivedClassOtherMeber();
        updateAddQuestion();
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        JSONObject requestJson = new JSONObject(RequestString);
        addQuestion = requestJson.getString("addquestion");
        addAnswer = requestJson.getString("addanswer");
    }

    public void updateAddQuestion(){
        String updateAddFriendQuestionSql = "update UserInfo set addquestion = \"" + addQuestion +
                "\", addanswer = \"" + addAnswer + "\" where account = \"" + basicObject.getAccount() + "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(updateAddFriendQuestionSql);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return;
        }
        buildReturnValue(1, "success", "success", "设置添加好友问题成功");
    }

    private String addQuestion;
    private String addAnswer;
}
