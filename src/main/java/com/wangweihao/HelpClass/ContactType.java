package com.wangweihao.HelpClass;

import com.wangweihao.ContactType.MailContact;
import sun.awt.util.IdentityArrayList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wwh on 15-10-26.
 */
public class ContactType {
    public static final int phoneContactPerson = 0x1;
    public static final int phoneContactHome = 0x2;
    public static final int phoneContactWork = 0x4;
    public static final int mailContactPerson = 0x8;
    public static final int mailContactHome = 0x10;
    public static final int mailContactWork = 0x20;
    public static final int qqContact = 0x40;
    public static final int weiboContact = 0x80;
    public static ArrayList<Integer> ContactType;
    public static Map<Integer, String> ContactMap;
    static {
        ContactType = new ArrayList<Integer>();
        ContactType.add(0x1);
        ContactType.add(0x2);
        ContactType.add(0x4);
        ContactType.add(0x8);
        ContactType.add(0x10);
        ContactType.add(0x20);
        ContactType.add(0x40);
        ContactType.add(0x80);
        ContactMap = new HashMap<Integer, String>();
        ContactMap.put(0x1, "personNumber");
        ContactMap.put(0x2, "homeNumer");
        ContactMap.put(0x4, "workNumber");
        ContactMap.put(0x8, "personEmail");
        ContactMap.put(0x10, "homeEmail");
        ContactMap.put(0x20, "workEmail");
        ContactMap.put(0x40, "qqNumber");
        ContactMap.put(0x80, "weiboNumber");
    }


    public static void main(String[] args){
        System.out.println(Integer.toBinaryString(phoneContactPerson));
        System.out.println(Integer.toBinaryString(phoneContactHome));
        System.out.println(Integer.toBinaryString(phoneContactWork));
        System.out.println(Integer.toBinaryString(mailContactPerson));
        System.out.println(Integer.toBinaryString(mailContactHome));
        System.out.println(Integer.toBinaryString(mailContactWork));
        System.out.println(Integer.toBinaryString(qqContact));
        System.out.println(Integer.toBinaryString(weiboContact));
        System.out.println(Integer.toBinaryString(phoneContactPerson | phoneContactHome | phoneContactWork | mailContactHome | mailContactPerson |
        mailContactWork | qqContact | weiboContact));
    }
}
