package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class UpdateOrInsertContacts extends AccessDatabase {
    public UpdateOrInsertContacts(){
        basicObject = new RecvBasicMessageObject();
    }

    public UpdateOrInsertContacts(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public RecvBasicMessageObject AccessXlDatabase(){
        System.out.println("更新或者加入新的联系人");
        return basicObject;
    }

}
