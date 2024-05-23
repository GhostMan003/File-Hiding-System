package Home_Page;

import DAO.Data_DAO;
import Model.Data_Info;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Show_Files extends JFrame implements ActionListener {

    private JLabel image_label,id_label,filename_label,id_info_label,file_info_label;
    private JButton back_button;
    private String e_mail;
    private int x_loc, y_loc;

    public Show_Files(String email,int x_loc,int y_loc) {
        this.x_loc = x_loc;
        this.y_loc = y_loc;
        this.e_mail = email;

        setTitle("Hidden Files");
        setLayout(null);
        ImageIcon imageicon = new ImageIcon(ClassLoader.getSystemResource("PNGPhotos/pexels-moose-photos-1037992.jpg"));
        Image image = imageicon.getImage().getScaledInstance(700,550,Image.SCALE_DEFAULT);;
        ImageIcon icon2 = new ImageIcon(image);
        image_label = new JLabel(icon2);
        image_label.setBounds(-50,-50,690,520);
        add(image_label);

        labelMethods();

        back_button = new JButton("Back");
        back_button.setFont(new Font("Osward",Font.PLAIN,30));
        back_button.setBounds(280,410,130,50);
        back_button.setBackground(Color.pink);
        back_button.setForeground(Color.black);
        back_button.addActionListener(this);
        image_label.add(back_button);

        getContentPane().setBackground(Color.LIGHT_GRAY);
        setSize(650,500);
        setLocation(x_loc, y_loc);
//        setUndecorated(true);
    }

    public void labelMethods() {
        id_label = new JLabel("File ID");
        id_label.setFont(new Font("Monospaced", Font.BOLD,30));
        id_label.setBounds(130,50,200,70);
        id_label.setForeground(Color.BLACK);
        image_label.add(id_label);

        filename_label = new JLabel("File Name");
        filename_label.setFont(new Font("Monospaced", Font.BOLD,30));
        filename_label.setBounds(430,50,200,70);
        filename_label.setForeground(Color.BLACK);
        image_label.add(filename_label);

        try {
            ArrayList<Data_Info> files = Data_DAO.getAllFiles(this.e_mail);
            int id_height = 110,file_height = 110,cnt = 0;
            for (Data_Info file: files) {
                labelMethod_part2(String.valueOf(file.getId()),file.getFilename(),id_height,file_height);
                id_height += 30;
                file_height += 30;
                cnt++;
                if(cnt > 8) {
                    labelMethod_part2(".......",".......",id_height,file_height);
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void labelMethod_part2(String id, String file,int id_height, int file_height) {
        id_info_label = new JLabel(String.valueOf(id));
        id_info_label.setFont(new Font("Arial", Font.BOLD,25));
        id_info_label.setBounds(170,id_height,100,30);
        id_info_label.setForeground(Color.BLACK);
        image_label.add(id_info_label);

        file_info_label = new JLabel(file);
        file_info_label.setFont(new Font("Arial", Font.BOLD,25));
        file_info_label.setBounds(430,file_height,250,30);
        file_info_label.setForeground(Color.BLACK);
        image_label.add(file_info_label);
    }

    public void actionPerformed(ActionEvent action) {
        if(action.getSource() == back_button) {
            setVisible(false);
            new Home_Interface(e_mail,x_loc, y_loc).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null,"Wrong Input");
        }
    }

//    public static void main(String[] args) {
//        new Show_Files("captainbiki100@gmail.com").setVisible(true);
//    }
}
