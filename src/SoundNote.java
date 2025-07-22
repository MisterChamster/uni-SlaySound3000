// package sound_classes;



public class SoundNote extends Sound {
    // ======================= Fields =======================
    public float frequency;


    // ===================== Constructors =====================
    // constructor for adding notes
    public SoundNote() {}

    public SoundNote(String soundName, float frequency) {
        super();
        this.soundName = soundName;
        this.frequency = frequency;
    }

    public SoundNote(float sampleRate, int sampleSize, double durationInSec, float frequency, String soundName) {
        super(sampleRate, sampleSize, durationInSec, soundName);
        this.frequency = frequency;
    }


    // ======================= Methods =======================
    public void setFrequency(float input) {
        this.frequency = input;
    }

    public void computeSampleArray() {
        double twoPIFreq = 2.0 * Math.PI * frequency;
        int sampleNumInArray = (int) (Math.ceil(sampleRate * durationInSeconds));
        sampleArray = new byte[sampleNumInArray * (sampleSize / 8)];
        double angleSIN;

        if (sampleSize == 8) {
            for (int i = 0; i < sampleArray.length; i++) {
                angleSIN = Math.sin(twoPIFreq * i / sampleRate);
                sampleArray[i] = (byte) (angleSIN * 127); // scale to byte range ((2**(8-1))-1)
            }
        } else if (sampleSize == 16) {
            short tempScaledAngleSIN;
            for (int i = 0, j = 0; i < sampleNumInArray; i++, j += 2) {
                angleSIN = Math.sin(twoPIFreq * i / sampleRate);
                tempScaledAngleSIN = (short) (angleSIN * 32767); // scale to byte range ((2**(16-1))-1)
                sampleArray[j] = (byte) (tempScaledAngleSIN & 0xFF);
                sampleArray[j + 1] = (byte) ((tempScaledAngleSIN >> 8) & 0xFF);
            }
        }
    }

    @Override
    public void prepareToPlay() {
        computeSampleArray();
        super.prepareToPlay();
    }

    @Override
    public void playSound() {
        super.playSound();
    }

    public void run() {
        this.playSound();
    }
}
