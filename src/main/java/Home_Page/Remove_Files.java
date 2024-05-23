package Home_Page;

import DAO.Data_DAO;
import Model.Data_Info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Remove_Files extends JFrame implements ActionListener {

    private JLabel image_label,info_label,remove_label;
    private JTextField id_field;
    private JButton show_file_button,remove_button,back_button;
    private String e_mail;
    private int x_loc, y_loc;

    public Remove_Files(String email,int x_loc,int y_loc) {
        this.x_loc = x_loc;
        this.y_loc = y_loc;
        this.e_mail = email;

        setTitle("Remove Files");
        setLayout(null);

        ImageIcon imageicon = new ImageIcon(ClassLoader.getSystemResource("PNGPhotos/pexels-invisiblepower-1076885.jpg"));
        Image image = imageicon.getImage().getScaledInstance(700,550,Image.SCALE_DEFAULT);;
        ImageIcon icon2 = new ImageIcon(image);
        image_label = new JLabel(icon2);
        image_label.setBounds(-50,-50,690,520);
        add(image_label);

        labelMethods();
        fieldMethods();
        buttonMethods();

        getContentPane().setBackground(Color.LIGHT_GRAY);
        setSize(650,500);
        setLocation(x_loc, y_loc);
//        setUndecorated(true);
    }

    public void labelMethods() {
        info_label = new JLabel("Remove Your Hidden Files");
        info_label.setFont(new Font("Monospaced", Font.BOLD,40));
        info_label.setBounds(85,50,800,100);
        info_label.setForeground(Color.BLACK);
        image_label.add(info_label);

        remove_label = new JLabel("Enter the File ID");
        remove_label.setFont(new Font("Serif",Font.BOLD,30));
        remove_label.setBounds(150,-20,330,450);
        remove_label.setForeground(Color.BLACK);
        image_label.add(remove_label);
    }
    public void fieldMethods() {
        id_field = new JTextField();
        id_field.setBounds(430,185,100,40);
        id_field.setFont(new Font("Arial",Font.PLAIN,26));
        id_field.setBackground(Color.WHITE);
        id_field.setForeground(Color.BLACK);
        image_label.add(id_field);
    }
    public void buttonMethods() {
        show_file_button = new JButton("Show Files");
        show_file_button.setFont(new Font("Osward",Font.PLAIN,20));
        show_file_button.setBounds(150,300,160,40);
        show_file_button.setBackground(Color.black);
        show_file_button.setForeground(Color.white);
        show_file_button.addActionListener(this);
        image_label.add(show_file_button);

        remove_button = new JButton("Remove Files");
        remove_button.setFont(new Font("Osward",Font.PLAIN,20));
        remove_button.setBounds(370,300,160,40);
        remove_button.setBackground(Color.black);
        remove_button.setForeground(Color.white);
        remove_button.addActionListener(this);
        image_label.add(remove_button);

        back_button = new JButton("Back");
        back_button.setFont(new Font("Osward",Font.PLAIN,20));
        back_button.setBounds(260,370,160,40);
        back_button.setBackground(Color.black);
        back_button.setForeground(Color.white);
        back_button.addActionListener(this);
        image_label.add(back_button);
    }

    public void actionPerformed(ActionEvent action) {
        String id_String = id_field.getText();

        if (action.getSource() == back_button) {
            setVisible(false);
            new Home_Interface(e_mail,x_loc, y_loc).setVisible(true);
        } else if (action.getSource() == show_file_button) {
            setVisible(false);
            new ShowFilesFormRemove(e_mail,x_loc, y_loc).setVisible(true);
        } else if (action.getSource() == remove_button) {
            if(id_String.equals("")){
                JOptionPane.showMessageDialog(null,"Enter the File ID");
            } else {
                ArrayList<Data_Info> files = null;
                try {
                    files = Data_DAO.getAllFiles(e_mail);
                    int id = Integer.parseInt(id_String);
                    boolean is_Valid = false;
                    for (Data_Info file: files) {
                        if (file.getId() == id) {
                            is_Valid = true;
                            break;
                        }
                    }
                    if(is_Valid) {
                        Data_DAO.unHide(id);
                        JOptionPane.showMessageDialog(null,"File Remove Successful");
                        id_field.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null,"Wrong ID Input");
                    }

                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    public static void main(String[] args) {
//        new Remove_Files("captainbiki100@gmail.com").setVisible(true);
//    }
}
