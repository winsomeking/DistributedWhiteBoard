/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.control;

import eboard.itf.model.IResponse;
import eboard.itf.model.IRoom;
import eboard.model.IShape;
import eboard.model.Response;
import eboard.viewcontrolller.IRoomViewController;
import java.util.ArrayList;

/**
 * <b>DrawExecutor </b>class to execute the the draw commands
 * @author Mingyi Yang
 */
class DrawExecutor {

    /**
     * Room need to draw a shape
     * @param roomId the specific room nees to draw a shape
     * @param shape the shape need to be draw
     * @return whethe the shape has been drawn successfully
     */
    public static IResponse draw(String roomId, IShape shape){
        IRoom r = Application.getInstance().getRoom(roomId);
        IResponse rsp = new Response();
        if ( r== null )
            return new Response(false, "room: "+roomId+"didn't draw.");
        IRoomViewController rvc = r.getRoomViewController();
        //no logic controll
        rsp.combine(rvc.draw(roomId, shape));
        return rsp;
    }


    /**
     * Room need to redo the former event
     * @param roomId the specific room nees to redo an event
     * @return whethe the event has been redone successfully
     */
    public static IResponse redo(String roomId){

         IRoom r = Application.getInstance().getRoom(roomId);
        IResponse rsp = new Response();
        if ( r== null )
            return new Response(false, "room: "+roomId+"didn't draw.");
        IRoomViewController rvc = r.getRoomViewController();
        //no logic controll
        rsp.combine(rvc.redo(roomId));
        return rsp;

    }



    /**
     * Room need to undo the former event
     * @param roomId the specific room nees to undo an event
     * @return whethe the event has been undone successfully
     */
    public static IResponse undo(String roomId){
        
         IRoom r = Application.getInstance().getRoom(roomId);
        IResponse rsp = new Response();
        if ( r== null )
            return new Response(false, "room: "+roomId+"didn't draw.");
        IRoomViewController rvc = r.getRoomViewController();
        //no logic controll
        rsp.combine(rvc.undo(roomId));
        return rsp;
    }

    public static IResponse synchronizeBoard(String roomId, ArrayList<byte[]> undoList, ArrayList<byte[]> redoList){

        IRoom r = Application.getInstance().getRoom(roomId);
        IResponse rsp = new Response();
        if ( r== null )
            return new Response(false, "room: "+roomId+"didn't draw.");
        IRoomViewController rvc = r.getRoomViewController();
        //no logic controll
        rsp.combine(rvc.synchronizeBoard(roomId, undoList, redoList));
        return rsp;
    }
}
