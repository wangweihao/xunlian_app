/**
 * Created by wwh on 15-10-18.
 */
public class DatabaseUserInfo {
    private String UserAccount;
    private String UserPassword;
    private String UserIp;
    private String UserDatabaseName;

    DatabaseUserInfo(String Account, String Password, String Ip, String databaseName){
        UserAccount = Account;
        UserPassword = Password;
        UserIp = Ip;
        UserDatabaseName = databaseName;
    }

    void replaceUserInfo(String Account, String Password, String Ip){
        UserAccount = Account;
        UserPassword = Password;
        UserIp = Ip;
    }

    String getUserAccount(){
        return UserAccount;
    }

    String getUserPassword(){
        return UserPassword;
    }

    String getUserIp(){
        return UserIp;
    }

    String getUserDatabaseName(){
        return UserDatabaseName;
    }
}
