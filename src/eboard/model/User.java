/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.model;

import eboard.itf.model.IUser;

/**
 *
 * @author elf
 */
 public class User implements IUser{

    private String userName = null;
    private String userId = null;
    private String ip = null;
    private int port = -1;


    public User(String ip, int port, String userName){
        this.userName = userName;
        this.ip = ip;
        this. port = port;
        this.userId = ip + port + userName;
    }
    public boolean equals(IUser user) {
        try{
            if(user == null||!user.getUserId().equals(userId)) return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName(){
        return this.userName;
    }

    public String getUserId(){
        return this.ip+ this.port + this.userName;
    }



    public String getIP() {
        return ip;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }





    


}
