
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.AudioInputStream;
//import javax.media.p
//import com.sun.media.parser.audio.
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class BasicMP3FormatConversionProvider extends javax.sound.sampled.spi.FormatConversionProvider{
//    javax.sound.sampled.
    protected static final AudioFormat [] SOURCE_FORMATS = {
   // encoding, rate, bits, channels, frameSize, frameRate, big endian
   new AudioFormat( BasicMP3Encoding.MP3,  8000.0F, -1, 1, -1, -1, false ),
   new AudioFormat( BasicMP3Encoding.MP3,  8000.0F, -1, 2, -1, -1, false ),
   new AudioFormat( BasicMP3Encoding.MP3, 11025.0F, -1, 1, -1, -1, false ),
   new AudioFormat( BasicMP3Encoding.MP3, 11025.0F, -1, 2, -1, -1, false ),
    };

    @Override
    public AudioInputStream getAudioInputStream(Encoding targetEncoding, AudioInputStream sourceStream) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AudioInputStream getAudioInputStream(AudioFormat targetFormat, AudioInputStream sourceStream) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Encoding[] getSourceEncodings() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Encoding[] getTargetEncodings() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Encoding[] getTargetEncodings(AudioFormat sourceFormat) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AudioFormat[] getTargetFormats(Encoding targetEncoding, AudioFormat sourceFormat) {
//        if ( isConversionSupported( targetFormat, audioInputStream.getFormat() )) {
//           return new DecodedMpegAudioInputStream( targetFormat, audioInputStream );
//        }
//        throw new IllegalArgumentException( "conversion not supported" );
        return null;
    }

}
