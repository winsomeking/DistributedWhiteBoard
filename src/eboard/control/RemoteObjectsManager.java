/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package eboard.control;

import eboard.rimpl.ExecutorImpl;
import eboard.rit.IExecutor;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;

/**
 * only one instance in one application
 * @author elf
 */
public class RemoteObjectsManager {

    HashMap<String, IExecutor> pool = null;
    private static RemoteObjectsManager rom = null;
    private static boolean[] availablePortNumber = new boolean[65536];


    public int getNextAvaliablePort4CreateRoom(){
        for(int i = 1024; i< availablePortNumber.length ; i ++){
            if (availablePortNumber[i]) return i;
        }
        return -1;
    }
    public int getNextAvaliablePort4EnterRoom(){
         for(int i = availablePortNumber.length-1; i>1024  ; i --){
            if (availablePortNumber[i]) return i;
        }
        return -1;
    }
    public boolean isAvaliablePort(int port){
        return availablePortNumber[port];
    }

    private RemoteObjectsManager(){
        pool = new HashMap<String, IExecutor> ();
         for(int i = 0; i< availablePortNumber.length ; i ++){//init port
            if (i<=1024){
                availablePortNumber[i] = false;
            }else{
                availablePortNumber[i] = true;
            }
        }
    }
    public static RemoteObjectsManager getInstance(){
        if( rom == null){
            rom = new RemoteObjectsManager();

        }
        return rom;
    }

//    /**
//     * This method is only for enterRoom
//     * @param ie
//     * @return
//     */
//    public String bind(  IExecutor ie){
//        int port = getNextAvaliablePort4EnterRoom();
//
//       return this.bind(port, ie);
//
//    }

    /**
     * bind remote object ie to localhost:port. 
     * @param port remote object will be binded to this port
     * @return false if unsuccessfully
     *         true if successfully
     */
    public boolean bind( int port ){
        IExecutor executor = this.pool.get(String.valueOf(port));
//        System.setSecurityManager (new RMISecurityManager()) ;
        if( executor != null||availablePortNumber[port]==false) return false;//the address ip:port already binded
         try {
            LocateRegistry.createRegistry(port);
           //bind object
            IExecutor ie = new ExecutorImpl();
            Naming.rebind("rmi://localhost:"+port+"/rExecutor",ie);
            availablePortNumber[port] = false;
            
            System.out.println(">>>>>INFO:IExecutor object bind successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
           // e.printStackTrace();
            return false;
        }
        return true;
    }

     /**
     * bind remote object ie to localhost:port.
     * @param port remote object will be binded to this port
     * @param ie remote object
     * @return null if unsuccessfully
     *         String port number if successfully
     */
    public String bind( int port, IExecutor ie){
        IExecutor executor = this.pool.get(String.valueOf(port));
        if( executor != null||availablePortNumber[port]==false) return null;//the address ip:port already binded
         try {
            LocateRegistry.createRegistry(port);
           //bind remote object
            Naming.rebind("rmi://localhost:"+port+"/rExecutor",ie);
            availablePortNumber[port] = false;
            System.out.println(">>>>>INFO:remoter Executor object bind successfully.");
        } catch (RemoteException e) {
            System.out.println("exception when create remote object.");
            //e.printStackTrace();
//        } catch (AlreadyBoundException e) {
//            System.out.println("exception when rebind remote object.");
//            e.printStackTrace();
        } catch (MalformedURLException e) {
            System.out.println("Urlis wrong");
           // e.printStackTrace();

        }
        return String.valueOf(port);
    }

    public IExecutor getRemoteExecutor(String roomId, String userId, String ip, int port){
          IExecutor executor = this.pool.get(roomId+userId);
           if( executor == null) {// no such user's remote object
                 try {
                    //find the remote object on the IP:port
                    executor =(IExecutor) Naming.lookup("rmi://"+ip+":"+port+"/rExecutor");
                   
                    if(userId != null||executor!=null) //if this is looking up for another user's remote executor
                        this.pool.put(roomId+userId, executor);
                } catch (NotBoundException e) {
                    System.out.println(e.getMessage());
//                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    System.out.println(e.getMessage());
//                    e.printStackTrace();
                } catch (RemoteException e) {
                    System.out.println(e.getMessage());
//                    e.printStackTrace();
                }
           }
          return executor;
    }

    
    public void addRemoteExecutor(String roomId, String userId, IExecutor executor){
        this.pool.put(roomId+userId, executor);
    }

}
