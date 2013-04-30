/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.control;

import eboard.itf.model.IResponse;
import eboard.itf.model.IRoom;
import eboard.model.IShape;
import eboard.itf.model.IUser;
import java.util.ArrayList;

/**
 * <b>IRoomExecutor </b> class is one of the core controller classes in eboard
 * application architecture. However, the control commands, like close and enter
 * room, send message, draw a picture, are dispathed into different sub-controllers,
 * room control command to the RoomCtrlExectuor, Message control command to the
 * MsgExecutor and draw control command to the DrawExector class.
 *
 * @author Mingyi Yang
 */
public class IRoomExecutor {

    //room control executor

    /**
     * Close the room with roomId
     * @param roomId the room to be closed
     * @return whether the room has been closed successfully
     */
    public IResponse closeRoom(String roomId){
        return RoomCtrlExecutor.closeRoom(roomId);
    }

    /**
     * Use whose id is userId quit the room (roomId)
     * @param roomId the room to be closed
     * @param userId the user's id who wants to quit the room
     * @return whether user has quit the room successfully
     */
    public IResponse quitRoom(String roomId, String userId){
        return RoomCtrlExecutor.quitRoom(roomId, userId);
    }

    /**
     * The user applies to entr the room with the given id and password
     * @param roomId the room applied to enter
     * @param userId the user's id who want to enter the room
     * @param pwd null if the room doesn't have a pwd
     *             pwd  when the room has a pwd
     * @return whether enterRoom is allowed and successed.
     */
    public IResponse enterRoom(String roomId, String userId, String pwd){
        return RoomCtrlExecutor.enterRoom(roomId, userId, pwd);
    }

    /**
     * Be enfoced to quit the room with the id
     * @param roomId the given room's id
     * @param userId the user to kick out
     * @return whether the user has been succuessfully kick out the given room
     */
    public IResponse enforceQuitRoom(String roomId, String userId){
        return RoomCtrlExecutor.enforceQuitRoom(roomId, userId);
    }

    /**
     * One user is entering the room
     * @param roomId the room the user is entering
     * @param user the user who is entering the room
     * @return always successfullly if no exception
     */
    public IResponse oneUserEntering(String roomId, IUser user){
        return RoomCtrlExecutor.oneUserEntering(roomId, user);
    }


    //message control method

    /**
     * recceive messeage which is sent to all from the user in the room
     * @param roomId  id of the room where the user sent the message
     * @param fromUserId id of the user who sent the message
     * @param msg the message which the user sent to all others in the room
     * @return always successfullly if no exception
     */
    public IResponse receiveMsg(String roomId, String fromUserId,  String msg){
        return MsgExecutor.receiveMsg(roomId, fromUserId, msg);
    }

    /**
     * recceive private messeage from the user in the room
     * @param roomId  id of the room where the user sent the message
     * @param fromUserId id of the user who sent the message
     * @param msg the message which the user sent in private
     * @return always successfullly if no exception
     */
     public IResponse receivePrivateMsg(String roomId, String fromUserId, String msg){
         return MsgExecutor.receivePrivateMsg(roomId, fromUserId, msg);
     }
      //draw executor



     public  IResponse apply2SynchronizeBoard(String roomId, String userId) {
        IRoom r = Application.getInstance().getRoom(roomId);
        return RoomCtrlExecutor.apply2SynchronizeBoard(roomId, userId);
    }
     /**
     * apply to the user with the given id for the whiteboard lock
     * @param roomId
     * @param ownerUserId Lock's owner id 
     * @return (false, not null): when lock is not got
     *          (false, null): when expected lock-owner doesn't exsist
     *          true: when lock is got
     */
    public IResponse getLock(String roomId, String ownerUserId){
        return RoomCtrlExecutor.getLock(roomId, ownerUserId);
    }

    /**
     * inform other user in the same room , i got the lock
     * @return false: when other guy got a lock and is using it
     *          true: when every body know this.
     */
    public IResponse iGotLock(String roomId, String ownerUserId){
        return RoomCtrlExecutor.iGotLock(roomId, ownerUserId);
    }

    /**
     * ask other users is there any lock exsist, if not the user who sent
     * this message will generate a lock, own it, when the guy who is supposed
     * to keep a lock doesn't exsist.
     * @return false: when some body get a lock AND is used.
     *          true: when every body doesn't get a lock OR isn;t use it.
     */
    public IResponse weNeedLock(String roomId){
        return RoomCtrlExecutor.weNeedLock(roomId);
    }


    /**
     * Room need to draw a shape
     * @param roomId the specific room nees to draw a shape
     * @param shape the shape need to be draw
     * @return whethe the shape has been drawn successfully
     */
    public IResponse draw(String roomId, IShape shape){

        return DrawExecutor.draw(roomId, shape);
    }


        /**
     * Room need to redo the former event
     * @param roomId the specific room nees to redo an event
     * @return whethe the event has been redone successfully
     */
    public IResponse redo(String roomId){

        return DrawExecutor.redo(roomId);
    }



    /**
     * Room need to undo the former event
     * @param roomId the specific room nees to undo an event
     * @return whethe the event has been undone successfully
     */
    public IResponse undo(String roomId){

        return DrawExecutor.undo(roomId);
    }


    public IResponse clear(String roomId){
        return RoomCtrlExecutor.clear(roomId);
    }


    public IResponse synchronizeBoard(String roomId,ArrayList<byte[]> undoList, ArrayList<byte[]> redoList){
        DrawExecutor.synchronizeBoard(roomId, undoList, redoList);
        return null;
    }
}
