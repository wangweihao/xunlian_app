package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * Created by wwh on 15-10-21.
 */
public class AccountRegister extends AccessDatabase {
    public AccountRegister(){
        super();
    }
    public AccountRegister(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("---------------------------------");
        System.out.println("帐号注册");
        System.out.println("---------------------------------");
        setDerivedClassOtherMeber();
        switch(flag){
            case 1:
                accountRegister();
                break;
            case 2:
                modifyQuestionBySecret();
                break;
            case 3:
                modifySecretByQuestion();
                break;
            default:
                break;
        }

        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        flag = jsonObject.getInt("flag");
        secret = new String(jsonObject.getString("secret"));
        question = new String(jsonObject.getString("question"));
        answer = new String(jsonObject.getString("answer"));
    }

    public void accountRegister() throws SQLException {
        String accountRegisterSql = "insert into UserInfo (account, password, question, answer) values (\"" +
                basicObject.getAccount() + "\", \"" + secret + "\", \"" + question + "\", \"" + answer + "\");";
        preparedStatement = DBPoolConnection.prepareStatement(accountRegisterSql);
        try {
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统有误，请稍后再试");
        }
        buildReturnValue(0, "success", "success", "帐号注册成功");
    }

    public void modifyQuestionBySecret(){

    }

    public void modifySecretByQuestion(){

    }

    private void buildReturnValue(int error, String status, String isSuccess, String resultInfo){
        JSONObject retInfo = new JSONObject();
        JSONObject Info = new JSONObject();

        retInfo.put("error", error);
        retInfo.put("status", status);
        retInfo.put("date", ObtainData.getData());
        Info.put("requestPhoneNum", basicObject.getAccount());
        Info.put("IsSuccess", isSuccess);
        Info.put("mark", basicObject.getMark());
        Info.put("ResultINFO", resultInfo);
        retInfo.put("result", Info);

        ResponseString = retInfo.toString();
    }

    private int flag;
    private String secret;
    private String question;
    private String answer;
}
