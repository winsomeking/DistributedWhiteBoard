package eboard.viewcontrolller;


import eboard.view.TestFrame;
import eboard.control.Application;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class TestFrameController {
    TestFrame tf = null;

    TestFrameController(TestFrame tf){

        this.tf = tf;
        System.out.println("testframecontroller");
       //this.tf.sendMsg("12");
    }

    void sendMsg(String msg){
        this.tf.sendMsg(msg);
    }

    void createRoom(String ip, int port, String roomName, String pwd){
//       Application ap =  Application.getInstance();
//       ap.init("test", 20000);
//       ap.createRoom(port, roomName, pwd);
    }


}
