/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.itf.model;

import java.io.Serializable;

/**
 *
 * @author elf
 */
public interface IUser extends Serializable{

    public String getUserId();
    public String getUserName();
    public String getIP();
    public int getPort();

    //public void setUserId(String id);
    public void setUserName(String name);
    public void setIP(String ip);
    public void setPort(int port);

    public boolean equals(IUser user);

}
