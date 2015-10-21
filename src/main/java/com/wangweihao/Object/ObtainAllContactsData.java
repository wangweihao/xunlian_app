package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class ObtainAllContactsData extends AccessDatabase {
    public ObtainAllContactsData() {
    }
    public ObtainAllContactsData(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public RecvBasicMessageObject AccessXlDatabase() {
        System.out.println("获得所有的联系人数据");
        return null;
    }

    private RecvBasicMessageObject basicObject;
}
