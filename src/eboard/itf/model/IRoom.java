/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.itf.model;

import eboard.control.IRoomExecutor;
import eboard.viewcontrolller.IRoomViewController;
import eboard.model.RoomInfo;
import eboard.model.WhiteBoardLock;
import eboard.rit.IExecutor;
import java.util.HashMap;

/**
 *
 * @author elf
 */
public interface IRoom {



    public HashMap<String,IUser> getUserList();
    public IRoomExecutor getRoomExecutor();
    public String getRoomId();
    public String getRoomName();
    public int getRoomPort();
    public String getRoomOwnerUserId();
    public IExecutor getRemoteExector(String userId);
    public IRoomViewController getRoomViewController();
    public RoomInfo getRoomInfo() ;
    public WhiteBoardLock getLockInfo();
   
//    public void redo();
//    public void undo();
    public void setRoomInfo(RoomInfo roomInfo) ;

    public boolean addUser(IUser user) ;
    public boolean deleteUser(String userId);
    public void setRoomViewController(IRoomViewController rvc);
//    public void setRoomExecutor(IRoomExecutor executor);
//    public void setRoomId(String roomId);
//    public void setRoomPort(int port);
//    public void setRoomName(String name);
//    public void setRoomOwnerUserId(String userId);
//    public void setRemoteExecutor(String userId, IExecutor remoteExecutor);
//    public void setRoomViewController(IRoomViewController roomViewController);

}
