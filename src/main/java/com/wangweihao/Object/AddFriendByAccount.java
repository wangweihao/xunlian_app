package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class AddFriendByAccount extends AccessDatabase {
    public AddFriendByAccount() {
    }
    public AddFriendByAccount(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public String AccessXlDatabase() {
        System.out.println("通过帐号添加好友");
        return null;
    }

    private RecvBasicMessageObject basicObject;
}

