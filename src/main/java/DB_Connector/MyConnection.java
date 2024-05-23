package DB_Connector;

import java.sql.*;

public class MyConnection {
    public static Connection connection = null;

    public  static  Connection getConnection(){
        String url = "jdbc:mysql:///database name";
        final String userName = "root";
        final String password = "pasword";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,userName,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static void closeConnection(){
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
