package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;

/**
 * Created by wwh on 15-10-21.
 */
public class ModifyData extends AccessDatabase {
    public ModifyData(){
        basicObject = new RecvBasicMessageObject();
    }

    public ModifyData(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
    }

    @Override
    public RecvBasicMessageObject AccessXlDatabase(){
        System.out.println("修改资料");
        return basicObject;
    }


}
