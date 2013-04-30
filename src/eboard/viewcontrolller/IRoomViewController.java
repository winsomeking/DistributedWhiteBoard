/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.viewcontrolller;

import eboard.itf.control.IApplication;
import eboard.itf.model.IResponse;
import eboard.itf.model.IRoom;
import eboard.itf.model.IUser;
import eboard.model.IShape;
import eboard.view.NewJFrame;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author elf
 */
public class IRoomViewController {

//    private static IRoomViewController  roomCtlr = null;
    private RoomCtrlViewController roomCtrlViewController = null;
    private MessageViewController msgViewController = null;
    private DrawViewController drawViewController = null;
    private IApplication ia = null;
//    private static IRoomViewController rvc= null;// new IRoomViewController();
    private NewJFrame jf = null;
    private Vector stack = null;
    private int statckPointer = -1;
    private IRoom room = null;
    public IRoomViewController(IRoom room){
        //this contructor is only for user
        this.room = room;
        roomCtrlViewController = new RoomCtrlViewController(room);
        msgViewController = new MessageViewController(room);
        drawViewController = new DrawViewController(room);
        //jf = new NewJFrame();
        statckPointer = 0;
        stack =  new Vector();
        
    }

    public  NewJFrame getFrame(){
        return this.jf;
    }

    public  void setFrame(NewJFrame f){
        this.jf = f;
    }

    public void setRoom(IRoom room){
        this.room = room;
    }

    public IRoom getRoom(IRoom room){

        return this.room;
    }


    
//    public static IRoomViewController getInstance(){
//        //if (rvc == null){
////            rvc =
//                   return null;// return new IRoomViewController();
//        //}
////        return rvc;
//
//
//    }

    public void showMainFrame(){

        this.jf.setVisible(true);
    }




    //room ctrl
    public IResponse createPwdRoom(String roomId){
        return roomCtrlViewController.createRoom(roomId);
    }
    public IResponse closeRoom(String roomId){
        return roomCtrlViewController.closeRoom(roomId);
    }
    public IResponse quitRoom(String roomId, String userId){
        return roomCtrlViewController.quitRoom(roomId, userId);
    }
    public IResponse enterRoom(String roomId, String userId, String pwd){
        return roomCtrlViewController.enterRoom(roomId, userId, pwd);
    }

    public IResponse oneUserEntering(String roomId, IUser user){
        return roomCtrlViewController.oneUserEntering(roomId, user);
    }
    public IResponse enforceQuitRoom(String roomId, String userId){
        return roomCtrlViewController.enforceQuitRoom(roomId, userId);
    }

    public IResponse remoteEnforceQuitRoom(String roomId){
        return null;
    }

    public IResponse oneUserQuitRoom(String roomId, String userId){
        return roomCtrlViewController.oneUserQuitRoom(roomId, userId);
    }



        //message control method
    public IResponse sendMsg2All(String roomId, String msg){
        return msgViewController.sendMsg2All(roomId, msg);
    }
     public IResponse sendMsg2User(String roomId,  String toUserId, String msg){
         return msgViewController.sendMsg2User(roomId, toUserId, msg);
     }

            //message control method
    public IResponse recieveMsg(String roomId, String fromUserId, String msg){
        return msgViewController.recieveMsg(roomId, fromUserId, msg);
    }
     public IResponse recievePrivateMsg(String roomId,  String fromUserId, String msg){
         return msgViewController.recievePrivateMsg(roomId, fromUserId, msg);
     }
      //draw executor

     /**
     * apply to the user with the given id for the whiteboard lock
     * @param roomId the room of which the lock is
     * @param ownerUserId the user who owns the lock
     * @return (false, not null): when lock is not got
     *          (false, null): when expected lock-owner doesn't exsist
     *          true: when lock is got
     */
    public IResponse getLock(String roomId, String ownerUserId){
        return  null;
    }

    /**
     * inform other user in the same room , i got the lock
     * @return false: when other guy got a lock and is using it
     *          true: when every body know this.
     */
    public IResponse iGotLock(String roomId, String ownerUserId){
        System.out.println("room ("+roomId+"), user ("+ownerUserId+") got lock.");
        return  null;
    }

    /**
     * ask other users is there any lock exsist, if not the user who sent
     * this message will generate a lock, own it, when the guy who is supposed
     * to keep a lock doesn't exsist.
     * @return false: when some body get a lock AND is used.
     *          true: when every body doesn't get a lock OR isn;t use it.
     */
    public IResponse weNeedLock(String roomId){
        return  null;
    }


    /**
     * Room need to draw a shape
     * @param roomId the specific room nees to draw a shape
     * @param shape the shape need to be draw
     * @return whethe the shape has been drawn successfully
     */
    public IResponse draw(String roomId, IShape shape){
        return drawViewController.draw(roomId, shape);
    }


        /**
     * Room need to redo the former event
     * @param roomId the specific room nees to redo an event
     * @return whethe the event has been redone successfully
     */
    public IResponse redo(String roomId){

        
        return drawViewController.redo(roomId);
    }



    /**
     * Room need to undo the former event
     * @param roomId the specific room nees to undo an event
     * @return whethe the event has been undone successfully
     */
    public IResponse undo(String roomId){

        return drawViewController.undo(roomId);
    }



     public IResponse clear(String roomId){
        return this.roomCtrlViewController.clear(roomId);
    }

     public IResponse synchronizeBoard(String roomId,ArrayList<byte[]> undoList, ArrayList<byte[]> redoList){
        return this.drawViewController.synchronizeBoard( roomId,undoList, redoList);
//        return null;
    }
}
