
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class SoundServer {
    ServerSocket server = null;
    boolean isRun = true;
    public void start() throws IOException{
        try{
            server = new ServerSocket(3010);
//            pool = new ThreadPoolManager();
//            DictionaryFactory.initFactory(new String[]{this.path,idxpath});
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

        File soundFile = new File("e:\\elf\\Desktop\\soundTest.wav");


        BufferedReader br = new BufferedReader(new FileReader(soundFile));
        String line = null;
       
        InputStream in = null;
        OutputStream out = null;
        Socket client = null;
         try{
        while(isRun){
            client = server.accept();
            in = client.getInputStream();
            out = client.getOutputStream();
             while ((line = br.readLine()) != null) {
                out.write(line.getBytes());
            }
        }
         }catch(IOException ioe){
                ioe.printStackTrace();
            }catch(Exception e){
                e.printStackTrace();
            }
        finally{
            in.close();
            out.close();
            br.close();
        }
    }

    public static void main(String[] args) throws IOException{
        new SoundServer().start();
    }
}
