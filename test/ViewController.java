
import eboard.viewcontrolller.TestFrameController;
import java.util.HashMap;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class ViewController {

    private static HashMap <String, TestFrameController> frames = null;
    private static ViewController vc = null;
    private ViewController(){
        frames = new HashMap<String,TestFrameController>();
    }

    private static ViewController getInstance(){
        if(vc == null){
            vc = new ViewController();
        }
        return vc;
    }

    public static TestFrameController getViewControllerOf(String roomId){
        return frames.get(roomId);
    }
    

         // Variables declaration - do not modify
//    static private javax.swing.JButton btSend;
//    static private javax.swing.JComboBox cbxToWho;
//    static private javax.swing.JTextArea jtaReceive;
//    static private javax.swing.JTextArea jtaSend;
//    static private javax.swing.JList lstUser;
}
