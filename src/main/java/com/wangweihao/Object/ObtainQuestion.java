package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import javax.lang.model.element.TypeElement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 15-10-21.
 */
public class ObtainQuestion extends AccessDatabase{
    public ObtainQuestion(){
        super();
    }

    public ObtainQuestion(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        String obtainQuestionSql = "select question from UserInfo where account = \"" + basicObject.getAccount()
                + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(obtainQuestionSql);
        try{
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            question = resultSet.getString(1);
        } catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return this;
        }
        buildReturnValue(1, "success", "success", question);
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
    }

    private ResultSet resultSet;
    private String question;
}
