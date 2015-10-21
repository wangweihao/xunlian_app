package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class UserLogIn extends AccessDatabase {
    public UserLogIn() {
        basicObject = new RecvBasicMessageObject();
    }
    public UserLogIn(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public RecvBasicMessageObject AccessXlDatabase() {
        System.out.println("用户登录");
        return basicObject;
    }

}