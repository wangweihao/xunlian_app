package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class UpdateOrInsertContacts extends AccessDatabase {
    public UpdateOrInsertContacts(){
        super();
    }

    public UpdateOrInsertContacts(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase(){
        System.out.println("更新或者加入新的联系人");
        return this;
    }

}
