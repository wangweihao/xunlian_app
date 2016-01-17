package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wwh on 15-10-21.
 */
public class DetectAccountRegister extends AccessDatabase{
    public DetectAccountRegister() {
        super();
    }
    public DetectAccountRegister(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("检测帐号是否存在");
        setDerivedClassOtherMeber();
        sqlString = "select count(*) from UserInfo where account = \"" +
                basicObject.getAccount() + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlString);
        ResultSet resultset = preparedStatement.executeQuery();
        resultset.next();
        ConstructSelfInfo(resultset.getInt(1));
        return this;
    }

    @Override
    public void ConstructSelfInfo(int SuccessOrFailure){
        if(SuccessOrFailure == 1){
            ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"该帐号已存在\"}}";
        }else{
            ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"failure\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"该帐号不存在\"}}";
        }
    }
}
