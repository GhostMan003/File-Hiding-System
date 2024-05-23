package DAO;

import DB_Connector.MyConnection;
import Model.User_Info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User_DAO {
    public static boolean isExists(String email) throws SQLException {
        Connection connection  = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT EMAIL FROM USERS");
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            String str = result.getString(1);
            if(str.equals(email)) return true;
        }
        return false;
    }


    public static int saveUser(User_Info user) throws SQLException{
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO USERS VALUES (default, ?, ?)");
        statement.setString(1,user.getName());
        statement.setString(2, user.getEmail());
        return statement.executeUpdate();
    }
}
