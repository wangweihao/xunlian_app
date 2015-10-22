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
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("帐号注册");

        return this;
    }


}
