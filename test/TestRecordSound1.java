//package cn.net.badboy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class TestRecordSound1 extends Thread {
    //產生TargetDataLine類別的變數m_targetdataline
    static TargetDataLine m_targetdataline;

    //透過TargetDataLine介面(繼承自DataLine)與音效卡溝通         target目標

    //產生AudioFileFormat.Type類別的變數m_targetType     Format格式
    static AudioFileFormat.Type m_targetType;

    //產生AudioInputStream類別的變數m_audioInputStream     stream流
    static AudioInputStream m_audioInputStream;

    static File m_outputFile;//產生File類別的變數   m_outputFile
    static ByteArrayOutputStream bos = new ByteArrayOutputStream();
    static byte[] buf;
    static boolean m_bRecording;//後面需用到布林函數   True,False

    public TestRecordSound1(TargetDataLine line, AudioFileFormat.Type targetType, File file) {
        m_targetdataline = line;
        m_audioInputStream = new AudioInputStream(line);
        m_targetType = targetType;
        m_outputFile = file;
    }

    public static void AudioRecorder() {
        String Filename = "e:\\elf\\Desktop\\1234.wav";
        File outputFile = new File(Filename);
        //WaveFileWriter w = new WaveFileWriter();

        //我們一開始先在主程式裡指定聲音檔的檔名為
        //JDKAudioRecorder.wav
        //   String     Filename   =   "JDKAudioRecord.wav ";
        //接著指定存檔的資料夾,預設存在相同的資料夾
        //   File outputFile   =   new   File(Filename);

        AudioFormat audioFormat = null;

        //audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,44100F, 16, , , 44100F, false);
        audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100F, 8, 1, 1, 44100F, false);
        //再來設定和取得音效檔的屬性
        //   audioFormat   =   new   AudioFormat(AudioFormat.Encoding.PCM_SIGNED,   44100F,   16,   ,   ,   44100F,   false);
        com.sun.media.MediaPlayer m = new com.sun.media.MediaPlayer();

        DataLine.Info info = new DataLine.Info(TargetDataLine.class,
                audioFormat);
        TargetDataLine targetDataLine = null;

        //然後透過TargetDataLine介面(繼承自DataLine)與音效卡溝通
        //   DataLine.Info   info   =   new   DataLine.Info(TargetDataLine.class,   audioFormat);
        //   接著做例外處理,當聲音裝置出錯或其他因素導致錄音功能無法被執行時,程式將被終止

        try {
            targetDataLine = (TargetDataLine) AudioSystem.getLine(info);
            targetDataLine.open(audioFormat);//   try{   }可能發生例外的敘述

        } catch (LineUnavailableException e)//catch{   }處理方法

        {
            System.out.println("無法錄音,錄音失敗 ");
            e.printStackTrace();
            System.exit(-1);
        }

        AudioFileFormat.Type targetType = AudioFileFormat.Type.WAVE;
        TestRecordSound1 recorder = null;

        recorder = new TestRecordSound1(targetDataLine, targetType, outputFile);
        recorder.start();
    }

    public void start() {
        m_targetdataline.start();
        super.start();
        System.out.println("recording...");
    }

    public static void stopRecording() {
        m_targetdataline.stop();
        m_targetdataline.close();
        m_bRecording = false;
        buf = bos.toByteArray();
        System.out.println("stopped.");
    }

    public void run() {
        try {
            //AudioSystem.write(m_audioInputStream, m_targetType, m_outputFile);
            AudioSystem.write(m_audioInputStream, m_targetType, bos);

            System.out.println("after   write() ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  public static void main(String args[]){
      TestRecordSound1.AudioRecorder();
      final Timer t = new Timer();
                    t.schedule(new TimerTask(){
                                public void run(){
                                    //stopRecording();
                                    System.out.println("112132312");
                                    t.cancel();
                                }
                        } , 6000
                    );
                        System.out.println(2);
  }

}


class Output2Input {

    Output2Input(OutputStream out, InputStream in){
        
    }


}