/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.control;

import eboard.itf.model.IResponse;
import eboard.itf.model.IRoom;
import eboard.itf.model.IUser;
import eboard.model.IShape;
import eboard.model.Response;
import eboard.model.RoomInfo;
import eboard.model.WhiteBoardLock;
import eboard.rit.IExecutor;
import eboard.viewcontrolller.IRoomViewController;
import java.util.ArrayList;
import java.util.ArrayList;

/**
 *
 * @author elf
 */
class RoomCtrlExecutor {
     
//     static IResponse createRoom(String roomId, String ownerUserId, String pwd){
//        //for test
//         System.out.println("room is created, gui init..." );
//        IRoom r = Application.getInstance().getRoom(roomId);
//        IResponse rsp = null;
//        if ( r== null )
//            return new Response(false, "room: "+roomId+"didn't draw.");
//        IRoomViewController rvc = r.getRoomViewController();
//        //to do logic controll
//
//        rsp.combine(rvc.closeRoom(roomId));
//        return null;
//    }

    /**
     * Close the room with roomId
     * @param roomId the room to be closed
     * @return whether the room has been closed successfully
     */
     static IResponse closeRoom(final String roomId){

          IRoom r = Application.getInstance().getRoom(roomId);
        IResponse rsp = new Response();
        if ( r== null )
            return new Response(false, "room: "+roomId+"didn't draw.");
        final IRoomViewController rvc = r.getRoomViewController();
        //remove the room with the room id
        new Thread(new Runnable(){
            public void run(){
                rvc.closeRoom(roomId);
            }
        }).start();
        Application.getInstance().removeRoom(roomId);
        return rsp;
       
    }

     /**
     * Use whose id is userId quit the room (roomId)
     * @param roomId the room to be closed
     * @param userId the user's id who wants to quit the room
     * @return whether user has quit the room successfully
     */
     static IResponse quitRoom(String roomId, String userId){
         IRoom r = Application.getInstance().getRoom(roomId);
        IResponse rsp = new Response();
        if ( r== null )
            return new Response(false, "room: "+roomId+"didn't draw.");
        IRoomViewController rvc = r.getRoomViewController();
        //delete user from room
        
        
        rsp.combine(rvc.oneUserQuitRoom(roomId, userId));
        r.deleteUser(userId);
        return rsp;
    }

     /**
      * The specific user (userId) ask the request to enter room ( roomId )
      * with password (pwd)
      * @param roomId the room id
      * @param userId the user who make the request to enter room
      * @param pwd room pwd
      * @return whether the pwd is correct
      */
     static IResponse enterRoom(String roomId, String userId, String pwd){
        IRoom r = Application.getInstance().getRoom(roomId);
//        IResponse rsp = new Response();
        if ( r== null )
            return new Response(false, "room: "+roomId+"didn't draw.");
        RoomInfo ri = r.getRoomInfo();
        if ( pwd == null && null != ri.getPwd() ||
             pwd != null && !pwd.equals(ri.getPwd())){
            return new Response(false, "room: "+roomId+" pwd didn't match.");
        }else if(r.getUserList().get(userId)!=null){
            return new Response(false, "can not enter the same room: "+roomId+
                    " twice.");
        }
            return new Response(true, "room: "+roomId+" allow to enter.");
//        IRoomViewController rvc = r.getRoomViewController();
//
//        rsp.combine(rvc.enterRoom(roomId, userId, pwd));
//        return rsp;
    }

     /**
      * Automatically quit room
      * @param roomId
      * @param userId
      * @return
      */
     static IResponse enforceQuitRoom(String roomId, String userId){
         IRoom r = Application.getInstance().getRoom(roomId);
        IResponse rsp = new Response();
        if ( r== null )
            return new Response(false, "room: "+roomId+"didn't draw.");
        IRoomViewController rvc = r.getRoomViewController();
        rsp.combine(rvc.enforceQuitRoom(roomId, userId));
         //quit room
        rsp.combine(Application.getInstance().quitRoom(roomId));
        
        return rsp;
    }



