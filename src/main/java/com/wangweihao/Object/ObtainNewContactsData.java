package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class ObtainNewContactsData extends AccessDatabase {
    public ObtainNewContactsData() {
        basicObject = new RecvBasicMessageObject();
    }
    public ObtainNewContactsData(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public RecvBasicMessageObject AccessXlDatabase() {
        System.out.println("获得刷新后的联系人数据");
        return basicObject;
    }

}
