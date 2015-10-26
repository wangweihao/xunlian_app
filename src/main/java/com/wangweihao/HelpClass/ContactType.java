package com.wangweihao.HelpClass;

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
