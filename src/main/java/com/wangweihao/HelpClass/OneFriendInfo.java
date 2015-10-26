package com.wangweihao.HelpClass;

import com.wangweihao.ContactType.*;

import java.util.HashMap;
import java.util.TreeSet;

/**
 * Created by wwh on 15-10-26.
 */
public class OneFriendInfo {
    private String name;
    private byte[] head;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setHead(byte[] head) {
        this.head = head;
    }

    public byte[] getHead() {
        return head;
    }

    public HashMap<Integer, Contact> contactSet;
    public OneFriendInfo(){
        contactSet = new HashMap<Integer, Contact>();
        contactSet.put(1, new PhoneContactPerson(1, ""));
        contactSet.put(2, new PhoneContactHome(2, ""));
        contactSet.put(4, new PhoneContactWork(4, ""));
        contactSet.put(8, new MailContactPerson(8, ""));
        contactSet.put(16, new MailContactHome(16, ""));
        contactSet.put(32, new MailContactWork(32, ""));
        contactSet.put(64, new QQContact(64, ""));
        contactSet.put(128, new WeiboContact(128, ""));
    }
}
