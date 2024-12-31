import javax.sound.sampled.*;

public abstract class Sound extends Thread{
    String soundName;
    float sampleRate;
    double durationInSeconds;
    byte[] sampleArray;
    AudioFormat format;
    SourceDataLine line;

    Sound(){
        this.soundName = "";
        this.sampleRate = 0;
        this.durationInSeconds = 0;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }

    Sound(float sampleRate){
        this.soundName = "";
        this.sampleRate = sampleRate;
        this.durationInSeconds = 0;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }

    Sound(float sampleRate, double durationInSeconds){
        this.soundName = "";
        this.sampleRate = sampleRate;
        this.durationInSeconds = durationInSeconds;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }

    Sound(float sampleRate, double durationInSeconds, String soundName){
        this.soundName = soundName;
        this.sampleRate = sampleRate;
        this.durationInSeconds = durationInSeconds;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }


    void setSampleRate(float input){
        this.sampleRate = input;
    }

    void setDurationInSec(double input){
        this.durationInSeconds = input;
    }

    void setNamee(String input){
        this.soundName = input;
    }

    byte[] getSampleArray(){
        return this.sampleArray;
    }

    void prepareToPlay(){
        format = new AudioFormat(sampleRate, 8, 1, false, true); //8 bits sample size
        try {line = AudioSystem.getSourceDataLine(format);}
        catch (LineUnavailableException e) {System.exit(0);}
    }
    
    void stopSound(){
        line.drain();
        line.close();
    }

    void playSound(){
        try {line.open(format);} catch (LineUnavailableException e) {System.exit(0);}
        line.start();
        //here it starts playing
        line.write(sampleArray, 0, sampleArray.length);

        //cleanup
        line.drain();
        line.close();
    }
}