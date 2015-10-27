package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ContactType;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONObject;

import javax.rmi.CORBA.Tie;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by wwh on 15-10-21.
 */

public class AddFriendByQRCode extends AccessDatabase {
    public AddFriendByQRCode() {
        super();
    }
    public AddFriendByQRCode(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException, ParseException {
        System.out.println("通过二维码添加好友");
        setDerivedClassOtherMeber();
        if(QRcodeIsExpired() == true){
            addFriendByQRcode();
        }else {
            deleteQRcode();
        }
        return this;
    }

    @Override
    public void setDerivedClassOtherMeber(){
        jsonObject = new JSONObject(RequestString);
        friendAccount = new String(jsonObject.getString("friendaccount"));
        qid = jsonObject.getInt("qid");
    }

    public boolean QRcodeIsExpired() throws SQLException, ParseException {
        String sqlQRcodeIsExpired = "select expir_time,authority, time_out from QRcode where qid = " + qid
                + ";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlQRcodeIsExpired);
        resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            expireTime = resultSet.getString(1);
            timeOut = resultSet.getLong(2);
            authority = resultSet.getInt(3);
        }
        System.out.println("OldTime:" + expireTime);
        System.out.println("CurrentTime:" + ObtainData.getData());
        ResponseString = "hello world";
        if(calculateTimeDifference(expireTime, ObtainData.getData())){
            /* 没过期，添加好友，返回好友信息，带权限 */
            System.out.println("没过期");
            return true;
        }else {
            /* 过期，二维码无效，返回错误并删除数据库中二维码 */
            System.out.println("过期");
            return false;
        }
    }

    boolean calculateTimeDifference(String oldTime, String newTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long TimeOut = timeOut * 24 * 60 * 60 * 1000;
        System.out.println("TimeOut:" + TimeOut);
        Long timeDirr = dateFormat.parse(oldTime).getTime() + TimeOut - dateFormat.parse(newTime).getTime();
        System.out.println(timeDirr);
        if(timeDirr > 0)
            return true;
        else
            return false;
    }

    /* 通过二维码添加好友，UserFriend权限位， */
    private void addFriendByQRcode() throws SQLException {
        String sqlAddFriendByQRcode = "insert into UserFriend (uid, friendId, authority) values (\""
                + getUserAccountId(basicObject.getAccount()) + "\", \"" + getUserAccountId(friendAccount) +
                "\", " + authority + ");";
        preparedStatement = DBPoolConnection.prepareStatement(sqlAddFriendByQRcode);
        try {
            preparedStatement.executeUpdate();
            retFriendInfo();
        }catch (Exception e){
            e.printStackTrace();
            JSONObject errObj = new JSONObject();
            errObj.put("error", 1);
            errObj.put("status", "failure");
            errObj.put("account", basicObject.getAccount());
            errObj.put("resultInfo", "对方以是您的好友");
            ResponseString = errObj.toString();
        }
    }

    private void retFriendInfo() throws SQLException {
        String sqlGetFriendInfo = "select type, content from UserContact where uid = "
                + userUid + ";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetFriendInfo);
        resultSet = preparedStatement.executeQuery();
        HashMap<Integer, String> friendContact = new HashMap<Integer, String>();
        while (resultSet.next()){
            friendContact.put(resultSet.getInt(1), resultSet.getString(2));
        }
        JSONObject retSponse = new JSONObject();
        JSONObject Contact = new JSONObject();
        retSponse.put("error", 0);
        retSponse.put("status", "success");
        retSponse.put("account", basicObject.getAccount());
        for(Integer Type : friendContact.keySet()){
            int saveType = Type;
            saveType &= authority;
            if (saveType != 0){
                Contact.put(ContactType.ContactMap.get(Type), friendContact.get(Type));
            }
        }
        retSponse.put("result", Contact);
        ResponseString = retSponse.toString();
    }

    private void deleteQRcode() throws SQLException {
        String sqlDeleteQRcode = "delete from QRcode where qid = " + qid + ";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlDeleteQRcode);
        preparedStatement.executeUpdate();
    }

    private int getUserAccountId(String account) throws SQLException {
        String sqlGetUserAccountId = "select uid from UserInfo where account = \'" +
                account + "\';";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetUserAccountId);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        userUid = resultSet.getInt(1);
        return userUid;
    }
    private String friendAccount;
    private int authority;
    private int qid;
    private ResultSet resultSet;
    private String expireTime;
    private Long timeOut;
    private ResultObject resultObject;
    private int userUid;
}