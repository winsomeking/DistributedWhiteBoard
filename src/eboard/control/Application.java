/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eboard.control;

import eboard.itf.control.IApplication;
import eboard.model.IShape;
import eboard.itf.model.IResponse;
import eboard.itf.model.IRoom;
import eboard.itf.model.IUser;
import eboard.viewcontrolller.IRoomViewController;
import eboard.model.Response;
import eboard.model.Room;
import eboard.model.RoomInfo;
import eboard.model.User;
import eboard.model.WhiteBoardLock;

import eboard.rit.IExecutor;
import eboard.view.ConnectingFrame;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <b>Application </b>class which is one of the core control class, implemented
 * the IApplication interface. And the <b>Singleton</b> design pattern used in
 * this class to ensure one eboard application can only create one Application
 * instance.
 * @author Mingyi Yang
 */
public class Application implements IApplication{

    private static Application me = null;
    private HashMap<String, IRoom> rooms = null;
    public static String IP = null;//local ip
    public static IUser user = null;
    public int port = 3010;
    
    private Application() {
        
        rooms = new HashMap<String, IRoom>();
        try{
         IP = java.net.InetAddress.getLocalHost().getHostAddress();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Init application environment variables. Create the "me" user
     * @param userName my user name
     * @param port the port listenning to for new service
     * @return wheter the port is avaliable
     */
    public boolean init (String userName, int port ){
        RemoteObjectsManager rom = RemoteObjectsManager.getInstance();
        if (rom.bind(port) == false) return false;
        this.port = port;
        user = new User(IP,port,userName);
        //bind variable: executor 
        
        //IRoomViewController.getInstance().showMainFrame();
        return true;
    }

    /**
     * Create one Application class instance when there is no Application object
     * or Application object exsists in this application.
     * @return application application instance
     */
    public static Application getInstance() {

        if (me == null) {
            me = new Application();
        }
        return me;
    }

    /**
     * return the instance of room whose roomId is the given roomId.
     * @param roomId the room's id
     * @return room instance with the given room id
     */
    public IRoom getRoom(String roomId) {
        return this.rooms.get(roomId);
    }

    /**
     * compute rooms number
     * @return rooms number
     */
    public int roomNumbers (){
        return this.rooms.keySet().size();
    }
    /**
     * local method add the given room with id to local room list
     * @param room the room instance to be added to the room list
     * @return success or not and the message
     */
    public IResponse addRoom(IRoom room) {
        if(this.rooms.get(room.getRoomId()) != null)
            return new Response(false,
                 "Same name room already exsists when enter new room.");//if th
        this.rooms.put(room.getRoomId(), room);
         return new Response(true,"Successfully entered room.");//if th
    }

    /**
     * local method remove the given room with id(roomId)
     * @param roomId this is to specify the room
     * @return success or not and the message
     */
    public IResponse removeRoom(String roomId) {
        
       return this.rooms.remove(roomId)==null?
             new Response(true,"No such a room to remove, room id: "+ roomId):
             new Response(true,"remove successfully room id: "+roomId);
    }

    /**
     * create an entity "room" and put it into roomlist of this application
     * roomId = IP + roomName, which is used to identify one room
     * room owner userId = current user Id
     * @param roomName room's name
     * @param pwd
     * @return IResponse object to show whether room is created.
     */
    public IResponse createRoom( String roomName, String pwd) {
        String roomId = IP+port+roomName;
        HashMap<String, IUser> iu = new HashMap<String, IUser>();
        if(rooms.get(roomId) != null)
            return new Response(false,
                "Same name room already exsists when created new room.");//if th

        iu.put(user.getUserId(),user);
        IRoom r = new Room(new RoomInfo(IP,port,roomName,user,iu , pwd));
        this.rooms.put(roomId, r);
        r.setRoomViewController(new IRoomViewController(r));


        r.getRoomViewController().createPwdRoom(roomId);
        // room.getRoomExecutor().createPwdRoom(user.getUserId(), pwd);//
        
        return new Response(true,"New room created successfully.");
    }

    /**
     * I apply for entering room with the given ip, port, roomId.
     * @param ip the room's address
     * @param port the room's port
     * @param roomName the room's name
     * @param pwd null if the room doesn't have a pwd
     *             pwd  when the room has a pwd
     * @return whether enterRoom is allowed and successed.
     */
    public IResponse enterRoom(String ip, int port, String roomName,String pwd) {
        final String roomId = ip+port+roomName;
        IRoom r = rooms.get(roomId);

        final IResponse rsp = new Response();
         if( r != null)
            return new Response(false,
                "Already in this room, roomId: "+roomId);//if th
//        new Thread(new Runnable(){
//            public void run(){
                ConnectingFrame.getInstance().popout(roomId,
                        400,300);
//            }
//        }).start();
        
        IExecutor rExecutor = RemoteObjectsManager.getInstance().
                getRemoteExecutor(roomId, null, ip, port);
        ConnectingFrame.getInstance().setVisible(false);
        if(rExecutor == null){
            rsp.combine(new Response(false,
                "Can not connect to the room: "+ip+":"+port+":"+roomName));

            return rsp;
        }
        RoomInfo ri = null;
        try{//inform the remote
             ri = rExecutor.getRoomInfo(roomId);
             rsp .combine(rExecutor.enterRoom(roomId, user.getUserId(), pwd));
        }catch(RemoteException re){
            return rsp.combine( new Response(false, re.getMessage()));

        }
        if(rsp.isSuccess()){//connect success
             r = new Room(new RoomInfo(ip,port,ri.getRoomName(),ri.getOwner(),
                ri.getUserList(),  ri.getPwd()));
             r.addUser(user);
             rooms.put(roomId, r);
             r.setRoomViewController(new IRoomViewController(r));
             informAll(r.getRoomInfo(), 0);
             //updating local GUI
             r.getRoomViewController().enterRoom(roomId, user.getUserId(), pwd);
              informUser(ri, r.getRoomOwnerUserId(), 2);
        }
       

        return rsp;
    }

    public IResponse enterRoomTask(final IExecutor rExecutor,
            final String roomName,
            final String pwd,
            final String ip,
            final int port){
        final IResponse rsp = new Response();
        final Timer t = new Timer();
        final String roomId = ip+port+roomName;
        final int lag = 6000;//6 seconds
        t.schedule(new TimerTask(){
            public void run(){
                
             }
         } ,
         lag
        );
         return rsp;
    }


    /**
     * I quit the room with the given roomId
     * @param roomId the room I want to quit
     * @return whether quiting room is successful
     */
    public IResponse quitRoom(String roomId) {
        IRoom r = rooms.get(roomId);
        IResponse rsp = null;
         if( r == null)
            return new Response(false,
                "not in this room, roomId: "+roomId);//if th
        RoomInfo ri = r.getRoomInfo();
        rsp = this.informAll(ri, 1);//inform other user, i  quit the room
        this.rooms.remove(ri.getRoomId());
        rsp.combine(r.getRoomViewController().quitRoom(roomId, this.user.getUserId()));
        return rsp;


    }

    /**
     * I will close the room, if i am the admin of this room
     * @param roomId the room id of which i want to close
     * @return whether the room has been closed successfully
     */
    public IResponse closeRoom(String roomId) {
        IRoom r = rooms.get(roomId);
        IResponse rsp = null;
        if(r == null) return
                 new Response(false,"The room: "+roomId+" doesn't exsist.");
        RoomInfo ri = r.getRoomInfo();
        boolean meIsAdmin = ri.getOwner().equals(user);


        if(!meIsAdmin) return 
                new Response(false,"You are not the admin of this room: "+roomId);
        this.informAll(ri, 2);//inform all user, I close room;
        this.rooms.remove(ri.getRoomId());
        r.getRoomViewController().getFrame().close();

        return rsp;

    }



    /**
     * inform all users in room with diffenet operationType
     *
     * @param roomInfo room information which is required to inform
     * @param operationType int data type. which repsente for different operation
     *                       0： one user enter the room
     *                       1:  one user quit the room
     *                       2:  the admin close the room
     *                       3:  we need a lock?
     *                       4:  i got the lock
     *                       5:  draw a shape
     *                       6:  redo
     *                       7:  undo
     *                       8:  clear white board
     * @return whether the notifications have been all distributed
     */
    private IResponse informAll(RoomInfo roomInfo, int operationType){
        RemoteObjectsManager rom = RemoteObjectsManager.getInstance();
        HashMap<String, IUser> userlist = roomInfo.getUserList();
        Iterator iter = userlist.entrySet().iterator();
        String roomId = roomInfo.getRoomId();
        String ip = roomInfo.getIp();
        int port = roomInfo.getPort();
        IExecutor executor = null;
        String userId = null;
        String myUserId = user.getUserId();
        IUser u = null;
        IResponse rspLoop = new Response(true,null);
        //nofiy all user in this room
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            userId = (String)entry.getKey();
            u = (IUser)entry.getValue();
            if( myUserId.equals(userId)) continue;//do not send message to my self
            executor = rom.getRemoteExecutor(roomId,userId,u.getIP(),u.getPort());
            try{
                switch(operationType){
                    case 0://i enter room.
                        executor.oneUserEntering(roomId, user);
                        break;
                    case 1://i quit the room
                        executor.quitRoom(roomId, myUserId);
                        break;
                    case 2://i close the room
                        executor.closeRoom(roomId);
                        break;
                    case 3:
                        rspLoop.combine(executor.weNeedLock(roomId));
                        break;
                    case 4:
                        rspLoop.combine(executor.iGotLock(roomId,myUserId));
                        break;
//                    case 5:
//                        rspLoop.combine(executor.draw(roomId,myUserId));
//                        return rspLoop;
                    case 6:
                        rspLoop.combine(executor.redo(roomId));
                        break;
                    case 7:
                        rspLoop.combine(executor.undo(roomId));
                        break;
                    case 8:
                        rspLoop.combine(executor.clear(roomId));
                        break;
                    default: ;

                }
            }catch(Exception e){
                e.printStackTrace();
                return new Response(false, e.getMessage());
            }
       }

        return new Response(true, "succusslly inform all users in room "+
                            roomId+" .  Operation type: "+operationType);



    }

    

    public void informUserWithThreads(final IExecutor executor,
            final String roomId, final String userId, final int optype){
        new Thread(new Runnable(){
            public void run(){
                try{
                    switch(optype){
                    case 0:
                        executor.enforceQuitRoom(roomId, userId);
                        break;
                    }
                }catch(Exception e){
                    System.out.println("exception happen when inform other user"
                            +" parallel");
                }
                
            }
        }).start();
        
    }


    /**
     * inform the user in the given room with roomInfo to operate the type
     * @param roomInfo the information of this room
     * @param user the user who  this operation execute on
     * @param operationType int type 0: enforce the toUser
     *                                1: get room lock from user
     *                                2: synchronize the whiteboard
     * @return
     */
    private IResponse informUser(RoomInfo roomInfo, String toUserId, int operationType){
        RemoteObjectsManager rom = RemoteObjectsManager.getInstance();

        String roomId = roomInfo.getRoomId();
        IUser toUser = roomInfo.getUserList().get(toUserId);
        String toIp = toUser.getIP();
        int toPort = toUser.getPort();
        IExecutor executor = null;
        //String toUserId = toUser.getUserId();
        String myUserId = user.getUserId();

        IResponse rsp = null;

            
            executor = rom.getRemoteExecutor(roomId,toUserId,toIp,toPort);
            try{
                switch(operationType){
                    case 0://enforce this user to quit room
                        if( user.equals(roomInfo.getOwner()))
                            return new Response(false,"Not the room admin.");//do not send message to my self
                         informUserWithThreads(executor,roomId,myUserId,operationType);
                        break;
                    case 1://get room lock from user
                        rsp = executor.getLock(roomId, myUserId);
                        return rsp;
                    case 2:
                        rsp = executor.apply2SynchronizeBoard(roomId, myUserId);
                        return rsp;
                    default: ;

                }
            }catch(Exception e){
                return new Response(false, e.getMessage());
            }


        return new Response(true, "succusslly inform the user: "+toUserId+
                " in the room "+roomId+" .  Operation type: "+operationType);
    }



/**
 * look up for the given roomid room, and get the room userlist
 * @param roomId to find the specific room
 * @return null if no such a room
 *          HashMap<String, IUser> user list if found
 */
    public HashMap<String, IUser> getUserList(String roomId){
        IRoom r = rooms.get(roomId);
        if( r == null ) return null;//no given room
        return r.getUserList();
    }


    /**
     * enfoce the specific user to quit room only if the user is a admin
     * @param roomId the given room's id
     * @param userId the user to kick out
     * @return whether the user has been succuessfully kick out the given room
     */
    public IResponse enforceQuitRoom(String roomId, String userId){
        IRoom r = rooms.get(roomId);
        IResponse rsp = null;
        RoomInfo ri = r.getRoomInfo();
        boolean meIsAdmin = ri.getOwner().equals(user);
         if(!meIsAdmin) return
                new Response(false,"You are not the admin of this room: "+roomId);

        this.informUser(ri,userId, 0);//inform the user to quit;
        this.rooms.remove(ri.getRoomId());
        rsp = r.getRoomViewController().closeRoom(roomId);
        return rsp;
    }

   /**
    * I sent the "msg" to all users in the room whose id is roomId
    * @param roomId this is to point out which room to recieved msg
    * @param msg this is message
    * @return whether the message has been sent to the room.
    */
    public IResponse sendMsg2All(String roomId, String msg){
        RemoteObjectsManager rom = RemoteObjectsManager.getInstance();
        RoomInfo roomInfo = this.getRoom(roomId).getRoomInfo();
        HashMap<String, IUser> userlist = roomInfo.getUserList();
        Iterator iter = userlist.entrySet().iterator();
        String toIp =null;//roomInfo.getIp();
        int toPort = -1;//roomInfo.getPort();
        IUser toUser = null;
        IExecutor executor = null;
        String toUserId = null;
        String myUserId = user.getUserId();

        //nofiy all user in this room
        while (iter.hasNext()) {
            Map.Entry<String, IUser> entry = (Map.Entry) iter.next();
            toUserId = entry.getKey();
            if( myUserId.equals(toUserId)) continue;//do not send message to my self
            toUser = entry.getValue();
            toIp = toUser.getIP();
            toPort = toUser.getPort();
            executor = rom.getRemoteExecutor(roomId,toUserId,toIp,toPort);
            try{
                executor.sendMsg2All(roomId, myUserId, msg);
                 
            }catch(Exception e){
                e.printStackTrace();
                return new Response(false, e.getMessage());
            }
       }
       IRoomViewController rvc = this.getRoom(roomId).getRoomViewController();
       rvc.sendMsg2All(roomId, msg);
       
        return new Response(true, "succusslly inform all users in room "+
                            roomId+" .");


    }

   /**
    * I sent the "msg" to the user with given userId in the room whose id is roomId
    * @param roomId roomId this is to point out which room to recieved msg
    * @param toUserId the msg will be sent to this user
    * @param msg msg this is message
    * @return whether the message has been sent to the room and the user.
    */
    public IResponse sendMsg2User(String roomId, String toUserId, String msg){
        RemoteObjectsManager rom = RemoteObjectsManager.getInstance();
        RoomInfo ri = this.getRoom(roomId).getRoomInfo();
        String ip = ri.getIp();
        int port = ri.getPort();
        IUser toUser = ri.getUserList().get(toUserId);
        IExecutor executor = rom.getRemoteExecutor(roomId,toUserId,toUser.getIP(),toUser.getPort());
        try{
               executor.sendMsg2User(roomId, user.getUserId(), msg);
        }catch(Exception e){
            return new Response(false, e.getMessage());
        }
       IRoomViewController rvc = this.getRoom(roomId).getRoomViewController();
       rvc.sendMsg2User(roomId,user.getUserId(), msg);
        return new Response(true, "succusslly send msg to the user: "+toUserId+
                " in the room "+roomId+" .");
    }


        /**
     * apply to the user with the given id for the whiteboard lock
     * @param roomId
     * @return (false, not null): when lock is not got
     *          (false, null): when expected lock-owner doesn't exsist
     *          true: when lock is got
     */
    public IResponse getLock(String roomId){
        IRoom r = rooms.get(roomId);
        if(r==null) return new Response(false,"no such a room: "+roomId);
        WhiteBoardLock l = r.getLockInfo();
        String lockOwnerUserId = r.getRoomOwnerUserId();
        IResponse rspLock = new Response(true,null);
        if(!user.getUserId().equals(lockOwnerUserId)&&lockOwnerUserId != null){
            rspLock = this.informUser(r.getRoomInfo(), lockOwnerUserId, 1);//get lock form user
        }
        if(!rspLock.isSuccess()){
            if(rspLock.getResponseMsg() == null) {//if no one get lock
                rspLock = this.weNeedLock(roomId);//ask every other one, whether they get a lock
                if(rspLock.isSuccess()){//if every need a lock
                    rspLock = this.iGotLock(roomId);// i own the m
                    if(!rspLock.isSuccess()) return rspLock;
                }
            }

            return rspLock;
        }
        //set lock user id an state
        l.setLockOwnerUserId(user.getUserId());
        l.setLocked(true);
        this.iGotLock(roomId);
        return rspLock;
    }

    /**
     * inform other user in the same room , i got the lock
     * @return false: when other guy got a lock and is using it
     *          true: when every body know this.
     */
    public IResponse iGotLock(String roomId){
        IRoom r = rooms.get(roomId);
        if(r==null) return new Response(false,"no such a room: "+roomId);
        return this.informAll(r.getRoomInfo(), 4);//inform all others, i get the lock
    }

    /**
     * ask other users is there any lock exsist, if not the user who sent
     * this message will generate a lock, own it, when the guy who is supposed
     * to keep a lock doesn't exsist.
     * @return false: when some body get a lock AND is used.
     *          true: when every body doesn't get a lock OR isn;t use it.
     */
    public IResponse weNeedLock(String roomId){
        IRoom r = rooms.get(roomId);
        if(r==null) return new Response(false,"no such a room: "+roomId);
        return this.informAll(r.getRoomInfo(), 3);//
    }



    /**
     * release the lock of this room; set the lock's lock state to false
     * @param roomId the given room
     * @return true when successfully unlock
     */
    public IResponse unlock(String roomId){
        IRoom r = rooms.get(roomId);
        if(r==null) return new Response(false,"no such a room: "+roomId);
        r.getLockInfo().setLocked(false);//release white board lock
        return new Response(true, "");
    }


    /**
     * draw the shape in the room
     * @param roomId the given room
     * @param shape the given shape
     * @return true when draw successfully
     *          false when something wrong happend
     */
    public IResponse draw(String roomId, IShape shape){

        
        IRoom r = this.rooms.get(roomId);
        if(r==null) return new Response(false,"no such a room: "+roomId);

        RemoteObjectsManager rom = RemoteObjectsManager.getInstance();
        HashMap<String, IUser> userlist = r.getUserList();
        Iterator iter = userlist.entrySet().iterator();

        String ip = r.getRoomInfo().getIp();
        int port = r.getRoomInfo().getPort();
        IExecutor executor = null;
        String userId = null;
        String myUserId = user.getUserId();
        IUser u = null;
        IResponse rspLoop = new Response();//new Response(true,null);
        
        WhiteBoardLock l = r.getLockInfo();

        if(this.getLock(roomId).isSuccess()||true){
//            rspLoop = r.getRoomViewController().draw(roomId, shape);
            //nofiy all user in this room
            while (iter.hasNext()) {
                Map.Entry<String,IUser> entry = (Map.Entry<String,IUser>) iter.next();
                userId = entry.getKey();
                u = entry.getValue();
                if( myUserId.equals(userId)) continue;//do not send message to my self

                executor = rom.getRemoteExecutor(roomId,userId,u.getIP(),u.getPort());
                try{
                    rspLoop.combine(executor.draw(roomId,shape));
                    
                }catch(Exception e){
                    e.printStackTrace();
                    return new Response(false, e.getMessage());
                }
           }
           this.unlock(roomId);
        }else{
            rspLoop = new Response(false, "can not get the white board right now.");
            IRoomViewController rvc = this.getRoom(roomId).getRoomViewController();
           // rvc.undo(roomId);
        }
       

        return rspLoop;

    }



    /**
     * Room need to redo the former event
     * @param roomId the specific room nees to redo an event
     * @return whethe the event has been redone successfully
     */
    public IResponse redo(String roomId){
        IRoom r = rooms.get(roomId);
        if(r==null) return new Response(false,"no such a room: "+roomId);
        IResponse rspRedo = new Response();
        WhiteBoardLock l = r.getLockInfo();
        if(this.getLock(roomId).isSuccess()||true){
//            rspRedo = r.getRoomViewController().redo(roomId);
            //inform remote to draw
            this.informAll(r.getRoomInfo(), 6);
            this.unlock(roomId);
        }else{
            rspRedo = new Response(false, "can not get the white board right now.");
        }

        return rspRedo;
    }



    /**
     * Room need to undo the former event
     * @param roomId the specific room nees to undo an event
     * @return whethe the event has been undone successfully
     */
    public IResponse undo(String roomId){
        IRoom r = rooms.get(roomId);
        if(r==null) return new Response(false,"no such a room: "+roomId);
        IResponse rspUndo = new Response();
        WhiteBoardLock l = r.getLockInfo();
        if(this.getLock(roomId).isSuccess()||true){
//            r.getRoomViewController().undo(roomId);
            //inform remote to draw
            this.informAll(r.getRoomInfo(), 7);
            this.unlock(roomId);
         }else{
            rspUndo = new Response(false, "can not get the white board right now.");
        }

        return rspUndo;
    }



//    public static void main(String[] args) throws Exception{
//        IP = java.net.InetAddress.getLocalHost().getHostAddress();
//        System.out.println(IP+java.net.InetAddress.getLocalHost().getHostName());
//    }


    public IResponse clearBoard(String roomId){
        IRoom r = rooms.get(roomId);
        if(r==null) return new Response(false,"no such a room: "+roomId);
        IResponse rspClear = new Response();
        return rspClear.combine(informAll(r.getRoomInfo(),8));
    }


    public IResponse quitAllRoom(){
        Iterator iter = rooms.entrySet().iterator();
        IResponse rsp = new Response();
        //nofiy all user in this room
        while (iter.hasNext()) {
            Map.Entry<String, IRoom> entryRoom = (Map.Entry) iter.next();
            String roomId = entryRoom.getKey();
            IRoom r = entryRoom.getValue();
            if(r!= null){
                if( user.getUserId().equals(
                        r.getRoomInfo().getOwner().getUserId())){
                    rsp.combine(this.closeRoom(roomId));
                }else{
                    rsp.combine(this.quitRoom(roomId));
                }
            }
            
            
       }
        return rsp;
    }
}
