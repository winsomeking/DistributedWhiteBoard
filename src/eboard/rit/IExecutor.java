/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.rit;

import eboard.itf.model.IResponse;
import eboard.model.IShape;
import eboard.itf.model.IUser;
import eboard.model.RoomInfo;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author elf
 */
public interface IExecutor extends Remote {

    ;

    //room control executor
    public IResponse closeRoom(String roomId)throws RemoteException;
    public IResponse quitRoom(String roomId, String userId)throws RemoteException;
    public IResponse enterRoom(String roomId, String userId, String pwd)throws RemoteException;
    public IResponse enforceQuitRoom(String roomId, String userId)throws RemoteException;
    public IResponse oneUserEntering(String roomId, IUser user)throws RemoteException;
    public RoomInfo getRoomInfo(String roomId) throws RemoteException;
    public IResponse apply2SynchronizeBoard(String roomId, String userId) throws RemoteException;

    public IResponse synchronizeBoard(String roomId, ArrayList<byte[]> undoList, ArrayList<byte[]> redoList)throws RemoteException;
    //message control method
     public IResponse sendMsg2All(String roomId, String userId, String msg)throws RemoteException;
     public IResponse sendMsg2User(String roomId, String userId, String msg)throws RemoteException;
    //draw executor

     /**
     * apply to the user with the given id for the whiteboard lock
     * @param roomId the room's id
     * @param ownerUserId the guy who owns the lock
     * @return (false, not null): when lock is not got
     *          (false, null): when expected lock-owner doesn't exsist
     *          true: when lock is got
     */
    public IResponse getLock(String roomId, String ownerUserId)throws RemoteException;

    /**
     * inform other user in the same room , i got the lock
     * @return false: when other guy got a lock and is using it
     *          true: when every body know this.
     */
    public IResponse iGotLock(String roomId, String ownerUserId)throws RemoteException;

    /**
     * ask other users is there any lock exsist, if not the user who sent
     * this message will generate a lock, own it, when the guy who is supposed
     * to keep a lock doesn't exsist.
     * @return false: when some body get a lock AND is used.
     *          true: when every body doesn't get a lock OR isn;t use it.
     */
    public IResponse weNeedLock(String roomId)throws RemoteException;

//    /**
//     * notify all users, lock is free
//     * @return  true: when every body doesn't get a lock OR isn;t use it.
//     */
//    public IResponse unLock(String roomId, String ownerUserId)throws RemoteException;


    /**
     * Room need to draw a shape
     * @param roomId the specific room nees to draw a shape
     * @param shape the shape need to be draw
     * @return whethe the shape has been drawn successfully
     * @throws RemoteException
     */
    public IResponse draw(String roomId, IShape shape) throws RemoteException;



            /**
     * Room need to redo the former event
     * @param roomId the specific room nees to redo an event
     * @return whethe the event has been redone successfully
     */
    public IResponse redo(String roomId) throws RemoteException;



    /**
     * Room need to undo the former event
     * @param roomId the specific room nees to undo an event
     * @return whethe the event has been undone successfully
     */
    public IResponse undo(String roomId) throws RemoteException;


    public IResponse clear(String roomId) throws RemoteException;
}
