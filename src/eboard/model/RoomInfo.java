/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.model;

import eboard.itf.model.IUser;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author elf
 */
public class RoomInfo implements Serializable{
    private HashMap<String, IUser> users = null;
    private IUser owner = null;
    private String roomName = null;
    private String ip = null;
    int port = -1;
    private String pwd = null;

    public RoomInfo(String ip, int port, String roomName, IUser owner, HashMap<String, IUser> userlist, String pwd){
        this.ip = ip;
        this.port = port;

        this.roomName = roomName;
        this.owner = owner;
        this.users = userlist;
        this.pwd = pwd;
    }

    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public IUser getOwner() {
        return owner;
    }

    public void setOwner(IUser owner) {
        this.owner = owner;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }


    public void setUserList(HashMap<String, IUser> users){
        this.users = users;
    }

    public HashMap<String, IUser> getUserList(){
        return this.users;
    }

    public String getRoomId(){
        return this.ip+this.port+this.roomName;
    }
}
