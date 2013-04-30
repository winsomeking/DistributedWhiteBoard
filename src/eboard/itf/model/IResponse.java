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
public interface IResponse extends Serializable{
    

    
    public boolean isSuccess();
    public String getResponseMsg();

    public void setSuccess(boolean flag);
    public void setResponseMsg(String msg);

    public IResponse combine(IResponse r);

    
}
