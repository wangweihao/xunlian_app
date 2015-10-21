package com.wangweihao.AccessDatabase;

import com.wangweihao.Object.RecvBasicMessageObject;

/**
 * Created by wwh on 15-10-21.
 */
public class AccessDatabase {
    public AccessDatabase(){
        basicObject = new RecvBasicMessageObject();
        sqlString = new String();
    }
    public AccessDatabase(int _mark, String _account){
        basicObject = new RecvBasicMessageObject(_mark, _account);
        sqlString = new String();
    }

    public RecvBasicMessageObject AccessXlDatabase(){
        return null;
    }

    public void ConstructObject(){

    }

    public RecvBasicMessageObject basicObject;
    public String sqlString;
}
