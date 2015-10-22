package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class ObtainNewContactsData extends AccessDatabase {
    public ObtainNewContactsData() {
        super();
    }
    public ObtainNewContactsData(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() {
        System.out.println("获得刷新后的联系人数据");
        return this;
    }

}
