package Home_Page;

import DB_Connector.MyConnection;
import Log_In_Page.Sign_In;
import Model.User_Info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Home_Interface extends JFrame implements ActionListener {

    private int x_loc, y_loc;
    private JLabel info_label,image_label,user_label;
    private JButton add_button,file_button,remove_button,exit_button;
    private String e_mail;
    public Home_Interface(String email,int x_loc,int y_loc) {
        this.x_loc = x_loc;
        this.y_loc = y_loc;
        this.e_mail = email;
        setTitle("Home Page");
        setLayout(null);

        ImageIcon imageicon = new ImageIcon(ClassLoader.getSystemResource("PNGPhotos/pexels-ben-mack-5326909.jpg"));
        Image image = imageicon.getImage().getScaledInstance(700,550,Image.SCALE_DEFAULT);;
        ImageIcon icon2 = new ImageIcon(image);
        image_label = new JLabel(icon2);
        image_label.setBounds(-50,-50,690,520);
        add(image_label);

        try {
            labelMethods(email);
        } catch (SQLException e) {e.printStackTrace();}
        buttonMethods();

        getContentPane().setBackground(Color.LIGHT_GRAY);
        setSize(650,500);
        setLocation(x_loc,y_loc);
//        setUndecorated(true);
    }
    public void labelMethods(String email) throws SQLException {
        user_label = new JLabel();
        user_label.setFont(new Font("Arial", Font.BOLD,30));
        user_label.setBounds(150,130,800,70);
        user_label.setForeground(Color.BLACK);
        image_label.add(user_label);

        try {
            User_Info user = new User_Info(email);
            Connection connection = MyConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM USERS WHERE EMAIL = ?");
            statement.setString(1, user.getEmail());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                user_label.setText("Mrs./Mr.  " + result.getString("USER_NAME"));
            }
        } catch (Exception e) {e.printStackTrace();}
        info_label = new JLabel("Thank You for choosing");
        info_label.setFont(new Font("Monospaced", Font.BOLD,40));
        info_label.setBounds(100,50,800,100);
        info_label.setForeground(Color.BLACK);
        image_label.add(info_label);

    }

    public void buttonMethods() {
        add_button = new JButton("Hide your Files");
        add_button.setFont(new Font("Osward",Font.PLAIN,20));
        add_button.setBounds(200,220,280,40);
        add_button.setBackground(Color.black);
        add_button.setForeground(Color.white);
        add_button.addActionListener(this);
        image_label.add(add_button);

        file_button = new JButton("See your Hidden Files");
        file_button.setFont(new Font("Osward",Font.PLAIN,20));
        file_button.setBounds(200,290,280,40);
        file_button.setBackground(Color.black);
        file_button.setForeground(Color.white);
        file_button.addActionListener(this);
        image_label.add(file_button);

        remove_button = new JButton("Remove your Hidden Files");
        remove_button.setFont(new Font("Osward",Font.PLAIN,20));
        remove_button.setBounds(200,360,280,40);
        remove_button.setBackground(Color.black);
        remove_button.setForeground(Color.white);
        remove_button.addActionListener(this);
        image_label.add(remove_button);

        exit_button = new JButton("Exit Page");
        exit_button.setFont(new Font("Osward",Font.PLAIN,20));
        exit_button.setBounds(250,430,180,40);
        exit_button.setBackground(Color.black);
        exit_button.setForeground(Color.white);
        exit_button.addActionListener(this);
        image_label.add(exit_button);
    }

    public void actionPerformed(ActionEvent action){
        if(action.getSource() == exit_button){
            setVisible(false);
            new Sign_In(x_loc, y_loc).setVisible(true);
        } else if (action.getSource() == add_button) {
            setVisible(false);
            new Add_Files(e_mail,x_loc, y_loc).setVisible(true);
        } else if (action.getSource() == file_button) {
            setVisible(false);
            new Show_Files(e_mail,x_loc, y_loc).setVisible(true);
        } else if (action.getSource() == remove_button) {
            setVisible(false);
            new Remove_Files(e_mail,x_loc, y_loc).setVisible(true);
        }
    }

//    public static void main(String[] args) {
//        new Home_Interface("captainbiki100@gmail.com").setVisible(true);
//    }
}
