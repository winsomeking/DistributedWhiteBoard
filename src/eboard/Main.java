/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard;

import eboard.view.LoginFrame;
import java.rmi.RMISecurityManager;

/**
 * Distributed System Assignment 2 -  eBoard
 * This class <b>Main</b> is the application entrance.
 * The LoginFrame instance will show after the init
 */
public class Main {


    //private static Main main = null;

    private Main(){
        
    }
    /**
     * Application entrance
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Main m = new Main();
        new Main().init(args);
    }

   

    /**
     * init application enviroment and so on
     * @param args the command line arguments
     */
    public void init(String[] args){
        try{
            LoginFrame lf = new LoginFrame();
            lf.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
    }

}
