package Home_Page;

import DAO.Data_DAO;
import Model.Data_Info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Add_Files extends JFrame implements ActionListener {

    private int x_loc, y_loc;
    private JLabel image_label,info_label,path_label;
    private JTextField path_field;
    private JButton back_button,add_button,clear_button;
    private String e_mail;
    public Add_Files(String email,int x_loc,int y_loc) {
        this.e_mail = email;
        this.x_loc = x_loc;
        this.y_loc = y_loc;

        setTitle("Hide Files");
        setLayout(null);
        ImageIcon imageicon = new ImageIcon(ClassLoader.getSystemResource("PNGPhotos/pexels-abdullah-ghatasheh-1631677.jpg"));
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
        info_label = new JLabel("Hide Your Files");
        info_label.setFont(new Font("Monospaced", Font.BOLD,40));
        info_label.setBounds(180,50,800,100);
        info_label.setForeground(Color.BLACK);
        image_label.add(info_label);

        path_label = new JLabel("Enter the PATH");
        path_label.setFont(new Font("Serif",Font.BOLD,30));
        path_label.setBounds(100,-40,330,450);
        path_label.setForeground(Color.BLACK);
        image_label.add(path_label);

    }
    public void fieldMethods() {
        path_field = new JTextField();
        path_field.setBounds(100,230,500,40);
        path_field.setFont(new Font("Arial",Font.PLAIN,26));
        path_field.setForeground(Color.BLACK);
        image_label.add(path_field);
    }
    public void buttonMethods() {
        add_button = new JButton("ADD File");
        add_button.setFont(new Font("Osward",Font.PLAIN,30));
        add_button.setBounds(150,350,160,50);
        add_button.setBackground(Color.BLACK);
        add_button.setForeground(Color.WHITE);
        add_button.addActionListener(this);
        image_label.add(add_button);

        back_button = new JButton("Back");
        back_button.setFont(new Font("Osward",Font.PLAIN,30));
        back_button.setBounds(380,350,160,50);
        back_button.setBackground(Color.BLACK);
        back_button.setForeground(Color.WHITE);
        back_button.addActionListener(this);
        image_label.add(back_button);

        clear_button = new JButton("Clear");
        clear_button.setFont(new Font("Osward",Font.PLAIN,15));
        clear_button.setBounds(520,280,80,40);
        clear_button.setBackground(Color.black);
        clear_button.setForeground(Color.white);
        clear_button.addActionListener(this);
        image_label.add(clear_button);
    }
    public void actionPerformed(ActionEvent action) {
        String path_String = path_field.getText().trim();

        if (path_field.isValid()) {
            if (action.getSource() == back_button) {
                setVisible(false);
                new Home_Interface(e_mail, x_loc, y_loc).setVisible(true);
            } else if (action.getSource() == add_button) {
                if (path_String.equals("")) {
                    JOptionPane.showMessageDialog(null, "Enter File Path");
                } else {
                    try {
                        if (Data_DAO.isPathExists(path_String, e_mail)) {
                            JOptionPane.showMessageDialog(null, "File is already exists");
                            path_field.setText("");
                        } else {
                            File file = new File(path_String);
                            Data_Info file_info = new Data_Info(0, file.getName(), path_String, e_mail);
                            try {
                                if (Data_DAO.hideFile(file_info) == -1) {
                                    JOptionPane.showMessageDialog(null, "File Not Found");
                                    path_field.setText("");
                                } else {
                                    JOptionPane.showMessageDialog(null, "File Hide Successful");
                                    path_field.setText("");
                                }
                            } catch (SQLException | IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else if (action.getSource() == clear_button) {
                path_field.setText("");
            }
        }

    }

//    public static void main(String[] args) {
//        int x_loc = 880, y_loc = 330;
//        new Add_Files("anuragghosh202@gmail.com",x_loc, y_loc).setVisible(true);
//    }
}
