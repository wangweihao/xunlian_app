package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.ContactType.Contact;
import com.wangweihao.HelpClass.ContactType;
import com.wangweihao.HelpClass.ObtainData;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public AccessDatabase AccessXlDatabase() throws SQLException {
        System.out.println("获得刷新后的联系人数据");
        packUpdateFriend();
        return this;
    }

    private int getUserAccountId() throws SQLException {
        String sqlGetUserAccountId = "select uid from UserInfo where account = \'" +
                basicObject.getAccount() + "\';";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetUserAccountId);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getInt(1);
    }

    private void getFriendId() throws SQLException {
        friendId = new ArrayList<Integer>();
        userId = getUserAccountId();
        String sqlGetFriendId = "select friendId from UserFriend where uid = \"" + userId  + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetFriendId);
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            friendId.add(resultSet.getInt(1));
        }
    }

    private void getIsUpdateFriend() throws SQLException {
        isUpdateFriend = new HashMap<Integer, Integer>();
        getFriendId();
        for (Integer Id : friendId){
            String sqlGetFriendIsUpdate = "select isUpdate from UserFriend where uid = \"" + Id + "\" and isUpdate != 0 " +
                    "and friendId = \"" + userId + "\";";
            preparedStatement = DBPoolConnection.prepareStatement(sqlGetFriendIsUpdate);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                if(resultSet.getInt(1) != 0){
                    isUpdateFriend.put(Id, resultSet.getInt(1));
                }
            }
        }
    }

    private void packUpdateFriend() throws SQLException {
        getIsUpdateFriend();
        if (isUpdateFriend.isEmpty()){
            ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"无好友更新信息\"}}";
        }else {
            JSONObject jsonResponse = new JSONObject();
            JSONArray jsonFriend = new JSONArray();
            jsonResponse.put("error", 0);
            jsonResponse.put("status", "success");
            jsonResponse.put("date", ObtainData.getData());
            for (Integer friendId : isUpdateFriend.keySet()){
                JSONObject oneFriend = new JSONObject();
                String sqlGetNameAndHead = "select name, head from UserInfo where uid = \"" + friendId + "\";";
                preparedStatement = DBPoolConnection.prepareStatement(sqlGetNameAndHead);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                oneFriend.put("name", resultSet.getString(1));
                oneFriend.put("head", resultSet.getBytes(2));
                isUpdateFriend.get(friendId);
                for (Integer type : ContactType.ContactType){
                    Integer isUpdate = isUpdateFriend.get(friendId);
                    Integer saveType = type;
                    type &= isUpdate;
                    if (type == 0){
                        String sqlObtainFriendUpdateContact = "select content from UserContact where uid = \"" + friendId
                                + "\" and type = " + type + ";";
                        preparedStatement = DBPoolConnection.prepareStatement(sqlObtainFriendUpdateContact);
                        resultSet = preparedStatement.executeQuery();
                        resultSet.next();
                        String contactType = ContactType.ContactMap.get(saveType);
                        oneFriend.put(contactType, resultSet.getString(1));
                    }

                }
                jsonFriend.put(oneFriend);
            }
            jsonResponse.put("result", jsonFriend);
            ResponseString = jsonResponse.toString();
        }
    }

    private ResultSet resultSet;
    private List<Integer> friendId;
    private HashMap<Integer, Integer> isUpdateFriend;
    private int userId;
}
