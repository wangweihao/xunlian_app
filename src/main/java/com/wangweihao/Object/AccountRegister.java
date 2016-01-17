package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.ResultSet;
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
                getAccountUid();
                modifyQuestionBySecret();
                break;
            case 3:
                getAccountUid();
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

    public void modifyQuestionBySecret() throws SQLException{
        String getUserPasswordSql = "select password from UserInfo where account = \"" + basicObject.getAccount()
                + "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(getUserPasswordSql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "密码错误，请重试");
            return;
        }
        String UserSecret = resultSet.getString(1);
        if(UserSecret == secret){
            System.out.println("验证密码成功");
        }
        String updateQuestionSql = "update UserInfo set question = \"" + question + "\", answer = \"" +
                answer + "\" where account = \"" + basicObject.getAccount() + "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(updateQuestionSql);
            preparedStatement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return;
        }
        buildReturnValue(0, "success", "success", "更新密宝问题成功");
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

    private void getAccountUid() throws SQLException {
        String getAccountUidSql = "select uid from UserInfo where account = \"" + basicObject.getAccount()
                + "\";";
        try{
        preparedStatement = DBPoolConnection.prepareStatement(getAccountUidSql);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        }catch (SQLException e){
            e.printStackTrace();
        }
        uid = resultSet.getInt(1);
    }

    private int flag;
    private int uid;
    private String secret;
    private String question;
    private String answer;
    private ResultSet resultSet;
}
