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
public class VerifySecretSecurity extends AccessDatabase{
    public VerifySecretSecurity(){
        super();
    }

    public VerifySecretSecurity(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("验证密宝");
        setDerivedClassOtherMeber();
        sqlString = "select count(*) from UserContact where type = \"" + Type + "\"and uid = " +
                "(select uid from UserInfo where account = \"" + basicObject.getAccount() + "\") and " +
                "content = \"" + Verify + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlString);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        ConstructSelfInfo(resultSet.getInt(1));
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        Type = jsonObject.getInt("type");
        Verify = new String(jsonObject.getString("verify"));
    }

    @Override
    public void ConstructSelfInfo(int SuccessOrFailure){
        if(SuccessOrFailure == 1){
            ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"帐号验证成功\"}}";
        }else{
            ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"2015-08\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"failure\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"帐号验证失败\"}}";
        }
    }

    private int Type;
    private String Verify;
}
