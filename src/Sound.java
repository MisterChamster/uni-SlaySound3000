// package sound_classes;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;



public abstract class Sound extends Thread{
    // ======================= Fields =======================
    String          soundName;
    float           sampleRate;
    int             sampleSize;
    double          durationInSeconds;
    byte[]          sampleArray;
    AudioFormat     format;
    SourceDataLine  line;


    // ===================== Constructors =====================
    public Sound(){
        this.soundName = null;
        this.sampleRate = 0;
        this.sampleSize = 0;
        this.durationInSeconds = 0;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }

    public Sound(float sampleRate, int sampleSize){
        this.soundName = "";
        this.sampleRate = sampleRate;
        this.sampleSize = sampleSize;
        this.durationInSeconds = 0;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }

    public Sound(float sampleRate, int sampleSize, double durationInSeconds){
        this.soundName = "";
        this.sampleRate = sampleRate;
        this.sampleSize = sampleSize;
        this.durationInSeconds = durationInSeconds;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }

    public Sound(float sampleRate, int sampleSize, double durationInSeconds, String soundName){
        this.soundName = soundName;
        this.sampleRate = sampleRate;
        this.sampleSize = sampleSize;
        this.durationInSeconds = durationInSeconds;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }


    // ======================= Methods =======================
    public void setSampleRate(float input){
        this.sampleRate = input;
    }

    public void setSampleSize(int input){
        this.sampleSize = input;
    }

    public void setDurationInSec(double input){
        this.durationInSeconds = input;
    }

    public void setNamee(String input){
        this.soundName = input;
    }

    public byte[] getSampleArray(){
        return this.sampleArray;
    }

    public void prepareToPlay(){
        format = new AudioFormat(sampleRate, sampleSize, 1, true, false);
        try {line = AudioSystem.getSourceDataLine(format);}
        catch (LineUnavailableException e) {System.exit(0);}
    }

    //cleanup
    public void stopSound(){
        line.drain();
        line.close();
    }

    public void playSound(){
        try {line.open(format);} catch (LineUnavailableException e) {System.out.println(e); System.exit(0);}
        line.start();
        //here it starts playing
        line.write(sampleArray, 0, sampleArray.length);

        stopSound();
    }
}
