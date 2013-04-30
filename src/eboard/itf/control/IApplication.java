/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eboard.itf.control;

import eboard.model.IShape;
import eboard.itf.model.IResponse;
import eboard.itf.model.IRoom;
import eboard.itf.model.IUser;

import java.lang.String;
import java.util.HashMap;

/**
 * <b>IApplication </b> interface define one eboard application instance should
 * implemented later.
 * More importantly, only one application instance can be created in one eboard
 * application. So the <b>Singleton</b> design pattern should be used to ensure
 * this feature.
 */
public interface IApplication {


    /**
     * Return the instance of room whose roomId is the given roomId.
     * @param roomId the specific room's id
     * @return room instance with the given id
     */
    public IRoom getRoom(String roomId) ;

    /**
     * Add a room instance to the room list
     * @param room the room instance to be added to the room list
     * @return wheter the room added to list successfully
     */
    public IResponse addRoom(IRoom room) ;

    /**
     * Remove room from room list
     * @param roomId the given roomId
     * @return wheter the room remmoved from list successfully
     */
    public IResponse removeRoom(String roomId) ;

    /**
     * Init application environment variables. Create the "me" user
     * @param userName my user name
     * @param port the port listenning to for new service
     * @return wheter the port is avaliable 
     */
    public boolean init (String userName, int port );

    /**
     * Compute the rooms number in one application
     * @return room list size
     */
    public int roomNumbers ();

    /**
     * create an entity "room" and put it into roomlist of this application
     * roomId = IP + roomName, which is used to identify one room
     * room owner userId = current user Id
     * @param roomName room's name
     * @param pwd room's pwd
     * @return IResponse object to show whether room is created.
     */
    public IResponse createRoom( String roomName, String pwd) ;

    /**
     * I apply for entering room with the given ip, port, roomId.
     * @param ip the room's address
     * @param port the room's port
     * @param name the user's name
     * @param pwd null if the room doesn't have a pwd
     *             pwd  when the room has a pwd
     * @return whether enterRoom is allowed and successed.
     */
    public IResponse enterRoom(String ip, int port, String name,String pwd) ;


    /**
     * I quit the room with the given roomId
     * @param roomId the room I want to quit
     * @return whether quiting room is successful
     */
    public IResponse quitRoom(String roomId) ;

    /**
     * I will close the room, if i am the admin of this room
     * @param roomId the room id of which i want to close
     * @return whether the room has been closed successfully
     */
    public IResponse closeRoom(String roomId) ;


    /**
     * look up for the given roomid room, and get the room userlist
     * @param roomId to find the specific room
     * @return null if no such a room
     *          HashMap<String, IUser> user list if found
     */
     public HashMap<String, IUser> getUserList(String roomId);

    /**
     * enfoce the specific user to quit room only if the user is a admin
     * @param roomId the given room's id
     * @param userId the user to kick out
     * @return whether the user has been succuessfully kick out the given room
     */
    public IResponse enforceQuitRoom(String roomId, String userId);

   /**
    * I sent the "msg" to all users in the room whose id is roomId
    * @param roomId this is to point out which room to recieved msg
    * @param msg this is message 
    * @return whether the message has been sent to the room.
    */
    public IResponse sendMsg2All(String roomId, String msg);

   /**
    * I sent the "msg" to the user with given userId in the room whose id is roomId
    * @param roomId roomId this is to point out which room to recieved msg
    * @param userId the msg will be sent to this user
    * @param msg msg this is message
    * @return whether the message has been sent to the room and the user.
    */
    public IResponse sendMsg2User(String roomId,String userId, String msg);


    


    //whiteboard drawing operation

    /**
     * apply to the user with the given id for the whiteboard lock
     * @param roomId the guy who owns the lock
     * @return (false, not null): when lock is not got
     *          (false, null): when expected lock-owner doesn't exsist
     *          true: when lock is got
     */
    public IResponse getLock(String roomId);

    /**
     * inform other user in the same room , i got the lock
     * @return false: when other guy got a lock and is using it
     *          true: when every body know this.
     */
    public IResponse iGotLock(String roomId);

    /**
     * ask other users is there any lock exsist, if not the user who sent
     * this message will generate a lock, own it, when the guy who is supposed
     * to keep a lock doesn't exsist.
     * @return false: when some body get a lock AND is used.
     *          true: when every body doesn't get a lock OR isn;t use it.
     */
    public IResponse weNeedLock(String roomId);


    /**
     * release the lock of this room; set the lock's lock state to false
     * @param roomId the given room
     * @return true when successfully unlock 
     */
    public IResponse unlock(String roomId);

    /**
     * draw the shape in the room
     * @param roomId the given room
     * @param shape the given shape
     * @return true when draw successfully
     *          false when something wrong happend
     */
    public IResponse draw(String roomId, IShape shape);

        /**
     * Room need to redo the former event
     * @param roomId the specific room nees to redo an event
     * @return whethe the event has been redone successfully
     */
    public IResponse redo(String roomId);



    /**
     * Room need to undo the former event
     * @param roomId the specific room nees to undo an event
     * @return whethe the event has been undone successfully
     */
    public IResponse undo(String roomId);
}
