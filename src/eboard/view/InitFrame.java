/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InitFrame.java
 *
 * Created on 2010-11-4, 20:19:49
 */

package eboard.view;

import eboard.control.Application;
import eboard.itf.control.IApplication;
import eboard.itf.model.IResponse;
import javax.imageio.ImageIO;

/**
 *
 * @author elf
 */
public class InitFrame extends javax.swing.JFrame {

    /** Creates new form InitFrame */
    public InitFrame() {
        initComponents();
        this.setResizable(false);
        this.setBounds(400, 100, 500, 300);
        try{
            this.setIconImage(ImageIO.read(
                getClass().getResource("/eboard/resource/snail2.bmp")));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        this.setTitle("eBoard");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jmiNew = new javax.swing.JMenuItem();
        jmiConnect = new javax.swing.JMenuItem();
        jmiQuit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jmiAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("frmInit"); // NOI18N
        setResizable(false);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/eboard/resource/supersnail.gif"))); // NOI18N

        jMenu1.setText("File(F)");

        jmiNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        jmiNew.setText("New Room");
        jmiNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiNewActionPerformed(evt);
            }
        });
        jMenu1.add(jmiNew);

        jmiConnect.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        jmiConnect.setText("Connect to... ");
        jmiConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiConnectActionPerformed(evt);
            }
        });
        jMenu1.add(jmiConnect);

        jmiQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jmiQuit.setText("Quit");
        jmiQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiQuitActionPerformed(evt);
            }
        });
        jMenu1.add(jmiQuit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help(H)");

        jmiAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jmiAbout.setText("About...");
        jmiAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiAboutActionPerformed(evt);
            }
        });
        jMenu2.add(jmiAbout);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmiQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiQuitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jmiQuitActionPerformed

    private void jmiAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiAboutActionPerformed
        // TODO add your handling code here:
        AboutBox.showAbout();
    }//GEN-LAST:event_jmiAboutActionPerformed

    private void jmiNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiNewActionPerformed
        // TODO add your handling code here:
        
        IApplication ap = Application.getInstance();
   /**
         * check room name
         */
         String roomName = showInputDialog(
                 "Please input room Name:","Room Name");
        if (roomName == null) return;
        while(roomName.trim().length() == 0) {
            this.showMsgDialog("Room name can not be empty, please check and input again.");
            roomName = showInputDialog(
                 "Please input room Name:","Room Name");
            if(roomName == null) return;
        }

         /**
         * check room name
         */
         String pwd = showInputDialog(
                 "Please input room password:","Room Password");
        if (pwd == null) pwd = "";
        
        IResponse r = ap.createRoom(roomName.trim(), pwd);
        if(r.isSuccess()) this.dispose();
        else
            this.showMsgDialog("Fail to create room\n"+r.getResponseMsg());
        //ap.createRoom(null, null)
    }//GEN-LAST:event_jmiNewActionPerformed

    private void jmiConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiConnectActionPerformed
        // TODO add your handling code here:
         IApplication ap = Application.getInstance();

         /**
         * check ip address
         */
        String ip = showInputDialog(
                "Please input IP address (like \"192.168.1.3\"):","IP address");
        if (ip == null) return;
        while(!checkIp(ip)) {
            this.showMsgDialog("IP format is not correct, please check and input again.");
            ip = showInputDialog(
                "Please input IP address (like \"192.168.1.3\"):","IP address");
            if(ip == null) return;
        }

        /**
         * check port number
         */
         String port = showInputDialog(
                "Please input remote port(1025-65534):","Port Number");
        if (port == null) return;
        while(!checkPort(port)) {
            this.showMsgDialog("port format is not correct, please check and input again.");
            port = showInputDialog(
                "Please input remote port(1025-65534):","Port Number");
            if(port == null) return;
        }

   /**
         * check room name
         */
         String roomName = showInputDialog(
                 "Please input room Name:","Room Name");
        if (roomName == null) return;
        while(roomName.trim().length() == 0) {
            this.showMsgDialog("Room name can not be empty, please check and input again.");
            roomName = showInputDialog(
                 "Please input room Name:","Room Name");
            if(roomName == null) return;
        }

         /**
         * check room name
         */
         String pwd = showInputDialog(
                 "Please input room password:","Room Password");
        if (pwd == null) pwd = "";


         IResponse r = ap.enterRoom(ip, Integer.parseInt(port),roomName, pwd);
         if (r.isSuccess()) this.dispose();
         else this.showMsgDialog("Fail to enter room. "+r.getResponseMsg());
    }//GEN-LAST:event_jmiConnectActionPerformed

    private void showMsgDialog(String msg){
        javax.swing.JOptionPane.showMessageDialog(this,
                    msg);
    }

     private String showInputDialog(String msg, String title){
        return javax.swing.JOptionPane.showInputDialog(this,
                msg, title,
                javax.swing.JOptionPane.DEFAULT_OPTION);
    }

    /**
     * check ip address format
     * @param  ip the ip to check up
     * @return whether the format of ip address is correct
     */
    private boolean checkIp(String ip){
        String[] s = ip.split("\\.");
        try{
            if( s== null || s.length!=4) return false;
            for( int i = 0; i < 4 ; i ++){
                int a = Integer.parseInt(s[i]);
                if(a<0||a>255) return false;
            }
        }catch(NumberFormatException nfe){
            nfe.printStackTrace();
            return false;
        }
        return true;
    }

        /**
     * check port number format
     * @param  port , must between 1024 and 65535
     * @return whether the format of port number is correct
     */
    private boolean checkPort(String port){
        try{
            if( port== null ) return false;
                int a = Integer.parseInt(port);
                if(a<1024||a>65535) return false;
        }catch(NumberFormatException nfe){
            nfe.printStackTrace();
            return false;
        }
        return true;
    }

    /**
    * @param args the command line arguments
    */
//    public static void main(String args[]) {
//           String[] ss= "123.4".split("\\.");
//            System.out.println(ss.length);
//            for(int i =0; i < 2; i ++){
//                System.out.println(ss[i]);
//            }
//
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new InitFrame().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jmiAbout;
    private javax.swing.JMenuItem jmiConnect;
    private javax.swing.JMenuItem jmiNew;
    private javax.swing.JMenuItem jmiQuit;
    // End of variables declaration//GEN-END:variables

}
