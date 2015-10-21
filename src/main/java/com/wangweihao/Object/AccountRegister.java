package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class AccountRegister extends AccessDatabase {
    public AccountRegister(){}
    public AccountRegister(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public String AccessXlDatabase(){
        System.out.println("帐号注册");
        return null;
    }

    private RecvBasicMessageObject basicObject;
}
