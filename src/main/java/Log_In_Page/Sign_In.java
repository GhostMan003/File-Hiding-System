package Log_In_Page;

import DAO.User_DAO;
import Home_Page.Home_Interface;
import Services.GenerateOTP;
import Services.SendOTPService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sign_In extends JFrame implements ActionListener {

    private int x_loc, y_loc;
    private JTextField email_Field,otp_field;
    private JButton sign_in_Button,sign_up_Button,generate_Button,exit_Button,verify_button;
    private JLabel welcome_Label,email_Label,otp_Label,image_label;

    public Sign_In(int x_loc,int y_loc) {
        this.x_loc = x_loc;
        this.y_loc = y_loc;
        setTitle("File Hide APP");
        setLayout(null);

        ImageIcon imageicon = new ImageIcon(ClassLoader.getSystemResource("PNGPhotos/pexels-roberto-nickson-2559941.jpg"));
        Image image = imageicon.getImage().getScaledInstance(700,550,Image.SCALE_DEFAULT);;
        ImageIcon icon2 = new ImageIcon(image);
        image_label = new JLabel(icon2);
        image_label.setBounds(-50,-50,690,520);
        add(image_label);

        labelMethod();
        fieldMethod();
        buttonMethod();


        getContentPane().setBackground(Color.LIGHT_GRAY);
        setSize(650,500);
        setLocation(x_loc,y_loc);
        setUndecorated(true);
    }

    public void labelMethod() {
        //Welcome label
        welcome_Label = new JLabel("Welcome to FileHide APP");
        welcome_Label.setFont(new Font("Monospaced", Font.BOLD,40));
        welcome_Label.setBounds(80,50,800,100);
        welcome_Label.setForeground(Color.BLACK);
        image_label.add(welcome_Label);

        //email label
        email_Label = new JLabel("Email");
        email_Label.setFont(new Font("Serif",Font.BOLD,30));
        email_Label.setBounds(100,-40,330,450);
        email_Label.setForeground(Color.BLACK);
        image_label.add(email_Label);

        //top label
        otp_Label = new JLabel("OTP");
        otp_Label.setFont(new Font("Serif",Font.BOLD,30));
        otp_Label.setBounds(100,-35,330,600);
        otp_Label.setForeground(Color.BLACK);
        otp_Label.setVisible(false);
        image_label.add(otp_Label);
    }

    public void fieldMethod() {
        //email field
        email_Field = new JTextField();
        email_Field.setBounds(200,165,380,40);
        email_Field.setFont(new Font("Arial",Font.PLAIN,26));
        email_Field.setForeground(Color.BLACK);
        image_label.add(email_Field);

        //otp field
        otp_field = new JTextField();
        otp_field.setBounds(200,245,120,40);
        otp_field.setFont(new Font("Arial",Font.PLAIN,26));
        otp_field.setForeground(Color.BLACK);
        otp_field.setVisible(false);
        image_label.add(otp_field);
    }

    public void buttonMethod() {
        //generate otp
        generate_Button = new JButton("Generate OTP");
        generate_Button.setFont(new Font("Osward",Font.PLAIN,13));
        generate_Button.setBounds(459,215,120,40);
        generate_Button.setBackground(Color.black);
        generate_Button.setForeground(Color.white);
        generate_Button.addActionListener(this);
        image_label.add(generate_Button);

        verify_button = new JButton("Verify");
        verify_button.setFont(new Font("Osward",Font.PLAIN,13));
        verify_button.setBounds(350,245,80,40);
        verify_button.setBackground(Color.black);
        verify_button.setForeground(Color.white);
        verify_button.setVisible(false);
        verify_button.addActionListener(this);
        image_label.add(verify_button);

        //sign in button
        sign_in_Button = new JButton("Sign In");
        sign_in_Button.setFont(new Font("Osward",Font.PLAIN,20));
        sign_in_Button.setBounds(200,350,160,40);
        sign_in_Button.setBackground(Color.black);
        sign_in_Button.setForeground(Color.white);
        sign_in_Button.addActionListener(this);
        image_label.add(sign_in_Button);

        //sign up button
        sign_up_Button = new JButton("Sign Up");
        sign_up_Button.setFont(new Font("Osward",Font.PLAIN,20));
        sign_up_Button.setBounds(420,350,160,40);
        sign_up_Button.setBackground(Color.black);
        sign_up_Button.setForeground(Color.white);
        sign_in_Button.setEnabled(false);
        sign_up_Button.addActionListener(this);
        image_label.add(sign_up_Button);

        //exit button
        exit_Button = new JButton("Exit");
        exit_Button.setFont(new Font("Osward",Font.PLAIN,20));
        exit_Button.setBounds(310,415,160,40);
        exit_Button.setBackground(Color.black);
        exit_Button.setForeground(Color.white);
        exit_Button.addActionListener(this);
        image_label.add(exit_Button);
    }

    private String genOTP = GenerateOTP.getOTP();
    public void actionPerformed(ActionEvent action) {
        String email_String = email_Field.getText();

        if(action.getSource() == exit_Button){
            System.exit(0);
        } else if (action.getSource() == generate_Button) {
            try {
                if(email_String.equals("")){
                    JOptionPane.showMessageDialog(null,"Enter your e-mail");
                }
                else if (User_DAO.isExists(email_String)) {
                    JOptionPane.showMessageDialog(null,"OTP send on your mail");
                    SendOTPService.sendOTP(email_String, genOTP);
                    otp_Label.setVisible(true);
                    otp_field.setVisible(true);
                    verify_button.setVisible(true);
                }
                else {
                    JOptionPane.showMessageDialog(null,"Your E-Mail is not Resister yet");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (action.getSource() == verify_button){
            String otp_string = otp_field.getText();
            if (otp_string.equals("")) {
                JOptionPane.showMessageDialog(null,"Enter the OTP");
            } else if (otp_string.equals(genOTP)) {
                sign_in_Button.setEnabled(true);
                JOptionPane.showMessageDialog(null,"Verified");
            } else {
                JOptionPane.showMessageDialog(null,"Wrong OTP entry");
            }
        } else if (action.getSource() == sign_in_Button) {
            setVisible(false);
            new Home_Interface(email_String,x_loc,y_loc).setVisible(true);
        } else if (action.getSource() == sign_up_Button) {
            setVisible(false);
            new Sign_Up(x_loc,y_loc).setVisible(true);
        }
    }
}
