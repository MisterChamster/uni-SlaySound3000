import javax.sound.sampled.*;

class SoundChord extends Sound{
    //Already inherited
    // String soundName;
    // float sampleRate;
    // double durationInSeconds;
    // byte[] sampleArray;
    // AudioFormat format;
    // SourceDataLine line;
    
    SoundNote[] noteArray;
    int noteArrayCLen;

    SoundChord(){
        this.soundName = "";
        this.noteArray = new SoundNote[20];  //max note size of a chord chosen arbitrarily
        this.noteArrayCLen = 0;
        this.sampleRate = 0;
        this.durationInSeconds = 0;
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

    // void setNamee(String input){
    //     this.soundName = input;
    // }

    // byte[] getSampleArray(){
    //     return this.sampleArray;
    // }

    void addNote(SoundNote note){
        if (noteArrayCLen >= noteArray.length - 1){
            //Resizes the array if the user is stupid enough to use more than 20 notes in a chord
            SoundNote[] resizeArray = new SoundNote[noteArray.length + 5];
            for (int i=0; i<noteArray.length; i++){
                resizeArray[i] = noteArray[i];
            }
            noteArray = resizeArray;
        }
        noteArray[noteArrayCLen] = note;
        noteArrayCLen++;
    }

    void delNote(int index){
        noteArrayCLen--;
        for (int i=index+1; i<=noteArrayCLen; i++){
            noteArray[i-1] = noteArray[i];
        }
        noteArray[noteArrayCLen] = null;
    }

    //debug function
    void printNoteArray(){
        for(int i=0; i<noteArrayCLen; i++){
            System.out.println((i+1) + ". " + noteArray[i].frequency);
        }
    }

    //same length arrays as of now
    void computeSampleArray(){
        // int divider = noteArrayCLen;
        int noteValTotal = 0;
        sampleArray = new byte[noteArray[0].sampleArray.length];
        //for all values in byte array...
        for (int i=0; i<noteArray[0].sampleArray.length; i++){
            //for all notes in note array...
            for (int j=0; j<noteArrayCLen; j++){
                noteValTotal += (int) noteArray[j].sampleArray[i];
            }
            sampleArray[i] = (byte) ((int)(noteValTotal/noteArrayCLen));
            // System.out.println(sampleArray[i]);
            noteValTotal = 0;
        }
    }

    @Override
    void prepareToPlay(){
        computeSampleArray();
        super.prepareToPlay();
    }

    @Override
    void playSound(){
        System.out.println("HDSAIKFDSAKFSJNAKF");
        try {line.open(format);}
        catch (LineUnavailableException e) {System.exit(0);}
        System.out.println("HDSAIKFDSAKFSJNAKF");
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