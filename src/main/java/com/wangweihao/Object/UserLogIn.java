package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class UserLogIn extends AccessDatabase {
    public UserLogIn() {
        super();
    }
    public UserLogIn(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() {
        System.out.println("用户登录");
        return this;
    }

}