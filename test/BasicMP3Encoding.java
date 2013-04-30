
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class BasicMP3Encoding extends Encoding  {
//public class BasicMP3Encoding extends AudioFormat.Encoding {
   public static final Encoding MP3 = new BasicMP3Encoding( "MP3" );
   public BasicMP3Encoding( String encodingName ) {
      super( encodingName );
   }


   public AudioInputStream getAudioInputStream( URL url ) throws UnsupportedAudioFileException, IOException {
       InputStream inputStream = url.openStream();
       try {
          return this.getAudioInputStream( url );
       } catch ( UnsupportedAudioFileException e ) {
          inputStream.close();
          throw e;
       } catch ( IOException e ) {
          inputStream.close();
          throw e;
       }
   }
}
