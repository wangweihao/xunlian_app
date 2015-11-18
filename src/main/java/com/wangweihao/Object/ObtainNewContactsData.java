package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
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
        try{
            System.out.println("获得刷新后的联系人数据");
            packUpdateFriend();
        }catch (SQLException e){
            JSONObject jsonResponse = new JSONObject();
            JSONObject jsonInfo = new JSONObject();
            jsonResponse.put("error", 0);
            jsonResponse.put("status", "success");
            jsonResponse.put("date", ObtainData.getData());
            jsonInfo.put("requestPhoneNum", basicObject.getAccount());
            jsonInfo.put("IsSuccess", "success");
            jsonInfo.put("mark", 6);
            jsonInfo.put("ResultInfo", "暂无好友更新数据");
            jsonResponse.put("result", jsonInfo);
            ResponseString = jsonResponse.toString();
        }
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


    private void getFriendIdAndIsUpdate() throws SQLException {
        try{
            FriendIdAndIsUpdate = new HashMap<Integer, Integer>();
            UserId = getUserAccountId();
            String sqlGetFriendIdAndIsUpdate = "select friendId, isUpdate from UserFriend where uid = \"" + UserId + "\" and" +
                    " isUpdate != 0;";
            preparedStatement = DBPoolConnection.prepareStatement(sqlGetFriendIdAndIsUpdate);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                FriendIdAndIsUpdate.put(resultSet.getInt(1), resultSet.getInt(2));
            }
        }catch (SQLException e){
            throw e;
        }
    }

    private JSONObject getNameAndHead(int friendId) throws SQLException {
        JSONObject oneFriend = new JSONObject();
        String sqlGetNameAndHead = "select name, head from UserInfo where uid = \"" + friendId + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlGetNameAndHead);
        resultSet = preparedStatement.executeQuery();
        resultSet.next();
        oneFriend.put("name", resultSet.getString(1));
        oneFriend.put("head", resultSet.getBytes(2));
        return oneFriend;
    }

    private void modifyFriendIsUpdate(int userId, int friendId) throws SQLException {
        String sqlmodifyFriendIsUpdate = "update UserFriend set isUpdate = 0 where uid = \"" + userId + "\" " +
                "and friendId = \"" + friendId + "\";";
        preparedStatement = DBPoolConnection.prepareStatement(sqlmodifyFriendIsUpdate);
        preparedStatement.executeUpdate();
    }

    private void packUpdateFriend() throws SQLException {
        getFriendIdAndIsUpdate();
        if (FriendIdAndIsUpdate.isEmpty()){
            ResponseString = "{\"error\":0, \"status\":\"success\", \"date\":\"" + ObtainData.getData() + "\", " +
                    "\"result\":{\"requestPhoneNum\":\"" + basicObject.getAccount() + "\", \"IsSuccess\":\"success\"," +
                    "\"mark\":" + basicObject.getMark() + ",\"ResultINFO\":\"无好友更新信息\"}}";
        }else {
            JSONObject jsonResponse = new JSONObject();
            JSONArray jsonFriend = new JSONArray();
            jsonResponse.put("error", 0);
            jsonResponse.put("status", "success");
            jsonResponse.put("date", ObtainData.getData());
            for (Integer friendId : FriendIdAndIsUpdate.keySet()){
                JSONObject oneFriend = getNameAndHead(friendId);
                for (Integer type : ContactType.ContactType){
                    Integer isUpdate = FriendIdAndIsUpdate.get(friendId);
                    Integer saveType = type;
                    /*
                       与运算，如果IsUpdate相应的位为1，
                       那么saveType值不变，否则saveType值变为0。
                       */
                    saveType &= isUpdate;
                    if (saveType != 0){
                        String sqlObtainFriendUpdateContact = "select content from UserContact where uid = \"" + friendId
                                + "\" and type = " + type + ";";
                        preparedStatement = DBPoolConnection.prepareStatement(sqlObtainFriendUpdateContact);
                        resultSet = preparedStatement.executeQuery();
                        resultSet.next();
                        String contactType = ContactType.ContactMap.get(type);
                        oneFriend.put(contactType, resultSet.getString(1));
                    }

                }
                jsonFriend.put(oneFriend);
                modifyFriendIsUpdate(UserId, friendId);
            }
            jsonResponse.put("result", jsonFriend);
            ResponseString = jsonResponse.toString();

        }
    }

    private ResultSet resultSet;
    private HashMap<Integer, Integer> FriendIdAndIsUpdate;
    private int UserId;
}