      public  static IResponse apply2SynchronizeBoard(String roomId, String userId) {
         IRoom r = Application.getInstance().getRoom(roomId);
        IResponse rsp = new Response();
        if ( r== null )
            return new Response(false, "room: "+roomId+"didn't draw.");
        IRoomViewController rvc = r.getRoomViewController();
//        rsp.combine(rvc.enforceQuitRoom(roomId, userId));

         //sent synchronize image to the user in the room
        IUser u = r.getUserList().get(userId);
        IShape is = rvc.getFrame().getPanel().getCurrentImage();
        RemoteObjectsManager rom = RemoteObjectsManager.getInstance();
        IExecutor ie = rom.getRemoteExecutor(roomId,
                userId,u.getIP(), u.getPort());
        try{
            rsp = ie.draw(roomId, is);
            ArrayList<byte[]> al = rvc.getFrame().getPanel().getDoUndoOpList();
            ArrayList<byte[]> undolist = new ArrayList<byte[]>();
            ArrayList<byte[]> redolist = new ArrayList<byte[]> ();
            ArrayList<byte[]> tmp = undolist;
            for(int i =0; i < al.size(); i ++){
                if(al.get(i)== null){
                    tmp = redolist;
                    continue;
                }else{
                    tmp.add(al.get(i));
                }
            }
            rsp.combine(ie.synchronizeBoard(roomId, undolist, redolist));
            System.out.println(9993121);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("synchronize error");
            return new Response(false, "fail to synchroized.");
        }

        return rsp;
    }

     /**
     * One user is entering the room
     * @param roomId the room the user is entering
     * @param user the user who is entering the room
     * @return always successfullly if no exception
     */
     static IResponse oneUserEntering(String roomId, IUser user){
         IRoom r = Application.getInstance().getRoom(roomId);
        IResponse rsp = new Response();
        if ( r== null )
            return new Response(false, "room: "+roomId+" didn't draw.");
        if (user == null)
            return new Response(false, "user is null ");
        IRoomViewController rvc = r.getRoomViewController();
        //add user to the userlist of room (roomId)
        r.addUser(user);
        rsp.combine(rvc.oneUserEntering(roomId, user));
        return rsp;
     }


    /**
     * apply to the user with the given id for the whiteboard lock
     * @param userId the guy who owns the lock
     * @return (false, not null): when lock is not got
     *          (false, null): when expected lock-owner doesn't exsist
     *          true: when lock is got
     */
    static public IResponse getLock(String roomId, String ownerUserId){
        Application ap = Application.getInstance();
        IRoom r = ap.getRoom(roomId);
        //
        if(r == null) return new Response(false,null);
        WhiteBoardLock l = r.getLockInfo();

        if(l.isLocked()) return new Response(false,"white board is being used.");
        if(!ap.user.getUserId().equals(l.getLockOwnerUserId())){
            System.out.println(l.getLockOwnerUserId() +"\n"+ownerUserId);
            return new Response(false, "white board is not here.");
        }
        l.setLockOwnerUserId(ownerUserId);
        l.setLocked(false);
        return new Response(true,"user: "+ownerUserId+"got the lock to white board.");
    }

    /**
     * inform other user in the same room , i got the lock
     * @return false: when other guy got a lock and is using it
     *          true: when every body know this.
     */
    static public IResponse iGotLock(String roomId, String ownerUserId){
        Application ap = Application.getInstance();
        IRoom r = ap.getRoom(roomId);
        if(r == null) return new Response(true,"user "+ownerUserId+"not in the room. ");
        WhiteBoardLock l = r.getLockInfo();

        if(ap.user.getUserId().equals(l.getLockOwnerUserId())&&l.isLocked()){
            return new Response(false, "white board is being used.");
        }
        l.setLocked(false);
        l.setLockOwnerUserId(ownerUserId);

        return new Response(true,"user: "+ownerUserId+"got the lock to white board.");
    }

    /**
     * ask other users is there any lock exsist, if not the user who sent
     * this message will generate a lock, own it, when the guy who is supposed
     * to keep a lock doesn't exsist.
     * @return false: when some body get a lock AND is used.
     *          true: when white board is not used..
     */
    static public IResponse weNeedLock(String roomId){
        Application ap = Application.getInstance();
        IRoom r = ap.getRoom(roomId);
        if(r == null) return new Response(true,"user "+ap.user.getUserId()+"is not in the room. ");
        WhiteBoardLock l = r.getLockInfo();
        if(l.isLocked()) return new Response(false,"user "+ap.user.getUserId()+"is using the white board. ");
        return new Response(true, "we need a lock");
    }


     public static IResponse clear(String roomId){
        Application ap = Application.getInstance();
        IRoom r = ap.getRoom(roomId);
        if(r == null) return new Response(true,"user "+ap.user.getUserId()+"is not in the room. ");
        return r.getRoomViewController().clear(roomId);
    }

}
