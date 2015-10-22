package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * Created by wwh on 15-10-21.
 */
public class ModifySecret extends AccessDatabase{
    public ModifySecret(){
        super();
    }

    public ModifySecret(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("修改密码");
        setDerivedClassOtherMeber();
        sqlString = "update UserInfo set password = \"" + Secret + "\"where account = \"" + basicObject.getAccount()
                + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlString);
        ConstructSelfInfo(preparedStatement.executeUpdate());
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        Secret = new String(jsonObject.getString("secret"));
    }

    public void ConstructSelfInfo(int SuccessOrFailure){
        if(ResponseString == null) {
            if (SuccessOrFailure == 1) {
                ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                        "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                        "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"修改密码成功\"}}";
            } else {
                ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                        "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"failure\"," +
                        "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"修改密码失败\"}}";
            }
        }
    }

    private String Secret;
}