package org.GUI_Version_Project;

import Log_In_Page.Sign_In;

public class Main {
    public static void main(String[] args) {
        int x_loc = 880, y_loc = 330;
        new Sign_In(x_loc,y_loc).setVisible(true);
    }
}