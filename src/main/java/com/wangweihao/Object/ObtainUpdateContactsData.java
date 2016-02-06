package com.wangweihao.Object;

import com.wangweihao.AccessDatabase.AccessDatabase;
import com.wangweihao.HelpClass.ContactType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Created by wwh on 16-2-6.
 */

/*
* mark 8
* 下拉刷新操作，或许更新信息的好友数据
* */
public class ObtainUpdateContactsData extends AccessDatabase {
    public ObtainUpdateContactsData(){
        super();
    }
    public ObtainUpdateContactsData(int _mark, String _account){
        super(_mark, _account);
    }

    @Override
    public AccessDatabase AccessXlDatabase() throws SQLException {
        if(getUserUid() && getFriendIdAndIsUpdate() && getFriendUpdateContacts() && modifyFriendIsUpdate()){
            System.out.println("下拉刷新操作成功");
        }else {
            System.out.println("下拉刷新操作失败");
        }
        return this;
    }

    private boolean getUserUid() throws SQLException{
        String getUserUidSql = "select uid from UserInfo where account = \"" +
                basicObject.getAccount() + "\"";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(getUserUidSql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            userUid = resultSet.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        return true;
    }

    private boolean getFriendIdAndIsUpdate() throws SQLException{
        String getFriendIdAndIsUpdateSql = "select friendId, isUpdate from UserFriend where uid = \"" +
                userUid + "\" and isUpdate != 0;";
        friendIdAndIsUpdate = new HashMap<Integer, Integer>();
        try{
            preparedStatement = DBPoolConnection.prepareStatement(getFriendIdAndIsUpdateSql);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                friendIdAndIsUpdate.put(resultSet.getInt(1), resultSet.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        return true;
    }

    private boolean getFriendUpdateContacts(){
        if(friendIdAndIsUpdate.isEmpty()){
            buildReturnValue(0, "success", "success", "暂时没有好友更新数据~");
            return true;
        }else {
            /* 返回更新信息好友的 json 数组 */
            JSONArray result = new JSONArray();
            for(Integer friendId : friendIdAndIsUpdate.keySet()) {
                JSONObject info = new JSONObject();
                JSONArray contacts = new JSONArray();
                if(getFriendNameAndHeadAndAccount(friendId, info) == false){
                    return false;
                }
                for(Integer type : ContactType.ContactType){
                    Integer isUpdate = friendIdAndIsUpdate.get(friendId);
                    Integer saveType = type;
                    /*
                    *  与运算，如果 isUpdate 相应位为 1
                    *  则 saveType 值不变，否则 saveType 值为 0
                    * */
                    saveType &= isUpdate;
                    if(saveType != 0){
                        String getUpdateContactSql = "select content from UserContact where uid = \"" + friendId
                                + "\" and type = \"" + type + "\";";
                        JSONObject contact = new JSONObject();
                        try{
                            preparedStatement = DBPoolConnection.prepareStatement(getUpdateContactSql);
                            resultSet = preparedStatement.executeQuery();
                            resultSet.next();
                            contact.put("mark", type);
                            contact.put("contact", resultSet.getString(1));
                            contacts.put(contact);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
                            return false;
                        }
                    }
                }
                info.put("contact", contacts);
                result.put(info);
            }
            buildReturnValue(0, "success", "success", result.toString());
        }
        return true;
    }

    private boolean getFriendNameAndHeadAndAccount(int friendId, JSONObject info){
        String getFriendNameAndHeadAndAccountSql = "select account, name, head from UserInfo where uid = \"" +
                friendId + "\";";
        try{
            preparedStatement = DBPoolConnection.prepareStatement(getFriendNameAndHeadAndAccountSql);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            info.put("account", resultSet.getString(1));
            info.put("name", resultSet.getString(2));
            info.put("head", resultSet.getInt(3));
        } catch (SQLException e) {
            e.printStackTrace();
            buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
            return false;
        }
        return true;
    }

    private boolean modifyFriendIsUpdate() throws SQLException{
        for(Integer friendId : friendIdAndIsUpdate.keySet()){
            String modifyFriendIsUpdateSql = "update UserFriend set isUpdate = 0 where uid = \"" + userUid +
                    "\" and friendId = \"" + friendId + "\";";
            try{
                preparedStatement = DBPoolConnection.prepareStatement(modifyFriendIsUpdateSql);
                preparedStatement.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
                buildReturnValue(1, "success", "failure", "系统错误，请稍后再试");
                return false;
            }
        }
        return true;
    }

    private ResultSet resultSet;
    private HashMap<Integer, Integer> friendIdAndIsUpdate;
    private int userUid;
}
