import javax.sound.sampled.*;

class SoundNote extends Sound{
    String soundName;
    float sampleRate;
    double durationInSeconds;
    double frequency;
    byte[] sampleArray;
    AudioFormat format;
    SourceDataLine line;

    SoundNote(double durationInSec, double frequency){
        this.soundName = "";
        this.sampleRate = 44100;
        this.durationInSeconds = durationInSec;
        this.frequency = frequency;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }

    @Override
    void setSampleRate(float input){
        this.sampleRate = input;
    }

    @Override
    void setDurationInSec(double input){
        this.durationInSeconds = input;
    }

    void setFrequency(double input){
        this.frequency = input;
    }

    @Override
    void setNamee(String input){
        this.soundName = input;
    }

    @Override
    byte[] getSampleArray(){
        return this.sampleArray;
    }

    void computeSampleArray(){
        double twoPIFreq = 2.0 * Math.PI * frequency;
        sampleArray = new byte[(int) Math.ceil(sampleRate * durationInSeconds)];
        double angle;

        for (int i = 0; i < sampleArray.length; i++) {
            angle = twoPIFreq * i / sampleRate;
            sampleArray[i] = (byte) (Math.sin(angle) * 127); //scale to byte range
            // System.out.println(sampleArray[i]);
        }
    }

    @Override
    void prepareToPlay(){
        computeSampleArray();
        format = new AudioFormat(sampleRate, 8, 1, true, true); //8 bits sample size
        try {line = AudioSystem.getSourceDataLine(format);}
        catch (LineUnavailableException e) {System.exit(0);}
    }

    @Override
    void playSound(){
        try {line.open(format);}
        catch (LineUnavailableException e) {System.exit(0);}
        line.start();
        //here it starts playing
        line.write(sampleArray, 0, sampleArray.length);

        //cleanup
        line.drain();
        line.close();
    }

    @Override
    void stopSound(){
        line.drain();
        line.close();
    }

    public void run(){
        this.playSound();
    }
}