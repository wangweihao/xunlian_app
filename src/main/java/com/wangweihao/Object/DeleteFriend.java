package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class DeleteFriend extends AccessDatabase {
    public DeleteFriend() {
        super();
    }
    public DeleteFriend(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public RecvBasicMessageObject AccessXlDatabase() {
        System.out.println("删除好友");
        return basicObject;
    }

}
