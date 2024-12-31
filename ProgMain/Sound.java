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

    void prepareToPlay(){}
    void playSound(){}
    void stopSound(){}
}