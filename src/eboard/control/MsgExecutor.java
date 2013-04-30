/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.control;

import eboard.itf.model.IResponse;
import eboard.itf.model.IRoom;
import eboard.model.Response;
import eboard.viewcontrolller.IRoomViewController;

/**
 * 
 * @author elf
 */
 class MsgExecutor {


     /**
     * recceive messeage which is sent to all from the user in the room
     * @param roomId  id of the room where the user sent the message
     * @param fromUserId id of the user who sent the message
     * @param msg the message which the user sent to all others in the room
     * @return always successfullly if no exception
     */
     public static IResponse receiveMsg(String roomId, String fromUserId, String msg){
        IRoom r = Application.getInstance().getRoom(roomId);
        IResponse rsp = new Response();
        if ( r== null )
            return new Response(false, "room: "+roomId+"does not exsist.");
        IRoomViewController rvc = r.getRoomViewController();
        rsp.combine(rvc.recieveMsg(roomId, fromUserId, msg));
        return rsp;
     }


      /**
     * recceive private messeage from the user in the room
     * @param roomId  id of the room where the user sent the message
     * @param fromUserId id of the user who sent the message
     * @param msg the message which the user sent in private
     * @return always successfullly if no exception
     */
     public static IResponse receivePrivateMsg(String roomId,  String fromUserId, String msg){
         IRoom r = Application.getInstance().getRoom(roomId);
        IResponse rsp = new Response();
        if ( r== null )
            return new Response(false, "room: "+roomId+"does not exsist.");
        IRoomViewController rvc = r.getRoomViewController();
        rsp.combine(rvc.recievePrivateMsg(roomId, fromUserId, msg));
        return rsp;
     }
}
