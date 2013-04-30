/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.model;

import eboard.control.IRoomExecutor;
import eboard.control.RemoteObjectsManager;
import eboard.itf.model.IRoom;
import eboard.itf.model.IUser;
import eboard.viewcontrolller.IRoomViewController;
import eboard.rit.IExecutor;
import java.util.HashMap;

/**
 *
 * @author elf
 */
public class Room implements IRoom{

    private String roomId = null;
    private IRoomExecutor executor = null;
   // private HashMap<String, IExecutor> remoteExceutors = null;
    private RoomInfo roomInfo = null;
    private WhiteBoardLock whiteBoardLock = null;
    private IRoomViewController viewController = null;
//    private Vector stack = new Vector();
//    private int stackPointer = 0;
    public Room(RoomInfo ri){
        this.roomInfo = ri;
        this.roomId = ri.getIp() + ri.getPort() + ri.getRoomName();
        this.executor = new IRoomExecutor();
//        this.viewController = new IRoomViewController();//
        this.whiteBoardLock = new WhiteBoardLock(ri.getOwner().getUserId());
        
    }

    public void setRoomViewController(IRoomViewController rvc){
        this.viewController = rvc;
    }
    


    public IRoomExecutor getExecutor() {
        return executor;
    }

    public void setExecutor(IRoomExecutor executor) {
        this.executor = executor;
    }

//    public HashMap<String, IExecutor> getRemoteExceutors() {
//        return remoteExceutors;
//    }

//    public void setRemoteExceutors(HashMap<String, IExecutor> remoteExceutors) {
//        this.remoteExceutors = remoteExceutors;
//    }

    public RoomInfo getRoomInfo() {
        return roomInfo;
    }

    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }

    public IExecutor getRemoteExector(String userId) {
        IUser u = this.roomInfo.getUserList().get(userId);
        String rIp = u.getIP();
        int rPort = u.getPort();
        return RemoteObjectsManager.getInstance().getRemoteExecutor(roomId, userId, rIp, rPort);
    }

    public IRoomExecutor getRoomExecutor() {
        return this.executor;
    }

    public String getRoomId() {
        return this.roomId;
    }

    public String getRoomName() {
        return this.roomInfo.getRoomName();
    }

    public String getRoomOwnerUserId() {
       return this.roomInfo.getOwner().getUserId();
    }

    public int getRoomPort() {
        return this.roomInfo.getPort();
    }

    public IRoomViewController getRoomViewController() {
       return this.viewController;
    }

    public HashMap<String, IUser> getUserList() {
        return this.roomInfo.getUserList();
    }

    public IRoomViewController getViewController() {
        return viewController;
    }

    public void setViewController(IRoomViewController viewController) {
        this.viewController = viewController;
    }

    public WhiteBoardLock getWhiteBoardLock() {
        return whiteBoardLock;
    }

    public void setWhiteBoardLock(WhiteBoardLock whiteBoardLock) {
        this.whiteBoardLock = whiteBoardLock;
    }


//    public void setRemoteExecutor(String userId, IExecutor remoteExecutor) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public void setRoomExecutor(IRoomExecutor executor) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public void setRoomId(String roomId) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public void setRoomName(String name) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public void setRoomOwnerUserId(String userId) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public void setRoomPort(int port) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public void setRoomViewController(IRoomViewController roomViewController) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

     public boolean addUser(IUser user) {

        IUser u = this.roomInfo.getUserList().get(user.getUserId());
        if (u == null){
            this.roomInfo.getUserList().put(user.getUserId(), user) ;
            return true;
        }
        return false;
    }

     public boolean deleteUser(String userId){
           return this.roomInfo.getUserList().remove(userId) != null;
     }
//   public void redo() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public void undo() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }


    public WhiteBoardLock getLockInfo(){
        return this.whiteBoardLock;
    }

}
