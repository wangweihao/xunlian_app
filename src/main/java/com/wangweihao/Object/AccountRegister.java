package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

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
    public RecvBasicMessageObject AccessXlDatabase() throws SQLException {
        System.out.println("帐号注册");
        sqlString = "select count(*) from UserInfo where account = \"" +
                basicObject.getAccount() + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlString);
        ResultSet resultset = preparedStatement.executeQuery();
        resultset.next();
        int result = resultset.getInt(1);
        if(result == 1){
            System.out.println("该帐号存在");
        }else {
            System.out.println("该帐号不存在");
        }
        return basicObject;
    }


}
