package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class DeleteFriend extends AccessDatabase {
    public DeleteFriend() {
    }
    public DeleteFriend(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public String AccessXlDatabase() {
        System.out.println("删除好友");
        return null;
    }

    private RecvBasicMessageObject basicObject;
}
