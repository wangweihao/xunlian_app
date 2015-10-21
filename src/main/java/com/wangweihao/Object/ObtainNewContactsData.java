package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class ObtainNewContactsData extends AccessDatabase {
    public ObtainNewContactsData() {
    }
    public ObtainNewContactsData(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public String AccessXlDatabase() {
        System.out.println("获得刷新后的联系人数据");
        return null;
    }

    private RecvBasicMessageObject basicObject;
}
