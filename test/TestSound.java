
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
//import com.sun.media.sound.
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author elf
 */
public class TestSound {

    private static String midi ="";
            private static String midiURI  = "http://file.midi.cn/midi/hk&tw/beyond/reallvU.mid";
            private Sequence sequence =null;
            private String midiFile ="E:\\elf\\Music\\Dixie Chicks\\Taking The Long Way\\1154999939.mp3";
            TestSound(){
                this.loadAndPlay();
//                javax.sound.sampled.spi.MixerProvider
            }

            public void loadAndPlay(){
                try{
                    Sequence sequence = MidiSystem.getSequence(new File("e:\\elf\\Desktop\\123.mid"));
//                    MidiSystem.getSequence("");
//                    MidiSystem.
                    Sequencer sequencer = MidiSystem.getSequencer();
                    sequencer.open();

                    sequencer.setSequence(sequence);
                    double durationInSecs = sequencer.getMicrosecondLength() / 1000000.0;

                    System.out.println("the duration of this audio is "+durationInSecs+"secs.");
                    double seconds = sequencer.getMicrosecondPosition() / 1000000.0;
                    System.out.println("the Position of this audio is "+seconds+"secs.");
                    if (sequencer instanceof Synthesizer) {
                    Synthesizer synthesizer = (Synthesizer)sequencer;

                    MidiChannel[] channels = synthesizer.getChannels();

                // gain is a value between 0 and 1 (loudest)"
                double gain = 0.9D;
                for (int i=0; i<channels.length; i++) {
                channels[i].controlChange(7, (int)(gain * 127.0));
                }
                }
                sequencer.start();
                Thread.currentThread().sleep(5000);
                seconds = sequencer.getMicrosecondPosition() / 1000000.0;
                System.out.println("the Position of this audio is "+seconds+"secs.");
                sequencer.addMetaEventListener(

                new MetaEventListener() {
                public void meta(MetaMessage event) {
                    if (event.getType() == 47) {
                        System.out.println("Sequencer is done playing.");

                        }
                    }
                });
        }catch (MalformedURLException e) {
            e.printStackTrace();
            }catch (IOException e) {e.printStackTrace();
                    }catch (MidiUnavailableException e) {e.printStackTrace();
                        }catch (InvalidMidiDataException e) {e.printStackTrace();
                            }catch (InterruptedException e){e.printStackTrace();}
            }

            public static void main(String[] args){
                new TestSound();
            }

        }




                
            

