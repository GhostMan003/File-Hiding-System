package Log_In_Page;

import DAO.User_DAO;
import Home_Page.Home_Interface;
import Model.User_Info;
import Services.GenerateOTP;
import Services.SendOTPService;
import Services.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Sign_Up extends JFrame implements ActionListener {

    private int x_loc, y_loc;
    private JLabel image_label,info_label,name_label,email_label,otp_label;
    private JTextField name_field,email_field,otp_field;
    private JButton generate_button,submit_button,cancel_button,verify_button;
    public Sign_Up(int x_loc, int y_loc) {
        this.x_loc = x_loc;
        this.y_loc = y_loc;
        setTitle("Personal Details Field");
        setLayout(null);

        ImageIcon imageicon = new ImageIcon(ClassLoader.getSystemResource("PNGPhotos/pexels-simon-berger-1323550.jpg"));
        Image image = imageicon.getImage().getScaledInstance(700,550,Image.SCALE_DEFAULT);;
        ImageIcon icon2 = new ImageIcon(image);
        image_label = new JLabel(icon2);
        image_label.setBounds(-50,-50,690,520);
        add(image_label);

        labelMethods();
        fieldMethods();
        buttonMethod();

        getContentPane().setBackground(Color.LIGHT_GRAY);
        setSize(650,500);
        setLocation(x_loc,y_loc);
//        setUndecorated(true);
    }

    private void labelMethods() {
        info_label = new JLabel("Fill Personal Details");
        info_label.setFont(new Font("Monospaced", Font.BOLD,40));
        info_label.setBounds(100,50,800,100);
        info_label.setForeground(Color.BLACK);
        image_label.add(info_label);

        name_label = new JLabel("Name");
        name_label.setFont(new Font("Serif",Font.BOLD,30));
        name_label.setBounds(100,-20,330,450);
        name_label.setForeground(Color.BLACK);
        image_label.add(name_label);

        email_label = new JLabel("Email");
        email_label.setFont(new Font("Serif",Font.BOLD,30));
        email_label.setBounds(100,55,330,450);
        email_label.setForeground(Color.BLACK);
        image_label.add(email_label);

        otp_label = new JLabel("OTP");
        otp_label.setFont(new Font("Serif",Font.BOLD,30));
        otp_label.setBounds(100,130,330,450);
        otp_label.setForeground(Color.BLACK);
        otp_label.setVisible(false);
        image_label.add(otp_label);
    }
    private void fieldMethods() {
        name_field = new JTextField();
        name_field.setBounds(210,190,380,40);
        name_field.setFont(new Font("Arial",Font.PLAIN,26));
        name_field.setForeground(Color.BLACK);
        image_label.add(name_field);

        email_field = new JTextField();
        email_field.setBounds(210,260,380,40);
        email_field.setFont(new Font("Arial",Font.PLAIN,26));
        email_field.setForeground(Color.BLACK);
        image_label.add(email_field);

        otp_field = new JTextField();
        otp_field.setBounds(210,330,120,40);
        otp_field.setFont(new Font("Arial",Font.PLAIN,26));
        otp_field.setForeground(Color.BLACK);
        otp_field.setVisible(false);
        image_label.add(otp_field);
    }
    private void buttonMethod() {
        generate_button = new JButton("Generate OTP");
        generate_button.setFont(new Font("Osward",Font.PLAIN,13));
        generate_button.setBounds(470,310,120,40);
        generate_button.setBackground(Color.black);
        generate_button.setForeground(Color.white);
        generate_button.addActionListener(this);
        image_label.add(generate_button);

        submit_button = new JButton("Submit");
        submit_button.setFont(new Font("Osward",Font.PLAIN,20));
        submit_button.setBounds(210,400,160,40);
        submit_button.setEnabled(false);
        submit_button.setBackground(Color.black);
        submit_button.setForeground(Color.white);
        submit_button.addActionListener(this);
        image_label.add(submit_button);

        cancel_button = new JButton("Cancel");
        cancel_button.setFont(new Font("Osward",Font.PLAIN,20));
        cancel_button.setBounds(430,400,160,40);
        cancel_button.setBackground(Color.black);
        cancel_button.setForeground(Color.white);
        cancel_button.addActionListener(this);
        image_label.add(cancel_button);

        verify_button = new JButton("Verify");
        verify_button.setFont(new Font("Osward",Font.PLAIN,13));
        verify_button.setBounds(350,330,80,40);
        verify_button.setBackground(Color.black);
        verify_button.setForeground(Color.white);
        verify_button.setVisible(false);
        verify_button.addActionListener(this);
        image_label.add(verify_button);
    }

    private String genOTP = GenerateOTP.getOTP();
    private int response = -1;
    public void actionPerformed(ActionEvent action) {
        String name_String = name_field.getText();
        String email_String = email_field.getText();
        User_Info user = new User_Info(name_String,email_String);

        try {
            if (action.getSource() == cancel_button) {

                setVisible(false);
                new Sign_In(x_loc,y_loc).setVisible(true);
            } else if (action.getSource() == generate_button) {
                if(name_String.equals("")) {
                    JOptionPane.showMessageDialog(null,"Enter your Name");
                } else if (email_String.equals("")){
                    JOptionPane.showMessageDialog(null,"Enter your E-Mail Id");
                } else if (User_DAO.isExists(email_String)) {
                    JOptionPane.showMessageDialog(null,"User Already Exist");
                } else {
                    JOptionPane.showMessageDialog(null,"OTP send on your mail");
                    SendOTPService.sendOTP(email_String, genOTP);
                    otp_label.setVisible(true);
                    otp_field.setVisible(true);
                    verify_button.setVisible(true);
                }
            } else if (action.getSource() == verify_button){
                String otp_string = otp_field.getText();
                if (otp_string.equals("")){
                    JOptionPane.showMessageDialog(null,"Enter OTP");
                }
                else if (otp_string.equals(genOTP)) {
                    submit_button.setEnabled(true);
                    response = UserService.saveUser(user);
                    JOptionPane.showMessageDialog(null,"Verified");
                } else {
                    JOptionPane.showMessageDialog(null,"Wrong OTP");
                }

            }
            else if (action.getSource() == submit_button) {
                switch (response) {
                    case 1 -> {
                        JOptionPane.showMessageDialog(null, "User Register Successful");
                        setVisible(false);
                        new Home_Interface(email_String,x_loc, y_loc).setVisible(true);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public static void main(String[] args) {
//        new Sign_Up().setVisible(true);
//    }
}
