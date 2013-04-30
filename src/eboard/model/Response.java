/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.model;

import eboard.itf.model.IResponse;

/**
 *
 * @author elf
 */
public class Response implements IResponse{

    private boolean successFlag = false;
    private StringBuffer msg = null;

    public Response(boolean flag, String msg){
        if(msg==null) msg = "";
        this.successFlag = flag;
        this.msg = new StringBuffer(msg);
        
    }
    public Response(){
        this.successFlag = true;
        this.msg = new StringBuffer();
    }
    /**
     * combine two response into one.
     * successFlag is true, only if both the two response is true
     * msg = msg1 + msg2.
     * @param r is the subsequent response
     * @return combined response
     */
    public IResponse combine(IResponse r) {
        if ( r == null) return this;
        this.successFlag &= r.isSuccess();
        this.msg.append(r.getResponseMsg());
       return this;
    }

    public String getResponseMsg() {
        return this.msg.toString();
    }

    public boolean isSuccess() {
       return this.successFlag;
    }

    public void setResponseMsg(String msg) {
        this.msg.delete(0, this.msg.length());
        if(msg== null) return;
        this.msg.append(msg);
    }

    public void setSuccess(boolean flag) {
        this.successFlag = flag;
    }

    


//    public static void main(String[] args){
//
////        Response r = new Response();
////        Response f = new Response();
////        r.setResponseMsg("T");
////        r.setSuccess(true);
////        f.setResponseMsg("F");
////        f.setSuccess(true);
//
//    }



}
