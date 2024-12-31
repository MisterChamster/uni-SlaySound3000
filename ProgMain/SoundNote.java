import javax.sound.sampled.*;
//every note must have name and freq

class SoundNote extends Sound{
    double frequency;

    // SoundNote(){
    //     super();
    //     this.frequency = 0;
    // }

    // //constructor for adding notes to chord
    // SoundNote(double frequency){
    //     super();
    //     this.frequency = frequency;
    // }

    //constructor for adding notes to chord
    SoundNote(double frequency, String soundName){
        super();
        this.frequency = frequency;
        this.soundName = soundName;
    }

    // SoundNote(float sampleRate, double durationInSec, double frequency){
    //     super(sampleRate, durationInSec);
    //     this.frequency = frequency;
    // }

    SoundNote(float sampleRate, double durationInSec, double frequency, String soundName){
        super(sampleRate, durationInSec, soundName);
        this.frequency = frequency;
    }


    void setFrequency(double input){
        this.frequency = input;
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

    public void run(){
        this.playSound();
    }
}