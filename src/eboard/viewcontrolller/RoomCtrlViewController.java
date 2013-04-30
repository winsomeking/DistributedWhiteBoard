/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.viewcontrolller;

import eboard.itf.model.IResponse;
import eboard.itf.model.IRoom;
import eboard.itf.model.IUser;
import eboard.model.Response;
import eboard.view.NewJFrame;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author elf
 */
 class RoomCtrlViewController {
     private IRoom room = null;


     RoomCtrlViewController(IRoom room ){
         this.room = room;
     }
     //
    public IResponse createRoom(String roomId){
        System.out.println("create room room id: "+roomId );
        NewJFrame f = new NewJFrame(room);
        room.getRoomViewController().setFrame(f);
        f.setVisible(true);
        HashMap<String,IUser> users = room.getUserList();
        ArrayList<Color> colors = new ArrayList<Color>();
        Iterator iter = users.entrySet().iterator();
        int i = 0;
        while (iter.hasNext()) {
            Map.Entry<String, IUser> entry = (Map.Entry<String, IUser>) iter.next();
            colors.add(new Color((int)(Math.random()*255),
                    (int)(Math.random()*255),
                    (int)(Math.random()*255)));
        }
        f.getUserListCList().addUsers(room.getUserList(),colors);
        f.getToWhoCombox().addUsers(room.getUserList(),colors);

        //init white board
        f.getPanel().initUndoBox();
        f.getPanel().clear();
        return new Response(true,"");
    }
    
    public IResponse closeRoom(String roomId){
        System.out.println("close room room id: "+roomId );

         NewJFrame f = room.getRoomViewController().getFrame();
         this.showMsgDialog(f, "Administrator closed this room.");
         f.close();
        return new Response(true,"");
    }
    
    public IResponse quitRoom(String roomId, String userId){
        System.out.println("quit room room id: "+roomId +" user id "+ userId );
        NewJFrame f = room.getRoomViewController().getFrame();
        f.close();
        return new Response(true,"");
    }

    public IResponse oneUserQuitRoom(String roomId, String userId){
        System.out.println("one user quit room room id: "+roomId +" user id "+ userId );
        NewJFrame f = room.getRoomViewController().getFrame();
        f.getUserListCList().removeUser(userId);
        //f.close();
        return new Response(true,"");
    }
    
    public IResponse enterRoom(String roomId, String userId, String pwd){
        System.out.println("enter room room id: "+roomId +" user id "+ userId+ " password "+ pwd);
        NewJFrame f = new NewJFrame(room);
        room.getRoomViewController().setFrame(f);
        f.setVisible(true);
        HashMap<String,IUser> users = room.getUserList();
        ArrayList<Color> colors = new ArrayList<Color>();
        Iterator iter = users.entrySet().iterator();
        int i = 0;
        while (iter.hasNext()) {
            Map.Entry<String, IUser> entry = (Map.Entry<String, IUser>) iter.next();
            colors.add(new Color((int)(Math.random()*255),
                    (int)(Math.random()*255),
                    (int)(Math.random()*255)));
        }
        f.getUserListCList().addUsers(room.getUserList(),colors);
        f.getToWhoCombox().addUsers(room.getUserList(),colors);
        //todo load the image
        return new Response(true,"");
    }
    public IResponse enforceQuitRoom(String roomId){
        System.out.println("enforce quit room, room id: "+roomId );
        NewJFrame f = room.getRoomViewController().getFrame();
        this.showMsgDialog(f, "Administrator kick you out of this room.");
        f.close();
        return new Response(true,"");
    }
    public IResponse oneUserEntering(String roomId, IUser user){
        System.out.println("one user entering room, room id: "+roomId +" user id "+user.getUserId());
        NewJFrame f = room.getRoomViewController().getFrame();
        Color c = new Color((int)(Math.random()*255),
                    (int)(Math.random()*255),
                    (int)(Math.random()*255));
        f.getUserListCList().addUser(user,c);
        f.getToWhoCombox().addUser(user,c);
        return new Response(true,"");
    }
    public IResponse enforceQuitRoom(String roomId, String userId){
         System.out.println("enforce Quit Room, room id: "+roomId +" user id "+userId);
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


    public IResponse clear(String roomId){
        this.room.getRoomViewController().getFrame().getPanel().clear();
        return new Response(true,"");
    }
}
