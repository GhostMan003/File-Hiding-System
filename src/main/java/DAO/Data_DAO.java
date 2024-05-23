package DAO;

import DB_Connector.MyConnection;
import Model.Data_Info;
import Model.User_Info;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class Data_DAO {
    public static ArrayList<Data_Info> getAllFiles(String email) throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM DATA WHERE EMAIL = ?");
        statement.setString(1,email);
        ResultSet result = statement.executeQuery();
        ArrayList<Data_Info> files = new ArrayList<>();
        while (result.next()) {
            int id = result.getInt(1);
            String name = result.getString(2);
            String path = result.getString(3);
            files.add(new Data_Info(id,name,path));
        }
        return files;
    }
    public static boolean isPathExists(String path,String email) throws SQLException {
        User_Info user = new User_Info(email);
        Connection connection  = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT PATH FROM DATA WHERE EMAIL = ?");
        statement.setString(1, user.getEmail());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            String str = result.getString(1);
            if(str.equals(path)) return true;
        }
        return false;
    }

    public static int hideFile(Data_Info file) throws SQLException, IOException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO DATA(FILE_NAME, PATH, EMAIL, BIN_DATA) VALUES (?, ?, ?, ?)"
        );
        statement.setString(1, file.getFilename());
        statement.setString(2, file.getPath());
        statement.setString(3, file.getEmail());
        File fill = new File(file.getPath());
        if (fill.exists()) {
            FileInputStream input = new FileInputStream(file.getPath());
            byte[] byteFile = new byte[input.available()];
            input.read(byteFile);
            input.close();
            statement.setBytes(4,byteFile);
            int equary = statement.executeUpdate();

            fill.delete();
            return equary;
        }
//        if (fill.exists()) {
//            FileReader file_read = new FileReader(fill);
//            statement.setCharacterStream(4,file_read,fill.length());
//            int equary = statement.executeUpdate();
//            file_read.close();
//            fill.delete();
//            return equary;
//        }
        return -1;
    }

    public static void unHide(int id) throws SQLException,IOException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(
                "SELECT PATH, BIN_DATA FROM DATA WHERE ID = ?"
        );
        statement.setInt(1,id);
        ResultSet result = statement.executeQuery();
        result.next();
        String path = result.getString("PATH");
        Clob col_data = result.getClob("BIN_DATA");

        Reader reader = col_data.getCharacterStream();
        FileWriter filewrite = new FileWriter(path);
        int i;
        while ((i = reader.read()) != -1) {
            filewrite.write((char) i);
        }
        filewrite.close();
        statement = connection.prepareStatement("DELETE FROM DATA WHERE ID = ?");
        statement.setInt(1,id);
        statement.executeUpdate();
//        System.out.println("Successfully remove the file");
    }
}
