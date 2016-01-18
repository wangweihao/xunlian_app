package com.wangweihao.Object;

import sun.security.pkcs11.P11Util;

import java.util.HashMap;

/**
 * Created by wwh on 15-10-21.
 */
public class MarkToClassObjectMap {
    /* 第一层括号为匿名内部类，第二层括号为实例初始化块 */
    public MarkToClassObjectMap() {
        markToClass = new HashMap<Integer, String>() {
            {
                put(1, "com.wangweihao.Object.DetectAccountRegister");
                put(2, "com.wangweihao.Object.AccountRegister");
                put(3, "com.wangweihao.Object.ObtainQuestion");
                put(4, "com.wangweihao.Object.ModifySecret");
                put(5, "com.wangweihao.Object.ModifyData");
                put(6, "com.wangweihao.Object.UpdateContact");
                put(7, "com.wangweihao.Object.ObtainAllContactsData");
                put(8, "com.wangweihao.Object.ObtainNewContactsData");
//                put(9, "com.wangweihao.Object.AddFriendSearchByAccount");
                put(9, "com.wangweihao.Object.CheckUserIsExit");
                put(10, "com.wangweihao.Object.AddFriendByQRCode");
                put(11, "com.wangweihao.Object.CreateQRCode");
                put(12, "com.wangweihao.Object.UserLogIn");
                put(13, "com.wangweihao.Object.DeleteFriend");
                put(14, "com.wangweihao.Object.SaveUserInfo");
                put(15, "com.wangweihao.Object.AddFriendByAccount");
                put(16, "com.wangweihao.Object.SetAddFriendQuestion");
                put(17, "com.wangweihao.Object.CheckAddFriendQuestion");
            }
        };
    }
    public String Mapping(int _mark){
        return markToClass.get(_mark);
    }

    HashMap<Integer, String> markToClass;
}
