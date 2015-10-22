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
        System.out.println("帐号注册");
        setDerivedClassOtherMeber();
        sqlString = "insert into UserInfo (account, password) values (\"" +
                basicObject.getAccount() + "\",\"" + Secret + "\");";
        preparedStatement = DBPoolConnection.prepareStatement(sqlString);
        try {
            ConstructSelfInfo(preparedStatement.executeUpdate());
        }catch (Exception e){
            /* 帐号已存在 */
            ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"failure\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"帐号以存在\"}}";
        }

        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        Secret = new String(jsonObject.getString("secret"));
    }

    @Override
    public void ConstructSelfInfo(int SuccessOrFailure){
        if(ResponseString == null) {
            if (SuccessOrFailure == 1) {
                ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                        "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                        "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"注册成功\"}}";
            } else {
                ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"2015-08\", " +
                        "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"failure\"," +
                        "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"注册失败\"}}";
            }
        }
    }

    private String Secret;
}
