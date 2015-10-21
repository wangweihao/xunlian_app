package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class ObtainAllContactsData extends AccessDatabase {
    public ObtainAllContactsData() {
        super();
    }
    public ObtainAllContactsData(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public RecvBasicMessageObject AccessXlDatabase() {
        System.out.println("获得所有的联系人数据");
        return basicObject;
    }

}
