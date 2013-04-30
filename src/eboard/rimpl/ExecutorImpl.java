/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.rimpl;

import eboard.itf.model.IResponse;
import eboard.itf.model.IRoom;
import eboard.control.Application;
import eboard.model.IShape;
import eboard.itf.model.IUser;
import eboard.model.RoomInfo;
import eboard.rit.IExecutor;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author elf
 */
public class ExecutorImpl extends UnicastRemoteObject implements IExecutor{
    
    private String name = "remote executor";
    public ExecutorImpl() throws RemoteException{
        
    }


    public RoomInfo getRoomInfo(String roomId) throws RemoteException {
        return Application.getInstance().getRoom(roomId).getRoomInfo();
    }




    public IResponse oneUserEntering(String roomId, IUser user)throws RemoteException{
        IRoom r = Application.getInstance().getRoom(roomId);

        return r.getRoomExecutor().oneUserEntering(roomId, user);
    }

    public IResponse closeRoom(String roomId) throws RemoteException {
        IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().closeRoom(roomId);
    }

    public IResponse enforceQuitRoom(String roomId, String userId) throws RemoteException {
        IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().enforceQuitRoom(roomId, userId);
    }

    public IResponse enterRoom(String roomId, String userId, String pwd) throws RemoteException {
        IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().enterRoom(roomId, userId, pwd);
    }

    public IResponse quitRoom(String roomId, String userId) throws RemoteException {
       IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().quitRoom(roomId, userId);
    }

    public IResponse apply2SynchronizeBoard(String roomId, String userId) throws RemoteException{
        IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().apply2SynchronizeBoard(roomId, userId);
    }
    //message control method
     public IResponse sendMsg2All(String roomId, String userId, String msg)throws RemoteException{
         IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().receiveMsg(roomId, userId, msg);
     }
     public IResponse sendMsg2User(String roomId, String userId, String msg)throws RemoteException{
         IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().receivePrivateMsg(roomId,  userId, msg);
     }
    //draw executor
       //draw executor

     /**
     * apply to the user with the given id for the whiteboard lock
     * @param roomId the id of the specific room
     * @param ownerUserId the guy who owns the lock
     * @return (false, not null): when lock is not got
     *          (false, null): when expected lock-owner doesn't exsist
     *          true: when lock is got
     */
    public IResponse getLock(String roomId, String ownerUserId)throws RemoteException{
        IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().getLock(roomId, ownerUserId);
    }

    /**
     * inform other user in the same room , i got the lock
     * @return false: when other guy got a lock and is using it
     *          true: when every body know this.
     */
    public IResponse iGotLock(String roomId, String ownerUserId)throws RemoteException{
        IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().iGotLock(roomId, ownerUserId);
    }

    /**
     * ask other users is there any lock exsist, if not the user who sent
     * this message will generate a lock, own it, when the guy who is supposed
     * to keep a lock doesn't exsist.
     * @return false: when some body get a lock AND is used.
     *          true: when every body doesn't get a lock OR isn;t use it.
     */
    public IResponse weNeedLock(String roomId)throws RemoteException{
        IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().weNeedLock(roomId);
    }

    /**
     * Room need to draw a shape
     * @param roomId the specific room nees to draw a shape
     * @param shape the shape need to be draw
     * @return whethe the shape has been drawn successfully
     * @throws RemoteException
     */
    public IResponse draw(String roomId, IShape shape) throws RemoteException{
        IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().draw(roomId, shape);
    }


    /**
     * Room need to redo the former event
     * @param roomId the specific room nees to redo an event
     * @return whethe the event has been redone successfully
     */
    public IResponse redo(String roomId) throws RemoteException{
        IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().redo(roomId);
    }



    /**
     * Room need to undo the former event
     * @param roomId the specific room nees to undo an event
     * @return whethe the event has been undone successfully
     */
    public IResponse undo(String roomId) throws RemoteException{
        IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().undo(roomId);
    }

    public IResponse clear(String roomId) throws RemoteException{
        IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().clear(roomId);
        
    }

    public IResponse synchronizeBoard(String roomId,ArrayList<byte[]> undoList, ArrayList<byte[]> redoList)
        throws RemoteException{

        IRoom r = Application.getInstance().getRoom(roomId);
        return r.getRoomExecutor().synchronizeBoard(roomId, undoList, redoList);
    }
}
