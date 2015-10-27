package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import java.sql.SQLException;

/**
 * Created by wwh on 15-10-21.
 */
public class CreateQRCode extends AccessDatabase {
    public CreateQRCode() {
        super();
    }
    public CreateQRCode(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        authority = jsonObject.getInt("authority");
        timeOut = jsonObject.getInt("time_out");
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("创建二维码");
        saveQRcode();
        return this;
    }

    private void saveQRcode() throws SQLException {
        String sqlSaveQRcode =  "insert into QRcode (uid, authority, time_out) values ((" +
                "select uid from UserInfo where account = \"" + basicObject.getAccount()  + "\"), \"" + authority +
                "\", \"" + timeOut + "\")";
        JSONObject retInfo = new JSONObject();
        retInfo.put("date", ObtainData.getData());
        retInfo.put("requestPhoneNum", basicObject.getAccount());
        preparedStatement = DBPoolConnection.prepareStatement(sqlSaveQRcode);
        try{
            preparedStatement.executeUpdate();
            retInfo.put("status", "success");
            retInfo.put("error", 0);
            retInfo.put("resultINFO", "新建二维码成功");
        }catch (SQLException e){
            e.printStackTrace();
            retInfo.put("status", "failure");
            retInfo.put("error", 1);
            retInfo.put("resultINFO", "新建二维码失败，请重试");
        }
        ResponseString = retInfo.toString();
    }

    private int authority;
    private int timeOut;

}
