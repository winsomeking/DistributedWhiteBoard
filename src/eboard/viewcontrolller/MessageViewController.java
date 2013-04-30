/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.viewcontrolller;

import eboard.itf.model.IResponse;
import eboard.itf.model.IRoom;
import eboard.itf.model.IUser;
import eboard.model.Response;
import eboard.view.ColorfulTextPane;
import eboard.view.NewJFrame;
import java.awt.Color;
import java.awt.Component;

/**
 *
 * @author elf
 */
 class MessageViewController {

     private IRoom room = null;


    MessageViewController(IRoom room){
        this.room = room;
    }
        //message control method
    public IResponse sendMsg2All(String roomId, String msg){
        NewJFrame f = room.getRoomViewController().getFrame();
        f.getMsgPane().append("I say to everyone: "+msg+"\n");
        return new Response(true,"");
    }
     public IResponse sendMsg2User(String roomId,  String toUserId, String msg){
         NewJFrame f = room.getRoomViewController().getFrame();
         IUser toUser = room.getUserList().get(toUserId);
         f.getMsgPane().append("I tell "+toUser.getUserName()+" :"+msg+"\n");
         return new Response(true,"");
     }

            //message control method
    public IResponse recieveMsg(String roomId, String fromUserId, String msg){
        NewJFrame f = room.getRoomViewController().getFrame();
        IUser fromUser = room.getUserList().get(fromUserId);
        ColorfulTextPane cftp =  f.getMsgPane();
//        Color c = f.getToWhoCombox().getUserColor(fromUserId);
        cftp.append(fromUser.getUserName()+ " say to everyone: "+msg+"\n");
        return new Response(true,"");
    }
    
     public IResponse recievePrivateMsg(String roomId,  String fromUserId, String msg){
        NewJFrame f = room.getRoomViewController().getFrame();
        IUser fromUser = room.getUserList().get(fromUserId);
        ColorfulTextPane cftp =  f.getMsgPane();
        Color c = f.getToWhoCombox().getUserColor(fromUserId);
        cftp.append(fromUser.getUserName()+ " tell me: "+msg+"\n",c);
        return new Response(true,"");
     }

    private void showMsgDialog(Component parentComponent, String msg){
        javax.swing.JOptionPane.showMessageDialog(parentComponent,
                    msg);
    }

     private String showInputDialog(Component parentComponent, String msg, String title){
        return javax.swing.JOptionPane.showInputDialog(parentComponent,
                msg, title,
                javax.swing.JOptionPane.DEFAULT_OPTION);
    }
}
