import javax.sound.sampled.*;

class SoundNote extends Sound{
    //Already inherited
    // String soundName;
    // float sampleRate;
    // double durationInSeconds;
    // byte[] sampleArray;
    // AudioFormat format;
    // SourceDataLine line;

    double frequency;

    SoundNote(double durationInSec, double frequency){
        this.soundName = "";
        this.sampleRate = 44100;
        this.durationInSeconds = durationInSec;
        this.frequency = frequency;
        this.sampleArray = null;
        this.line = null;
        this.format = null;
    }

    // void setSampleRate(float input){
    //     this.sampleRate = input;
    // }

    // void setDurationInSec(double input){
    //     this.durationInSeconds = input;
    // }

    void setFrequency(double input){
        this.frequency = input;
    }

    // void setNamee(String input){
    //     this.soundName = input;
    // }

    // byte[] getSampleArray(){
    //     return this.sampleArray;
    // }

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
        super.prepareToPlay();
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

    // void stopSound(){
    //     line.drain();
    //     line.close();
    // }

    public void run(){
        this.playSound();
    }
}