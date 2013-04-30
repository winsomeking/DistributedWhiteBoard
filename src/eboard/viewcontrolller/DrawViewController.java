/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.viewcontrolller;

import eboard.itf.model.IResponse;
import eboard.itf.model.IRoom;
import eboard.model.IShape;
import eboard.model.Response;
import java.util.ArrayList;

/**
 *
 * @author elf
 */
 class DrawViewController {
    private IRoom room = null;
    
    
    DrawViewController(IRoom room){
        this.room = room;
    }
    /**
     * Room need to draw a shape
     * @param roomId the specific room nees to draw a shape
     * @param shape the shape need to be draw
     * @return whethe the shape has been drawn successfully
     */
    public IResponse draw(String roomId, IShape shape){
                                               // according to the roomID get to the right JFrame.then
                                              //  pass the argument to JFrame, then
            this.room.getRoomViewController().getFrame().receiveNewIShape(shape);// the JFrame pass the argument to JPanel,
                                             // who conducts the drawing
                                             //the argument --"shape" has two instance variables,
                                             // an shape variableand a color variable

            return new Response(true,"");
    }


    /**
     * Room need to redo the former event
     * @param roomId the specific room nees to redo an event
     * @return whethe the event has been redone successfully
     */
    public IResponse redo(String roomId){

            this.room.getRoomViewController().getFrame().RemoteRedoOrder();
            return new Response(true,"");
    }



    /**
     * Room need to undo the former event
     * @param roomId the specific room nees to undo an event
     * @return whethe the event has been undone successfully
     */
    public IResponse undo(String roomId){
       this.room.getRoomViewController().getFrame().RemoteUndoOrder();
        return new Response(true,"");
    }

    public IResponse synchronizeBoard(String roomId,ArrayList<byte[]> undoList, ArrayList<byte[]> redoList){
        try{
            this.room.getRoomViewController().
                    getFrame().getPanel().initDoUndoList(undoList, redoList);
        }catch(Exception e){
            System.out.println("synchronize erro when synchronize arrays.");
//            e.printStackTrace();
        }
        return new Response(true,"");
    }
}
