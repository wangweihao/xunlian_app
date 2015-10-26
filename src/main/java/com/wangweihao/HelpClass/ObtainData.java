package com.wangweihao.HelpClass;

import com.wangweihao.ContactType.Contact;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wwh on 15-10-22.
 */
public class ObtainData {
    private static Date date;
    private static DateFormat format;
    static {
        date = new Date();
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    public static String getData(){
        return format.format(date);
    }

    public static void main(String[] args){
        System.out.println(getData());
    }
}
