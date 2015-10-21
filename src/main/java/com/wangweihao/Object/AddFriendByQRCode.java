package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */

public class AddFriendByQRCode extends AccessDatabase {
    public AddFriendByQRCode() {
    }
    public AddFriendByQRCode(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public RecvBasicMessageObject AccessXlDatabase() {
        System.out.println("通过二维码添加好友");
        return null;
    }

}